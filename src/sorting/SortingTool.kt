package sorting

import java.util.Scanner
import kotlin.streams.toList

class SortingTool(args: Array<String>) {
    private val inputType: InputType = parseArguments(args)
    private val sc = Scanner(System.`in`)

    fun run() {
        val whiteSpaceDelimiter = "\\s+"
        val newlineDelimiter = "\\R"
        when (inputType) {
            InputType.WORD -> getStatsForStrings(whiteSpaceDelimiter)
            InputType.LINE -> getStatsForStrings(newlineDelimiter)
            InputType.LONG -> getStatsForNumbers()
        }
    }

    private fun getStatsForStrings(delimiter: String) {
        val input = sc.useDelimiter(delimiter)
                .tokens().toList()
        printStats(InputStats(input, input.maxBy { it.length }))
    }

    private fun getStatsForNumbers() {
        val userNums = sc.tokens()
                .mapToInt(Integer::parseInt)
                .toList()
        printStats(InputStats(userNums, userNums.max()))
    }

    private fun printStats(stats: InputStats<*>) {
        val isLine = inputType == InputType.LINE
        val type = inputType.getName()
        val optionalBr = if (!isLine) "" else "\n"
        println("Total ${type}s: ${stats.totalCount}.")
        println("The ${inputType.getWordMax()} $type: $optionalBr${stats.maxElement}" +
                "$optionalBr (${stats.times} time(s), ${stats.percentage}%).")
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
}
