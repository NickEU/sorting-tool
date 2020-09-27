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
        val tokens =
                if (config.inputFileName.isNotEmpty())
                    getTokensFromFile(delimiter)
                else sc.useDelimiter(delimiter).tokens()

        when (config.inputType) {
            InputType.WORD -> printResultsSelector(tokens.toList())
            InputType.LINE -> printResultsSelector(tokens.toList())
            InputType.LONG -> printResultsSelector(stringsToListOfLongs(tokens))
        }
    }

    private fun getTokensFromFile(delimiter: String): Stream<String> {
        return File(config.inputFileName).readText().split(delimiter).stream()
    }

    private fun stringsToListOfLongs(tokens: Stream<String>): List<Long> {
        return tokens
                .filter { s ->
                    tryParseToLong(s) != null
                }.mapToLong(String::toLong)
                .toList()
    }

    private fun tryParseToLong(token: String): Long? {
        return try {
            token.toLong()
        } catch (e: NumberFormatException) {
            println("\"$token\" is not a long. It will be skipped")
            null
        }
    }

    private fun <T : Comparable<T>> printResultsSelector(elements: List<T>) {
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
        val result = "Total ${name}s: ${elements.size}.\nSorted data:$separator$sortedElements"
        outputResultsToUser(result)
    }

    private fun <T : Comparable<T>> printElementsByCount(elements: List<T>) {
        val occurrences = countOccurrences(elements)
        val count = elements.count()
        var result = "Total ${config.inputType.getName()}s: $count\n"
        occurrences.entries.forEach {
            val percentage = (100.0 * it.value / count).toInt()
            result += "${it.key}: ${it.value} time(s), $percentage%\n"
        }
        outputResultsToUser(result)
    }

    private fun outputResultsToUser(result: String) {
        if (config.outputFileName.isEmpty()) {
            println(result)
        } else {
            File(config.outputFileName).writeText(result)
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
