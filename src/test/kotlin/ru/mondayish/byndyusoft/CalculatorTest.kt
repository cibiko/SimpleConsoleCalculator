package ru.mondayish.byndyusoft

import kotlin.test.assertEquals

class CalculatorTest {

    fun basicTests() {
        assertEquals(0.0, Calculator().calculate("1+2-3"))
    }
}
