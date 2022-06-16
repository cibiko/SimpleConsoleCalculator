package ru.mondayish.byndyusoft

import kotlin.math.pow
import kotlin.math.round

private const val ALL_ACTIONS: String = "+-*/"
private const val DECIMAL_PLACES_TO_ROUND: Int = 3
private val ACTION_PRIORITIES: Map<String, Int> = mapOf(Pair("+", 1), Pair("-", 1), Pair("*", 2), Pair("/", 2))
private val ACTION_FUNCTIONS: Map<String, (Double, Double) -> Double> =
    mapOf(Pair("+") { a: Double, b: Double -> a + b }, Pair("-") { a: Double, b: Double -> a - b },
        Pair("*") { a: Double, b: Double -> a * b }, Pair("/") { a: Double, b: Double -> a / b })

class Calculator {

    fun calculate(expression: String): Double {
        val actions: MutableList<Action> = findActions(expression)
        if (actions.isEmpty()) {
            return parseDouble(expression)
        }

        try {
            while (actions.size > 1) {
                val maxIndex: Int = findMaxPriorityActionIndex(actions)
                val actionResult: Double = calculateActionResult(actions[maxIndex])

                if (maxIndex != 0) {
                    actions[maxIndex - 1].secondOperand = actionResult.toString()
                }
                if (maxIndex != actions.size - 1) {
                    actions[maxIndex + 1].firstOperand = actionResult.toString()
                }
                actions.removeAt(maxIndex)
            }

            return roundDouble(calculateActionResult(actions[0]))
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("All operands must be number")
        }
    }

    private fun findActions(expression: String): MutableList<Action> {
        val actions: MutableList<Action> = mutableListOf()
        var previousOperand = ""
        var currentOperator = ""
        var currentOperand: StringBuilder = StringBuilder()
        var nestingCount = 0

        for (i in expression.indices) {
            if (ALL_ACTIONS.contains(expression[i].toString())) {
                if (previousOperand.isNotEmpty()) {
                    actions.add(
                        Action(
                            previousOperand, currentOperand.toString(), currentOperator,
                            calculatePriority(
                                currentOperator[0],
                                nestingCount + getCloseBracketCount(currentOperand.toString())
                                        - getOpenBracketCount(currentOperand.toString())
                            )
                        )
                    )
                }

                previousOperand = currentOperand.toString()
                currentOperator = expression[i].toString()
                currentOperand = StringBuilder()
            } else {
                nestingCount = if (expression[i] == '(') nestingCount + 1 else nestingCount
                nestingCount = if (expression[i] == ')') nestingCount - 1 else nestingCount
                currentOperand.append(expression[i])
            }
        }

        if (nestingCount != 0) {
            throw IllegalArgumentException("Invalid brackets in expressions")
        }

        try {
            if (currentOperator.isEmpty()) {
                return mutableListOf()
            }
            actions.add(
                Action(
                    previousOperand, currentOperand.toString(), currentOperator,
                    calculatePriority(currentOperator[0], getCloseBracketCount(currentOperand.toString()))
                )
            )
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("All operands must be number")
        }

        return actions
    }

    private fun calculateActionResult(action: Action) =
        ACTION_FUNCTIONS[action.operation]!!
            .invoke(
                parseDouble(action.firstOperand.replace("(", "").replace(")", "")),
                parseDouble(action.secondOperand.replace("(", "").replace(")", ""))
            )

    private fun roundDouble(n: Double): Double =
        round(n * 10.0.pow(DECIMAL_PLACES_TO_ROUND)) / 10.0.pow(DECIMAL_PLACES_TO_ROUND)

    private fun getCloseBracketCount(operand: String): Int = operand.split("").count { s -> s == ")" }

    private fun getOpenBracketCount(operand: String): Int = operand.split("").count { s -> s == "(" }

    private fun findMaxPriorityActionIndex(actions: MutableList<Action>): Int =
        sequence {
            for (i in 0 until actions.size) {
                yield(i)
            }
        }.sortedWith { a, b -> actions[a].priority - actions[b].priority }.last()

    private fun parseDouble(operand: String): Double = operand.replace(",", ".").toDouble()

    private fun calculatePriority(operator: Char, nestingCount: Int): Int =
        nestingCount * 10 + ACTION_PRIORITIES[operator.toString()]!!
}





