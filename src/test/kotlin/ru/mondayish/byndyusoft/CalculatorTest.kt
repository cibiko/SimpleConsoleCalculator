package ru.mondayish.byndyusoft

import org.junit.Test
import ru.mondayish.byndyusoft.service.Calculator
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CalculatorTest {

    @Test
    fun basicTests() {
        assertEquals(0.0, Calculator().calculate("1+2-3"))
        assertEquals(8.0, Calculator().calculate("2*2*2"))
        assertEquals(-2.0, Calculator().calculate("3-5"))
        assertEquals(3.0, Calculator().calculate("2+10/10"))
        assertEquals(6.0, Calculator().calculate("2*4-2"))
    }

    @Test
    fun longTests() {
        assertEquals(5.0, Calculator().calculate("2*3-6+10/2"))
        assertEquals(0.4, Calculator().calculate("1*2*3*4/12/5"))
        assertEquals(19.0, Calculator().calculate("100/25*4/16+4*2/4+1+2+3+4+5+6/6"))
        assertEquals(36.0, Calculator().calculate("10-5*2+3-3+122-121*1/1+35"))
        assertEquals(0.0, Calculator().calculate("2*3*4*5*6*5*4*3*2*1/1/2/3/4/5/6/5/4/3/2-1"))
    }

    @Test
    fun bracketsTests() {
        assertEquals(10.0, Calculator().calculate("2*(8-3)"))
        assertEquals(4.333, Calculator().calculate("10-(5+2/3)"))
        assertEquals(0.0, Calculator().calculate("(1+2)+2*(3-2)-5"))
        assertEquals(115.0, Calculator().calculate("10/(6+4)*115"))
        assertEquals(8.0, Calculator().calculate("100-(9+0-8)*(32/16)-(9*10)"))
    }

    @Test
    fun nestedBracketsTests() {
        assertEquals(0.0, Calculator().calculate("10-(2*(2+3))"))
        assertEquals(0.0, Calculator().calculate("((2+3)*2)-10"))
        assertEquals(-15.0, Calculator().calculate("10-(35/7*(3+2))"))
        assertEquals(1.2, Calculator().calculate("3*2-((3+3)-6/(7-2))"))
        assertEquals(12.0, Calculator().calculate("((1+2)+(1+3)+(1+4))"))
    }

    @Test
    fun deepNestedBracketsTests() {
        assertEquals(0.0, Calculator().calculate("((((10-1)-2)-3)-4)"))
        assertEquals(20.0, Calculator().calculate("((1+2)*6+(10/(2+3)))"))
        assertEquals(0.01, Calculator().calculate("(1/100+(2-1*(3-2))+3-4)"))
        assertEquals(0.0, Calculator().calculate("(1*(2*(3*(4*(5-5)/4)/3)/2)/1)"))
        assertEquals(-4.0, Calculator().calculate("(10-(3*(6/(4-2)))-5)"))
    }

    @Test
    fun doubleNumbersTests() {
        assertEquals(5.0, Calculator().calculate("1,1*5-0,5"))
        assertEquals(85.333, Calculator().calculate("10.24/0.02/6"))
        assertEquals(1.69, Calculator().calculate("(13,0*13,0)/100,0"))
        assertEquals(20.3, Calculator().calculate("3,5*3-1,4+(9,4+1,8)"))
        assertEquals(3.130, Calculator().calculate("3.5*3.3*4.2/15.5"))
    }

    @Test
    fun doubleIntNumbersTests() {
        assertEquals(3.667, Calculator().calculate("5.5*2/3"))
        assertEquals(4.55, Calculator().calculate("4,55*4/2/2"))
        assertEquals(-38.5, Calculator().calculate("100.0/5-19.5*3"))
        assertEquals(18.0, Calculator().calculate("1,4*5+(9,4+1,6)"))
        assertEquals(38.5, Calculator().calculate("((10.5-5)*2)*3.5"))
    }

    @Test
    fun expressionWithErrorsTests() {
        assertFailsWith<IllegalArgumentException> { Calculator().calculate("2ass*3*4") }
        assertFailsWith<IllegalArgumentException> { Calculator().calculate("2*3/0") }
        assertFailsWith<IllegalArgumentException> { Calculator().calculate("2*3*(9/2-(3+4)))") }
    }

    @Test
    fun unaryMinusAtStartTests() {
        assertEquals(-1.0, Calculator().calculate("-3+2"))
        assertEquals(-12.0, Calculator().calculate("-3*4"))
        assertEquals(-2.0, Calculator().calculate("-12/6"))
    }

    @Test
    fun unaryMinusTests() {
        assertEquals(1.0, Calculator().calculate("2+(-3)+2"))
        assertEquals(-4.0, Calculator().calculate("2*(-3)+2"))
        assertEquals(1.0, Calculator().calculate("2/(-2)+2"))
        assertEquals(6.0, Calculator().calculate("2-(-2)+2"))
        assertEquals(-2.0, Calculator().calculate("2+(-3*2)+2"))
    }
}