package sorting

import java.lang.IllegalArgumentException

object ArgsParser {
    private lateinit var args: Array<String>

    fun buildConfig(args: Array<String>): Config {
        this.args = args
        val inputType: InputType = parseArgsForInputType()
        val sortingType: SortingType = parseArgsForSortingType()
        parseArgsForInvalidCommands()
        val inputFile: String = parseArgsForFileName(ValidCommands.INPUT_FILE.cmd)
        val outputFile: String = parseArgsForFileName(ValidCommands.OUTPUT_FILE.cmd)
        return Config(inputType, sortingType, inputFile, outputFile)
    }

    private fun parseArgsForFileName(cmd: String): String {
        val idxOfCmd = args.indexOf(cmd)
        if (idxOfCmd == -1) {
            return ""
        }

        return args[parseFindIndexOfArg(idxOfCmd, cmd)]
    }

    private fun parseArgsForInvalidCommands() {
        for (arg in args) {
            if (arg.startsWith("-") && ValidCommands.values().none { it.cmd == arg }) {
                println("\"$arg\" is not a valid parameter. It will be skipped.")
            }
        }
    }

    private fun parseArgsForInputType(): InputType {
        val idxOfDataTypeCmd = args.indexOf(ValidCommands.DATA_TYPE.cmd)
        if (idxOfDataTypeCmd == -1) {
            return InputType.WORD
        }

        val argIdx: Int = parseFindIndexOfArg(idxOfDataTypeCmd, "data")

        return when (args[argIdx]) {
            "long" -> InputType.LONG
            "line" -> InputType.LINE
            "word" -> InputType.WORD

            else -> InputType.WORD
        }
    }

    private fun parseArgsForSortingType(): SortingType {
        val idxOfSortingTypeCmd = args.indexOf(ValidCommands.SORTING_TYPE.cmd)
        if (idxOfSortingTypeCmd == -1) {
            return SortingType.NATURAL
        }

        val argIdx: Int = parseFindIndexOfArg(idxOfSortingTypeCmd, "sorting")

        return when (args[argIdx]) {
            "natural" -> SortingType.NATURAL
            "byCount" -> SortingType.BY_COUNT

            else -> throw IllegalArgumentException()
        }
    }

    private fun parseFindIndexOfArg(idxOfCmd: Int, cmdType: String): Int {
        val potentialArgIdx = idxOfCmd + 1
        if (args.lastIndex < potentialArgIdx || args[potentialArgIdx].startsWith("-")) {
            println("No $cmdType type defined!")
            throw IllegalArgumentException()
        }

        return potentialArgIdx
    }

}
