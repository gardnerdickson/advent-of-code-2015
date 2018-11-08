import java.io.File
import java.net.URLDecoder
import java.net.URLEncoder

fun main(args: Array<String>) {
    val input = File("res/day_8_input.txt")
    println("Part 1: ${Day8.part1(input.readLines())}")
    println("Part 2: ${Day8.part2(input.readLines())}")
}

object Day8 {

    private val hexRegex = "\\\\x([0-9a-f]{2})".toRegex()

    fun part1(lines: List<String>): Int {
        var totalCodeLength = 0
        var totalInMemoryLength = 0
        for (line in lines) {
            totalCodeLength += line.length
            totalInMemoryLength += line.replace("\\\\", "_").replace("\\\"", "_").replace(hexRegex, "_").length - 2
        }
        return totalCodeLength - totalInMemoryLength
    }

    private val slashRegex = Regex("\\\\(?!\\\\x([0-9a-f]{2}))")
    private val quoteRegex = Regex("\"")

    private const val slashReplacer = "[SLASH]"
    private const val quoteReplacer = "[QUOTE]"

    fun part2(lines: List<String>): Int {
        var totalCodeLength = 0
        var totalEncodedLength = 0
        for (line in lines) {
            var encodedLine = line
            // Add placeholders to line to avoid encoded patterns from getting caught and encoded again
            slashRegex.findAll(line).forEach {
                encodedLine = encodedLine.replace(it.value, slashReplacer)
            }
            quoteRegex.findAll(line).forEach {
                encodedLine = encodedLine.replace(it.value, quoteReplacer)
            }

            // Go through the line and replace the placeholders with the encodings
            encodedLine = "\"${encodedLine.replace(slashReplacer, "\\\\").replace(quoteReplacer, "\\\"")}\""

            totalEncodedLength += encodedLine.length
            totalCodeLength += line.length
        }
        return totalEncodedLength - totalCodeLength
    }

}


