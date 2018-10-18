import java.io.File

fun main(args: Array<String>) {
    val filename = "res/day_6_input.txt"
    val answer1 = Day6.part1(File(filename))
    println("Part 1: $answer1")
    val answer2 = Day6.part2(File(filename))
    println("Part 2: $answer2")
}


object Day6 {

    fun part1(file: File): Int {
        val lights = Array(1000) { BooleanArray(1000) }
        file.bufferedReader().useLines { lines ->
            lines.forEach {
                val command = OnOffCommand.parse(it)
                command.execute(lights)
            }
        }
//        printLights(lights)
        return findOnLights(lights)
    }

    fun part2(file: File): Int {
        val lights = Array(1000) { IntArray(1000) }
        file.bufferedReader().useLines { lines ->
            lines.forEach {
                val command = BrightnessCommand.parse(it)
                command.execute(lights)
            }
        }
        return findTotalBrightness(lights)

    }

    private fun findOnLights(arr: Array<BooleanArray>): Int {
        var onLights = 0
        for (x in 0 until arr.size) {
            for (y in 0 until arr[x].size) {
                if (arr[x][y]) {
                    onLights++
                }
            }
        }
        return onLights
    }

    private fun printLights(arr: Array<BooleanArray>) {
        for (x in 0 until arr.size) {
            for (y in 0 until arr[x].size) {
                print(if (arr[x][y]) "*" else " ")
            }
            println()
        }
    }

    private fun findTotalBrightness(arr: Array<IntArray>): Int {
        var brightness = 0
        for (x in 0 until arr.size) {
            for (y in 0 until arr[x].size) {
                brightness += arr[x][y]
            }
        }
        return brightness
    }
}


sealed class OnOffCommand(private val startX: Int,
                          private val startY: Int,
                          private val endX: Int,
                          private val endY: Int) {

    companion object {

        private val turnOffRegex = Regex("turn off (?<startX>\\d+),(?<startY>\\d+) through (?<endX>\\d+),(?<endY>\\d+)")
        private val turnOnRegex = Regex("turn on (?<startX>\\d+),(?<startY>\\d+) through (?<endX>\\d+),(?<endY>\\d+)")
        private val toggleRegex = Regex("toggle (?<startX>\\d+),(?<startY>\\d+) through (?<endX>\\d+),(?<endY>\\d+)")

        fun parse(command: String): OnOffCommand {
            var matcher = turnOffRegex.toPattern().matcher(command)
            if (matcher.matches()) {
                return TurnOffCommand(matcher.group("startX").toInt(), matcher.group("startY").toInt(), matcher.group("endX").toInt(), matcher.group("endY").toInt())
            }

            matcher = turnOnRegex.toPattern().matcher(command)
            if (matcher.matches()) {
                return TurnOnCommand(matcher.group("startX").toInt(), matcher.group("startY").toInt(), matcher.group("endX").toInt(), matcher.group("endY").toInt())
            }

            matcher = toggleRegex.toPattern().matcher(command)
            if (matcher.matches()) {
                return ToggleCommand(matcher.group("startX").toInt(), matcher.group("startY").toInt(), matcher.group("endX").toInt(), matcher.group("endY").toInt())
            }

            throw IllegalArgumentException("Invalid command string: $command")
        }
    }

    abstract fun execute(arr: Array<BooleanArray>)

    fun traverseArray(arr: Array<BooleanArray>, func: (Boolean) -> Boolean) {
        for (x in startX..endX) {
            for (y in startY..endY) {
                arr[x][y] = func(arr[x][y])
            }
        }
    }

}

class TurnOffCommand(startX: Int, startY: Int, endX: Int, endY: Int) : OnOffCommand(startX, startY, endX, endY) {
    override fun execute(arr: Array<BooleanArray>) {
        traverseArray(arr) { false }
    }
}

class TurnOnCommand(startX: Int, startY: Int, endX: Int, endY: Int) : OnOffCommand(startX, startY, endX, endY) {
    override fun execute(arr: Array<BooleanArray>) {
        traverseArray(arr) { true }
    }
}

class ToggleCommand(startX: Int, startY: Int, endX: Int, endY: Int) : OnOffCommand(startX, startY, endX, endY) {
    override fun execute(arr: Array<BooleanArray>) {
        traverseArray(arr) { light -> !light }
    }
}

sealed class BrightnessCommand(private val startX: Int,
                               private val startY: Int,
                               private val endX: Int,
                               private val endY: Int) {

    companion object {

        private val turnOffRegex = Regex("turn off (?<startX>\\d+),(?<startY>\\d+) through (?<endX>\\d+),(?<endY>\\d+)")
        private val turnOnRegex = Regex("turn on (?<startX>\\d+),(?<startY>\\d+) through (?<endX>\\d+),(?<endY>\\d+)")
        private val toggleRegex = Regex("toggle (?<startX>\\d+),(?<startY>\\d+) through (?<endX>\\d+),(?<endY>\\d+)")

        fun parse(command: String): BrightnessCommand {
            var matcher = turnOffRegex.toPattern().matcher(command)
            if (matcher.matches()) {
                return TurnDownCommand(matcher.group("startX").toInt(), matcher.group("startY").toInt(), matcher.group("endX").toInt(), matcher.group("endY").toInt())
            }

            matcher = turnOnRegex.toPattern().matcher(command)
            if (matcher.matches()) {
                return TurnUpCommand(matcher.group("startX").toInt(), matcher.group("startY").toInt(), matcher.group("endX").toInt(), matcher.group("endY").toInt())
            }

            matcher = toggleRegex.toPattern().matcher(command)
            if (matcher.matches()) {
                return TurnUpBy2Command(matcher.group("startX").toInt(), matcher.group("startY").toInt(), matcher.group("endX").toInt(), matcher.group("endY").toInt())
            }

            throw IllegalArgumentException("Invalid command string: $command")
        }
    }

    abstract fun execute(arr: Array<IntArray>)

    fun traverseArray(arr: Array<IntArray>, func: (Int) -> Int) {
        for (x in startX..endX) {
            for (y in startY..endY) {
                arr[x][y] = func(arr[x][y])
            }
        }
    }

}


class TurnUpCommand(startX: Int, startY: Int, endX: Int, endY: Int) : BrightnessCommand(startX, startY, endX, endY) {
    override fun execute(arr: Array<IntArray>) {
        traverseArray(arr) { light -> light + 1 }
    }
}

class TurnDownCommand(startX: Int, startY: Int, endX: Int, endY: Int) : BrightnessCommand(startX, startY, endX, endY) {
    override fun execute(arr: Array<IntArray>) {
        traverseArray(arr) { light -> if (light - 1 > 0) light - 1 else 0 }
    }
}

class TurnUpBy2Command(startX: Int, startY: Int, endX: Int, endY: Int) : BrightnessCommand(startX, startY, endX, endY) {
    override fun execute(arr: Array<IntArray>) {
        traverseArray(arr) { light -> light + 2 }
    }
}

