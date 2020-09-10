package sorting

import java.util.Scanner
import kotlin.streams.toList

class SortingTool(args: Array<String>) {
    private val config: Config = ArgsParser.buildConfig(args)
    private val sc = Scanner(System.`in`)

    fun run() {
        val whiteSpaceDelimiter = "\\s+"
        val newlineDelimiter = "\\R"
        when (config.inputType) {
            InputType.WORD -> printSortedStrings(whiteSpaceDelimiter)
            InputType.LINE -> printSortedStrings(newlineDelimiter)
            InputType.LONG -> printSortedNumbers()
        }
    }

    private fun printSortedStrings(delimiter: String) {
        val elements = sc.useDelimiter(delimiter)
                .tokens().toList()
        if (config.sortingType == SortingType.NATURAL) {
            val separator = if (config.inputType == InputType.WORD) " " else "\n"
            val name = config.inputType.getName()
            val sortedElements = elements.sorted()
                    .joinToString(separator)

            println("Total ${name}s: ${elements.size}.")
            println("Sorted data:$separator$sortedElements")
        } else if (config.sortingType == SortingType.BY_COUNT) {
            printElementsByCount()
        }
    }

    private fun printSortedNumbers() {
        val userNums = sc.tokens()
                .mapToInt(Integer::parseInt)
                .toList()
        if (config.sortingType == SortingType.NATURAL) {
            val sortedNums = userNums.sorted()
                    .joinToString(" ")
            println("Total numbers: ${userNums.size}.")
            println("Sorted data: $sortedNums")
        } else if (config.sortingType == SortingType.BY_COUNT){
            printElementsByCount()
        }
    }

    private fun printElementsByCount() {
        TODO("Not yet implemented")
    }

    private fun printStats(stats: InputStats<*>) {
        val type = config.inputType.getName()
        val optionalBr = if (config.inputType != InputType.LINE) "" else "\n"
        println("Total ${type}s: ${stats.totalCount}.")
        println("The ${config.inputType.getWordMax()} $type: $optionalBr${stats.maxElement}" +
                "$optionalBr (${stats.times} time(s), ${stats.percentage}%).")
    }
}
