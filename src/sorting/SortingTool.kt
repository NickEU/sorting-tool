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
            InputType.WORD -> printResults(collectUserInputToStrList(whiteSpaceDelimiter))
            InputType.LINE -> printResults(collectUserInputToStrList(newlineDelimiter))
            InputType.LONG -> printResults(collectUserInputToIntList())
        }
    }

    private fun collectUserInputToIntList(): List<Int> {
        return sc.tokens()
                .mapToInt(Integer::parseInt)
                .toList()
    }
    private fun collectUserInputToStrList(delimiter: String): List<String> {
        return sc.useDelimiter(delimiter)
                .tokens().toList()
    }

    private fun <T : Comparable<T>> printResults(elements: List<T>) {
        when (config.sortingType) {
            SortingType.NATURAL -> printElementsNatural(elements)
            SortingType.BY_COUNT -> printElementsByCount(elements)
        }
    }

    private fun <T : Comparable<T>> printElementsNatural(elements: List<T>) {
        val separator = if (config.inputType == InputType.LINE) "\n" else " "
        val name = config.inputType.getName()
        val sortedElements = elements.sorted()
                .joinToString(separator)
        println("Total ${name}s: ${elements.size}.")
        println("Sorted data:$separator$sortedElements")
    }

    private fun <T : Comparable<T>> printElementsByCount(elements: List<T>) {
        val occurrences = countOccurrences(elements)
        val count = elements.count()
        println("Total ${config.inputType.getName()}s: $count")
        occurrences.entries.forEach {
            val percentage = (100.0 * it.value / count).toInt()
            println("${it.key}: ${it.value} time(s)," +
                    " $percentage%")
        }
    }

    private fun <T : Comparable<T>> countOccurrences(elements: List<T>): Map<T, Int> {
        val result = mutableMapOf<T, Int>()
        for (el in elements) {
            val frequency = result[el] ?: 0
            result[el] = frequency + 1
        }

        return result.entries
                .sortedWith(Comparator { a, b ->
                    when {
                        (a.value == b.value) -> a.key.compareTo(b.key)
                        else -> a.value - b.value
                    }
                }).associate { it.toPair() }
    }
}
