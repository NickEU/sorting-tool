package sorting

import java.lang.IllegalArgumentException

class ConfigFromArgs(private val args: Array<String>) {
    val inputType: InputType = parseArgsForInputType()
    val sortingType: SortingType = parseArgsForSortingType()

    private fun parseArgsForInputType(): InputType {
        val idxOfDataTypeCmd = args.indexOf("-dataType")
        if (idxOfDataTypeCmd == -1) {
            return InputType.WORD
        }

        val argIdx: Int = parseFindIndexOfArg(idxOfDataTypeCmd)

        return when (args[argIdx]) {
            "long" -> InputType.LONG
            "line" -> InputType.LINE
            "word" -> InputType.WORD

            else -> InputType.WORD
        }
    }

    private fun parseArgsForSortingType(): SortingType {
        val idxOfSortingTypeCmd = args.indexOf("-sortingType")
        if (idxOfSortingTypeCmd == -1) {
            return SortingType.NATURAL
        }

        val argIdx: Int = parseFindIndexOfArg(idxOfSortingTypeCmd)

        return when (args[argIdx]) {
            "natural" -> SortingType.NATURAL
            "byCount" -> SortingType.BY_COUNT

            else -> throw IllegalArgumentException()
        }
    }

    private fun parseFindIndexOfArg(idxOfCmd: Int): Int {
        val potentialArgIdx = idxOfCmd + 1
        if (args.lastIndex == potentialArgIdx) {
            throw IllegalArgumentException()
        }

        return potentialArgIdx
    }
}
