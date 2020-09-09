package sorting

enum class InputType(private val s: String) {
    LONG("number"),
    LINE("line"),
    WORD("word");

    fun getName(): String = s
    fun getWordMax(): String = if (this.name == LONG.name) "greatest" else "longest"
}
