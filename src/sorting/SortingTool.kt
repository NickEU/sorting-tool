package sorting

import java.io.File
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.util.*
import java.util.stream.Stream
import kotlin.streams.toList

class SortingTool {
    private lateinit var config: Config
    private val sc = Scanner(System.`in`)

    fun run(args: Array<String>) {
        try {
            config = ArgsParser.buildConfig(args)
        } catch (e: IllegalArgumentException) {
            return
        }
        val delimiter = if (config.inputType == InputType.LINE) "\\R" else "\\s+"
        val tokens = if (config.inputFileName.isNotEmpty())
            File(config.inputFileName).readText().split(delimiter).stream()
        else sc.useDelimiter(delimiter).tokens()

        when (config.inputType) {
            InputType.WORD -> printResults(tokens.toList())
            InputType.LINE -> printResults(tokens.toList())
            InputType.LONG -> printResults(collectUserInputToLongList(tokens))
        }
    }

    private fun collectUserInputToLongList(tokens: Stream<String>): List<Long> {
        return tokens
                .filter { s ->
                    tryParse(s) != null
                }
                .mapToLong(String::toLong)
                .toList()
    }

    private fun tryParse(token: String): Long? {
        return try {
            token.toLong()
        } catch (e: NumberFormatException) {
            println("\"$token\" is not a long. It will be skipped")
            null
        }
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
            println("${it.key}: ${it.value} time(s), $percentage%")
        }
    }

    private fun <T : Comparable<T>> countOccurrences(elements: List<T>): Map<T, Int> {
        return elements.groupingBy { it }
                .eachCount()
                .entries.sortedWith { a, b ->
                    when {
                        (a.value == b.value) -> a.key.compareTo(b.key)
                        else -> a.value - b.value
                    }
                }.associate { it.toPair() }
    }
}
