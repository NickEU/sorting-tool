package sorting

import java.lang.IllegalArgumentException
import java.util.Scanner
import kotlin.streams.toList

class SortingTool(args: Array<String>) {
    private val inputType: InputType
    private val sortingType: SortingType
    private val sc = Scanner(System.`in`)
    private var performSort = false

    init {
        inputType = parseArgsForInputType(args)
        sortingType = parseArgsForSortingType(args)
    }

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
        val type = inputType.getName()
        val optionalBr = if (inputType != InputType.LINE) "" else "\n"
        println("Total ${type}s: ${stats.totalCount}.")
        println("The ${inputType.getWordMax()} $type: $optionalBr${stats.maxElement}" +
                "$optionalBr (${stats.times} time(s), ${stats.percentage}%).")
    }

    private fun parseArgsForInputType(args: Array<String>): InputType {
        if (args.size < 2 || args[0] != "-dataType") {
            return InputType.WORD
        }

        return when (args[1]) {
            "long" -> InputType.LONG
            "line" -> InputType.LINE
            "word" -> InputType.WORD

            else -> InputType.WORD
        }
    }

    private fun parseArgsForSortingType(args: Array<String>): SortingType {
        val idxOfTypeCmd = args.indexOf("-sortingType")
        if (idxOfTypeCmd == -1) {
            return SortingType.NATURAL
        }

        if (args.lastIndex == idxOfTypeCmd + 1) {
            throw IllegalArgumentException()
        }

        return when (args[idxOfTypeCmd + 1]) {
            "natural" -> SortingType.NATURAL
            "byCount" -> SortingType.BY_COUNT
            else -> throw IllegalArgumentException()
        }
    }
}
