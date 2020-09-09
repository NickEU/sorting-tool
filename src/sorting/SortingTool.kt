package sorting

import java.util.Scanner
import kotlin.streams.toList

class SortingTool(args: Array<String>) {
    private val inputType: InputType
    private val sc = Scanner(System.`in`)

    init {
        inputType = parseArguments(args)
    }

    fun run() {
        when (inputType) {
            InputType.WORD -> getStatsForStrings("\\s+")
            InputType.LINE -> getStatsForStrings("\\R")
            InputType.LONG -> getStatsForNumbers()
        }
    }

    private fun getStatsForStrings(delimiter: String) {
        val input = sc.useDelimiter(delimiter)
                .tokens().toList()
        val totalCount = input.count()
        val longest = input.maxBy { it.length }
        val times = input.filter { n -> n == longest }.size
        val percentage = (100.0 * times / totalCount).toInt()

        printStats(InputStats(totalCount, longest, times, percentage))
    }

    private fun printStats(stats: InputStats<*>) {
        val isLine = inputType == InputType.LINE
        val type = inputType.getName()
        val optionalBr = if (!isLine) "" else "\n"
        println("Total ${type}s: ${stats.totalCount}.")
        println("The ${inputType.getWordMax()} $type: $optionalBr${stats.longest}" +
                "$optionalBr (${stats.times} time(s), ${stats.percentage}%).")
    }

    private fun getStatsForNumbers() {
        val userNums = sc.tokens()
                .mapToInt(Integer::parseInt)
                .toList()
        val totalCount = userNums.count()
        val max = userNums.max()
        val times = userNums.filter { n -> n == max }.size
        val percentage = (100.0 * times / totalCount).toInt()

        printStats(InputStats(totalCount, max, times, percentage))
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
