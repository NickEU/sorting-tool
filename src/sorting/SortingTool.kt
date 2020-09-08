package sorting

import java.util.*
import kotlin.streams.toList

class SortingTool(args: Array<String>) {
    private val inputType : InputType
    private val sc = Scanner(System.`in`)
    init {
        inputType = parseArguments(args)
    }

    fun run() {
        when (inputType) {
            InputType.WORD -> println()
            InputType.LINE -> println()
            InputType.LONG -> getStatsForNumbers()
        }
    }

    private fun parseArguments(args: Array<String>): InputType {
        if (args.isEmpty() || args[0] != "-dataType") {
            return InputType.WORD
        }

        return when (args[1]) {
            "long" -> InputType.LONG
            "line" -> InputType.LINE
            "word" -> InputType.WORD

            else -> InputType.WORD
        }
    }

    private fun getStatsForNumbers() {
        val userNums = sc.tokens()
                .mapToInt(Integer::parseInt)
                .toList()
        val totalCount = userNums.count()
        val max = userNums.max()
        val times = userNums.filter{ n -> n == max }.size
        val percentage = (100.0 * times / totalCount).toInt()
        println("Total numbers: $totalCount.")
        println("The greatest number: $max ($times time(s), $percentage%).")
    }
}
