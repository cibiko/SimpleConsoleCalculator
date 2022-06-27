package ru.mondayish.byndyusoft.model

data class Action(
    var firstOperand: String,
    var secondOperand: String,
    val operation: String,
    val priority: Int
)