package sorting

import java.util.Scanner
import kotlin.streams.toList

class SortingTool(args: Array<String>) {
    private val config: Config = ArgsParser.buildConfig(args)
    private val sc = Scanner(System.`in`)
    private var performSort = false

    fun run() {
        val whiteSpaceDelimiter = "\\s+"
        val newlineDelimiter = "\\R"
        when (config.inputType) {
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
        if (performSort) {
            val sortedNums = userNums.sorted()
                    .joinToString(" ")
            println("Total numbers: ${userNums.size}.")
            println("Sorted data: $sortedNums")
        } else {
            printStats(InputStats(userNums, userNums.max()))
        }
    }

    private fun printStats(stats: InputStats<*>) {
        val type = config.inputType.getName()
        val optionalBr = if (config.inputType != InputType.LINE) "" else "\n"
        println("Total ${type}s: ${stats.totalCount}.")
        println("The ${config.inputType.getWordMax()} $type: $optionalBr${stats.maxElement}" +
                "$optionalBr (${stats.times} time(s), ${stats.percentage}%).")
    }
}
