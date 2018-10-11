import java.io.File
import kotlin.streams.toList

fun main(args: Array<String>) {
    val input = "res/day_2_input.txt"
    val answer1 = Day2.part1(input)
    println("Part 1: $answer1")
    val answer2 = Day2.part2(input)
    println("Part 2: $answer2")
}


object Day2 {

    fun part1(input: String): Int {
        val reader = File(input).bufferedReader()
        val amounts = reader.lines()
                .map { parsePresent(it) }
                .map { it.surfaceArea() + it.slack() }
                .toList()
        return amounts.sum()
    }

    fun part2(input: String): Int {
        val reader = File(input).bufferedReader()
        val amounts = reader.lines()
                .map { parsePresent(it) }
                .map { it.ribbon() + it.bow() }
                .toList()
        return amounts.sum()
    }

    private fun parsePresent(dimensions: String): Present {
        val components = dimensions.split("x")
        return Present(components[0].toInt(), components[1].toInt(), components[2].toInt())
    }

}


data class Present(val width: Int, val height: Int, val length: Int) {
    fun surfaceArea(): Int {
        return 2 * length * width + 2 * width * height + 2 * height * length
    }

    fun slack(): Int {
        val sorted = listOf(width, height, length).sorted()
        return sorted[0] * sorted[1]
    }

    fun ribbon(): Int {
        val sorted = listOf(width, height, length).sorted()
        return sorted[0] * 2 + sorted[1] * 2
    }

    fun bow(): Int {
        return width * height * length
    }
}
