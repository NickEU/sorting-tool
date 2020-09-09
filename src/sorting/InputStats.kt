package sorting

class InputStats<T>(input: List<T>, max: T) {
    val totalCount: Int = input.count()
    var maxElement: T = max
    val times: Int = input.filter { n -> n == this.maxElement }.size
    val percentage: Int = (100.0 * times / totalCount).toInt()
}
