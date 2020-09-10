package sorting

import java.util.*
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
            printElementsByCount(elements)
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
            printElementsByCount(userNums)
        }
    }

    private fun <T : Comparable<T>> printElementsByCount(elements: List<T>) {
        val occurrences = countOccurrences(elements)
        val type = config.inputType.getName()
        val count = elements.count()
        println("Total ${type}s: $count")
        occurrences.entries.forEach { println("${it.key}: ${it.value} time(s), ${(100.0 * it.value / count).toInt()}%") }
    }

    private fun <T : Comparable<T>> countOccurrences(elements: List<T>): Map<T, Int> {
        val result = mutableMapOf<T, Int>()
        for (el in elements) {
            val frequency = result[el] ?: 0
            result[el] = frequency + 1
        }

        return result.entries.sortedBy { o -> o.value }
                .sortedWith(Comparator { a, b ->
                    when {
                        (a.value == b.value) -> a.key.compareTo(b.key)
                        else -> a.value - b.value
                    }
                })
                .associate { it.toPair() }
    }
}
