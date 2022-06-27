package ru.mondayish.byndyusoft.utils

import kotlin.math.pow
import kotlin.math.round

private const val DECIMAL_PLACES_TO_ROUND: Int = 3

object CalculatorDoubleUtils {

    fun roundDouble(n: Double): Double =
        round(n * 10.0.pow(DECIMAL_PLACES_TO_ROUND)) / 10.0.pow(DECIMAL_PLACES_TO_ROUND)

    fun parseDouble(operand: String): Double = operand.replace(",", ".").toDouble()
}