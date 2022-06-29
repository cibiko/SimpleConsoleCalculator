package ru.mondayish.byndyusoft.service

import ru.mondayish.byndyusoft.utils.PriorityUtils
import ru.mondayish.byndyusoft.model.Action
import ru.mondayish.byndyusoft.utils.OperationUtils

class ActionParser {

    fun findActions(expression: String): MutableList<Action> {
        val actions: MutableList<Action> = mutableListOf()
        var previousOperand = ""
        var currentOperator = ""
        var currentOperand: StringBuilder = StringBuilder()
        var nestingCount = 0

        for (i in expression.indices) {
            if (OperationUtils.ALL_ACTIONS.contains(expression[i].toString()) && !(i == 0 && expression[i] == '-') &&
                !(expression[i] == '-' && i != 0 && expression[i - 1] == '(')) {
                if (previousOperand.isNotEmpty()) {
                    actions.add(
                        Action(
                            previousOperand, currentOperand.toString(), currentOperator,
                            PriorityUtils.calculatePriority(
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
                    PriorityUtils.calculatePriority(currentOperator[0], getCloseBracketCount(currentOperand.toString()))
                )
            )
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("All operands must be number")
        }

        return actions
    }

    private fun getCloseBracketCount(operand: String): Int = operand.split("").count { s -> s == ")" }

    private fun getOpenBracketCount(operand: String): Int = operand.split("").count { s -> s == "(" }
}