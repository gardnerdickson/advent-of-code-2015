import java.lang.StringBuilder

fun main(args: Array<String>) {
    val input = "1113222113"
    val answer1 = Day10.part1(input)
    println("Part 1: $answer1")
    val answer2 = Day10.part2(input)
    println("Part 2: $answer2")
}

object Day10 {

    private fun lookAndSay(sequence: String): String {
        val digits = sequence.toCharArray().map { Character.getNumericValue(it) }
        val stringBuilder = StringBuilder()
        var lastDigit = -1
        var currentDigit: Int
        var quantity = 0
        for (i in 0 until digits.size) {
            currentDigit = digits[i]
            if (i == digits.size - 1) {
                if (currentDigit != lastDigit) {
                    stringBuilder.append("$quantity$lastDigit")
                    stringBuilder.append("1$currentDigit")
                } else {
                    stringBuilder.append("${(quantity + 1)}$lastDigit")
                }
            } else if (currentDigit != lastDigit && lastDigit != -1) {
                stringBuilder.append("$quantity$lastDigit")
                quantity = 1
            } else {
                quantity++
            }
            lastDigit = currentDigit
        }
        return stringBuilder.toString()
    }

    fun part1(sequence: String): Int {
        var nextSequence = sequence
        repeat(40) {
            nextSequence = lookAndSay(nextSequence)
        }
        return nextSequence.length
    }

    fun part2(sequence: String): Int {
        var nextSequence = sequence
        repeat(50) {
            nextSequence = lookAndSay(nextSequence)
        }
        return nextSequence.length
    }

}

