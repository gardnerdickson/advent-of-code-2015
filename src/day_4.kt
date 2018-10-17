import java.security.MessageDigest

fun main(args: Array<String>) {
    val input = "yzbqklnj"
    val answer1 = Day4.part1(input)
    println("Part 1: $answer1")
    val answer2 = Day4.part2(input)
    println("Part 2: $answer2")
}

object Day4 {

    fun part1(key: String): Int {
        var number = 1
        while (true) {
            val hashInput = key.plus(number)
            val hash = getHash(hashInput)
            if (hash.startsWith("00000")) {
                return number
            }
            number++
        }
    }

    fun part2(key: String): Int {
        var number = 1
        while (true) {
            val hashInput = key.plus(number)
            val hash = getHash(hashInput)
            if (hash.startsWith("000000")) {
                return number
            }
            number++
        }
    }

    private fun getHash(input: String): String {
        val bytes = ByteArray(16)
        val digester = MessageDigest.getInstance("MD5")
        digester.update(input.toByteArray())
        digester.digest(bytes, 0, 16)
        return bytes.joinToString(separator = "", transform = { String.format("%02X", (it.toInt() and 0xFF))})
    }

}



