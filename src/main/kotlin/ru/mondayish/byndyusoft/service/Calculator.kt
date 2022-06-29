package ru.mondayish.byndyusoft.service

import ru.mondayish.byndyusoft.utils.CalculatorDoubleUtils
import ru.mondayish.byndyusoft.utils.PriorityUtils
import ru.mondayish.byndyusoft.model.Action
import ru.mondayish.byndyusoft.utils.OperationUtils

class Calculator {

    fun calculate(expression: String): Double {
        val actions: MutableList<Action> = ActionParser().findActions(expression)
        if (actions.isEmpty()) {
            return CalculatorDoubleUtils.parseDouble(expression)
        }

        try {
            while (actions.size > 1) {
                val maxIndex: Int = PriorityUtils.findMaxPriorityActionIndex(actions)
                val actionResult: Double = calculateActionResult(actions[maxIndex])

                if (maxIndex != 0) {
                    actions[maxIndex - 1].secondOperand = actionResult.toString()
                }
                if (maxIndex != actions.size - 1) {
                    actions[maxIndex + 1].firstOperand = actionResult.toString()
                }
                actions.removeAt(maxIndex)
            }

            val result: Double = CalculatorDoubleUtils.roundDouble(calculateActionResult(actions[0]))
            if (result.isNaN() || result.isInfinite()) {
                throw IllegalArgumentException("Illegal operation in expression, division on 0, for example")
            }
            return result
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("All operands must be number")
        }
    }

    private fun calculateActionResult(action: Action) =
        OperationUtils.ACTION_FUNCTIONS[action.operation]!!
            .invoke(
                CalculatorDoubleUtils.parseDouble(action.firstOperand.replace("(", "").replace(")", "")),
                CalculatorDoubleUtils.parseDouble(action.secondOperand.replace("(", "").replace(")", ""))
            )
}