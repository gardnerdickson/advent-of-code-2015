import java.io.File

fun main(args: Array<String>) {
    val filename = "res/day_5_input.txt"
    val answer1 = Day5.part1(File(filename))
    println("Part 1: $answer1")
    val answer2 = Day5.part2(File(filename))
    println("Part 2: $answer2")
}

object Day5 {

    private val vowels = setOf('a', 'e', 'i', 'o', 'u')
    private val blackList = setOf("ab", "cd", "pq", "xy")

    fun part1(file: File): Int {
        var niceStrings = 0
        file.bufferedReader().useLines { lines ->
            lines.forEach {
                if (checkVowels(it) && checkBlackList(it) && checkConsecutiveCharacters(it)) {
                    niceStrings++
                }
            }
        }
        return niceStrings
    }

    fun part2(file: File): Int {
        var niceStrings = 0
        file.bufferedReader().useLines { lines ->
            lines.forEach {
                if (checkConsecutiveRepeatingWithoutOverlap(it) && checkRepeatingCharacters(it)) {
                    niceStrings++
                }
            }
        }
        return niceStrings
    }

    private fun checkVowels(str: String): Boolean {
        return str.filter { vowels.contains(it) }.length >= 3
    }

    private fun checkBlackList(str: String): Boolean {
        blackList.forEach {
            if (str.contains(it)) {
                return false
            }
        }
        return true
    }

    private fun checkConsecutiveCharacters(str: String): Boolean {
        str.forEachIndexed { index, char ->
            if (index == str.length - 1) return false
            if (char == str[index + 1]) return true
        }
        return false
    }

     private fun checkConsecutiveRepeatingWithoutOverlap(str: String): Boolean {
        str.forEachIndexed { index, char ->
            if (index == str.length - 1) return false
            val target = "$char${str[index + 1]}"
            if (Regex("$target\\w*$target").containsMatchIn(str)) {
                return true
            }
        }
        return false
    }

    private fun checkRepeatingCharacters(str: String): Boolean {
        str.forEach {
            if (Regex("$it\\w$it").containsMatchIn(str)) {
                return true
            }
        }
        return false
    }

}
