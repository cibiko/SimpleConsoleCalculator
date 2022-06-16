package ru.mondayish.byndyusoft

import kotlin.math.pow
import kotlin.math.round

private const val ALL_ACTIONS: String = "+-*/"
private const val DECIMAL_PLACES_TO_ROUND: Int = 3
private val ACTION_PRIORITIES: Map<String, Int> = mapOf(Pair("+", 1), Pair("-", 1), Pair("*", 2), Pair("/", 2))
private val ACTION_FUNCTIONS: Map<String, (Double, Double) -> Double> =
    mapOf(Pair("+") { a: Double, b: Double -> a + b }, Pair("-") { a: Double, b: Double -> a - b },
        Pair("*") { a: Double, b: Double -> a * b }, Pair("*") { a: Double, b: Double -> a / b })

class Calculator {

    fun calculate(expression: String): Double {
        val actions: MutableList<Action> = mutableListOf()
        var previousOperand: String = ""
        var currentOperator: String = ""
        var currentOperand: StringBuilder = StringBuilder()
        var nestingCount: Int = 0

        for (i in expression.indices) {
            if (ALL_ACTIONS.contains(expression[i].toString())) {
                if (previousOperand.isNotEmpty()) {
                    actions.add(Action(previousOperand, currentOperand.toString(), currentOperator, 0))
                }
            }
        }

//        for (int i = 0; i < expression.length(); i++) {
//            if (ALL_ACTIONS.contains(String.valueOf(expression.charAt(i)))) {
//                if (!previousOperand.isEmpty()) {
//                    actions.add(new Action(previousOperand, currentOperand.toString(), currentOperator,
//                            calculatePriority(currentOperator.charAt(0),
//                                    (currentOperand.charAt(0) == '(' ? nestingCount - 1 :
//                                            nestingCount + getCloseBracketCount(currentOperand.toString())))));
//                }
//
//                previousOperand = currentOperand.toString();
//                currentOperator = String.valueOf(expression.charAt(i));
//                currentOperand = new StringBuilder();
//
//            } else {
//                if (expression.charAt(i) == '(') {
//                    nestingCount++;
//                }
//                if (expression.charAt(i) == ')') {
//                    nestingCount--;
//                }
//                currentOperand.append(expression.charAt(i));
//            }
//        }
//
//        if (nestingCount != 0) {
//            throw new IllegalArgumentException("Invalid brackets in expression");
//        }
//
//        try {
//            if (currentOperator.isEmpty()) return parseDouble(expression);
//            actions.add(new Action(previousOperand, currentOperand.toString(), currentOperator,
//                    calculatePriority(currentOperator.charAt(0), getCloseBracketCount(currentOperand.toString()))));
//
//            while (actions.size() > 1) {
//                int maxIndex = findMaxPriorityActionIndex(actions);
//                double actionResult = calculateActionResult(actions.get(maxIndex));
//
//                if (maxIndex != 0) {
//                    actions.get(maxIndex - 1).setSecondOperand(String.valueOf(actionResult));
//                }
//                if (maxIndex != actions.size() - 1) {
//                    actions.get(maxIndex + 1).setFirstOperand(String.valueOf(actionResult));
//                }
//                actions.remove(maxIndex);
//            }
//
//            return roundDouble(calculateActionResult(actions.get(0)));
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("All operands must be number");
//        }
//    }
//
//    private double calculateActionResult(Action action) {
//        double firstOperand = parseDouble(action.getFirstOperand()
//                .replaceAll("\\(", "").replaceAll("\\)", ""));
//        double secondOperand = parseDouble(action.getSecondOperand()
//                .replaceAll("\\(", "").replaceAll("\\)", ""));
//        return ACTION_FUNCTIONS.get(action.getOperation())
//                .applyAsDouble(firstOperand, secondOperand);
//    }
//
//    private double roundDouble(double n) {
//        return Math.round(n * Math.pow(10, DECIMAL_PLACES_TO_ROUND)) / Math.pow(10, DECIMAL_PLACES_TO_ROUND);
//    }
//
//    private int getCloseBracketCount(String operand) {
//        return (int) Arrays.stream(operand.split("")).filter(s -> s.equals(")")).count();
//    }
        return 0.0
    }

    private fun roundDouble(n: Double): Double =
        round(n * 10.0.pow(DECIMAL_PLACES_TO_ROUND)) / 10.0.pow(DECIMAL_PLACES_TO_ROUND)

    private fun getCloseBracketCount(operand: String): Int = operand.split("").count { s -> s == ")" }

    private fun findMaxPriorityActionIndex(actions: MutableList<Action>): Int =
        generateSequence(actions.size) { if (it == 0) 0 else it + 1 }
            .sortedWith { a, b -> actions[a].priority - actions[b].priority }
            .reduce { _, second -> second }

    private fun parseDouble(operand: String): Double = operand.replace(",", ".").toDouble()

    private fun calculatePriority(operator: Char, nestingCount: Int): Int =
        nestingCount * 10 + ACTION_PRIORITIES[operator.toString()]!!
}





