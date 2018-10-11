import java.io.File


fun main(args: Array<String>) {
    val input = "res/day_1_input.txt"
    val answer1 = Day1.part1(input)
    println("Part 1: $answer1")
    val answer2 = Day1.part2(input)
    println("Part 2: $answer2")
}

object Day1 {
    private const val openBracket = '('.toInt()
    private const val closeBracket = ')'.toInt()

    fun part1(input: String): Int {
        val charReader = File(input).reader()
        var floor = 0
        charReader.use {
            while (true) {
                val char = charReader.read()
                if (char == -1) break
                when (char) {
                    openBracket -> floor++
                    closeBracket -> floor--
                    else -> throw IllegalArgumentException("Input contains invalid character: $char")
                }
            }
        }
        return floor
    }

    fun part2(input: String): Int {
        val charReader = File(input).reader()
        var floor = 0
        var index = 1
        charReader.use {
            while (true) {
                val char = charReader.read()
                if (char == -1) break
                when (char) {
                    openBracket -> floor++
                    closeBracket -> floor--
                    else -> throw IllegalArgumentException("Input contains invalid character: $char")
                }
                if (floor < 0) {
                    return index
                }
                index++
            }
        }
        throw RuntimeException("Floor never goes negative")
    }

}




