package ru.mondayish.byndyusoft.utils

import ru.mondayish.byndyusoft.model.Action

private val ACTION_PRIORITIES: Map<String, Int> = mapOf(Pair("+", 1), Pair("-", 1), Pair("*", 2), Pair("/", 2))

object PriorityUtils {

    fun calculatePriority(operator: Char, nestingCount: Int): Int =
        nestingCount * 10 + ACTION_PRIORITIES[operator.toString()]!!

    fun findMaxPriorityActionIndex(actions: MutableList<Action>): Int {
        val sortedIndexes: List<Int> = sequence {
            for (i in 0 until actions.size) {
                yield(i)
            }
        }.sortedWith { a, b -> actions[a].priority - actions[b].priority }.toList()
        return sortedIndexes.first { i -> actions[i].priority == actions[sortedIndexes.last()].priority }
    }
}