package sorting

enum class InputType(private val typeName: String) {
    LONG("number"),
    LINE("line"),
    WORD("word");

    fun getName(): String = typeName
    fun getWordMax(): String = if (this.name == LONG.name) "greatest" else "longest"
}
