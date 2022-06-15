package ru.mondayish.byndyusoft

data class Action(
    var firstOperand: String,
    var secondOperand: String,
    val operation: String,
    val priority: Int
)