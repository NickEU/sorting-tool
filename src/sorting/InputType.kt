package sorting

enum class InputType(private val typeName: String) {
    LONG("number"),
    LINE("line"),
    WORD("word");

    fun getName(): String = typeName
}
