package sorting

import java.util.Scanner
import kotlin.streams.toList

fun main() {
    val userNums = Scanner(System.`in`).tokens()
            .mapToInt(Integer::parseInt).toList()
    getStatsForNumbers(userNums)
}

fun getStatsForNumbers(numbers: List<Int>) {
    val totalCount = numbers.count()
    val max = numbers.max()
    val times = numbers.filter{ n -> n == max }.size
    println("Total numbers: $totalCount.")
    println("The greatest number: $max ($times time(s)).")
}
