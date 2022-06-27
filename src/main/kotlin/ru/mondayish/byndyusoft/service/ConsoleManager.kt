package ru.mondayish.byndyusoft.service

class ConsoleManager {

    fun runCalculator() {
        println("Введите выражение:")
        val expression: String = readln()
        writePrettyResult(Calculator().calculate(expression))
    }

    private fun writePrettyResult(result: Double) {
        println("Результат: " + makePretty(result))
    }

    private fun makePretty(n: Double): String = if (n % 1.0 == 0.0) n.toInt().toString() else n.toString()
}