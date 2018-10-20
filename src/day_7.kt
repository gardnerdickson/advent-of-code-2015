import java.io.File
import java.util.regex.Matcher

fun main(args: Array<String>) {
    val filename = "res/day_7_input.txt"
    val answer1 = Day7.part1(File(filename))
    println("Part 1: $answer1")
    val answer2 = Day7.part2(File(filename), answer1)
    println("Part 2: $answer2")
}

object Day7 {

    fun part1(file: File): Int {
        val wires = mutableMapOf<String, Gate>()
        file.useLines { lines ->
            lines.map { Gate.parse(it) }.forEach { wires[it.target] = it }
        }
        return wires["a"]!!.evaluate(wires)
    }

    fun part2(file: File, a: Int): Int {
        val wires = mutableMapOf<String, Gate>()
        file.useLines { lines ->
            lines.map { Gate.parse(it) }.forEach { wires[it.target] = it }
        }
        wires["b"]!!.wireValue = a
        return wires["a"]!!.evaluate(wires)
    }

}

sealed class Gate(val target: String) {

    companion object {

        private val andRegex = Regex("(?<left>[\\w\\d]+) AND (?<right>[\\w\\d]+) -> (?<target>\\w+)").toPattern()
        private val orRegex = Regex("(?<left>[\\w\\d]+) OR (?<right>[\\w\\d]+) -> (?<target>\\w+)").toPattern()
        private val lshiftRegex = Regex("(?<left>[\\w\\d]+) LSHIFT (?<right>[\\d]+) -> (?<target>\\w+)").toPattern()
        private val rshiftRegex = Regex("(?<left>[\\w\\d]+) RSHIFT (?<right>[\\d]+) -> (?<target>\\w+)").toPattern()
        private val notRegex = Regex("NOT (?<source>[\\w]+) -> (?<target>\\w+)").toPattern()
        private val signalRegex = Regex("(?<source>[\\w\\d]+) -> (?<target>\\w+)").toPattern()

        operator fun Matcher.get(name: String): String {
            return this.group(name)
        }

        fun parse(instruction: String): Gate {
            var matcher = andRegex.matcher(instruction)
            if (matcher.matches()) {
                return AndGate(matcher["left"], matcher["right"], matcher["target"])
            }
            matcher = orRegex.matcher(instruction)
            if (matcher.matches()) {
                return OrGate(matcher["left"], matcher["right"], matcher["target"])
            }
            matcher = lshiftRegex.matcher(instruction)
            if (matcher.matches()) {
                return LshiftGate(matcher["left"], matcher["right"].toInt(), matcher["target"])
            }
            matcher = rshiftRegex.matcher(instruction)
            if (matcher.matches()) {
                return RshiftGate(matcher["left"], matcher["right"].toInt(), matcher["target"])
            }
            matcher = notRegex.matcher(instruction)
            if (matcher.matches()) {
                return NotGate(matcher["source"], matcher["target"])
            }
            matcher = signalRegex.matcher(instruction)
            if (matcher.matches()) {
                return SignalGate(matcher["source"], matcher["target"])
            }
            throw IllegalArgumentException("Instruction '$instruction' could not be parsed")
        }

    }

    var wireValue: Int? = null

    abstract fun evaluate(wires: Map<String, Gate>): Int

}

class AndGate(private val left: String, private val right: String, target: String) : Gate(target) {
    override fun evaluate(wires: Map<String, Gate>): Int {
        if (wireValue == null) {
            wireValue = (left.toIntOrNull() ?: wires[left]!!.evaluate(wires)) and (right.toIntOrNull() ?: wires[right]!!.evaluate(wires))
        }
        return wireValue!!.toInt()
    }
}

class OrGate(private val left: String, private val right: String, target: String) : Gate(target) {
    override fun evaluate(wires: Map<String, Gate>): Int {
        if (wireValue == null) {
            wireValue = (left.toIntOrNull() ?: wires[left]!!.evaluate(wires)) or (right.toIntOrNull() ?: wires[right]!!.evaluate(wires))
        }
        return wireValue!!.toInt()
    }
}

class LshiftGate(private val left: String, private val right: Int, target: String) : Gate(target) {
    override fun evaluate(wires: Map<String, Gate>): Int {
        if (wireValue == null) {
            wireValue = (left.toIntOrNull() ?: wires[left]!!.evaluate(wires)) shl right
        }
        return wireValue!!.toInt()
    }
}

class RshiftGate(private val left: String, private val right: Int, target: String) : Gate(target) {
    override fun evaluate(wires: Map<String, Gate>): Int {
        if (wireValue == null) {
            wireValue = (left.toIntOrNull() ?: wires[left]!!.evaluate(wires)) shr right
        }
        return wireValue!!.toInt();
    }
}

class NotGate(private val source: String, target: String) : Gate(target) {
    override fun evaluate(wires: Map<String, Gate>): Int {
        if (wireValue == null) {
            wireValue = (source.toIntOrNull() ?: wires[source]!!.evaluate(wires)).inv()
        }
        return wireValue!!.toInt()
    }
}

class SignalGate(private val source: String, target: String) : Gate(target) {
    override fun evaluate(wires: Map<String, Gate>): Int {
        if (wireValue == null) {
            wireValue = source.toIntOrNull() ?: wires[source]!!.evaluate(wires)
        }
        return wireValue!!.toInt()
    }
}

