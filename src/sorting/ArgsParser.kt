package sorting

import java.lang.IllegalArgumentException

object ArgsParser {
    private const val dataTypeCmd = "-dataType"
    private const val sortingTypeCmd = "-sortingType"
    private const val inputFileCmd = "-inputFile"
    private const val outputFileCmd = "-outputFile"
    private val commands = listOf(dataTypeCmd, sortingTypeCmd, inputFileCmd, outputFileCmd)
    private lateinit var args: Array<String>

    fun buildConfig(args: Array<String>): Config {
        this.args = args
        val inputType: InputType = parseArgsForInputType()
        val sortingType: SortingType = parseArgsForSortingType()
        parseArgsForInvalidCmds()
        val inputFile: String = parseArgsForFileName(inputFileCmd)
        val outputFile: String = parseArgsForFileName(outputFileCmd)
        val config = Config(inputType, sortingType)
        if (inputFile.isNotEmpty()) {
            config.inputFileName = inputFile
        }
        if (outputFile.isNotEmpty()) {
            config.outputFileName = outputFile
        }
        return config
    }

    private fun parseArgsForFileName(cmd: String): String {
        val idxOfCmd = args.indexOf(cmd)
        if (idxOfCmd == -1) {
            return ""
        }

        return args[parseFindIndexOfArg(idxOfCmd, cmd)]
    }

    private fun parseArgsForInvalidCmds() {
        for (arg in args) {
            if (arg.startsWith("-") && !commands.contains(arg)) {
                println("\"$arg\" is not a valid parameter. It will be skipped.")
            }
        }
    }

    private fun parseArgsForInputType(): InputType {
        val idxOfDataTypeCmd = args.indexOf(dataTypeCmd)
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
        val idxOfSortingTypeCmd = args.indexOf(sortingTypeCmd)
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
