package sorting

import java.util.*
import kotlin.streams.toList

class SortingTool(args: Array<String>) {
    private val inputType : InputType
    init {
        inputType = parseArguments(args)
    }

    fun run() {
        when (inputType) {
            InputType.WORD -> println()
            InputType.LINE -> println()
            InputType.NUMBER -> getStatsForNumbers()
        }
    }

    private fun parseArguments(args: Array<String>): InputType {
        if (args.isEmpty() || args[0] != "-dataType") {
            return InputType.WORD
        }

        return when (args[1]) {
            "long" -> InputType.NUMBER
            "line" -> InputType.LINE
            "word" -> InputType.WORD

            else -> InputType.WORD
        }
    }
}

fun getStatsForNumbers() {
    val userNums = Scanner(System.`in`).tokens()
            .mapToInt(Integer::parseInt).toList()
    val totalCount = userNums.count()
    val max = userNums.max()
    val times = userNums.filter{ n -> n == max }.size
    println("Total numbers: $totalCount.")
    println("The greatest number: $max ($times time(s)).")
}
