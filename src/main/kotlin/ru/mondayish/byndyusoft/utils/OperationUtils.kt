package ru.mondayish.byndyusoft.utils

object OperationUtils {

    const val ALL_ACTIONS: String = "+-*/"
    val ACTION_PRIORITIES: Map<String, Int> = mapOf(Pair("+", 1), Pair("-", 1), Pair("*", 2), Pair("/", 2))
    val ACTION_FUNCTIONS: Map<String, (Double, Double) -> Double> =
        mapOf(Pair("+") { a: Double, b: Double -> a + b }, Pair("-") { a: Double, b: Double -> a - b },
            Pair("*") { a: Double, b: Double -> a * b }, Pair("/") { a: Double, b: Double -> a / b })
}