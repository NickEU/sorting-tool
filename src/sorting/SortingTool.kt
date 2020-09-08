package sorting

import java.util.*
import kotlin.streams.toList

class SortingTool(args: Array<String>) {
    private val inputType : InputType
    init {
        inputType = parseArguments(args)
    }

    fun run() {
        val userNums = Scanner(System.`in`).tokens()
                .mapToInt(Integer::parseInt).toList()
        getStatsForNumbers(userNums)
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

fun getStatsForNumbers(numbers: List<Int>) {
    val totalCount = numbers.count()
    val max = numbers.max()
    val times = numbers.filter{ n -> n == max }.size
    println("Total numbers: $totalCount.")
    println("The greatest number: $max ($times time(s)).")
}
