import java.io.File

fun main(args: Array<String>) {
    val input = "res/day_3_input.txt"
    val answer1 = Day3.part1(input)
    println("Part 1: $answer1")
    val answer2 = Day3.part2(input)
    println("Part 2: $answer2")
}

object Day3 {

    private enum class Turn {
        SANTA, ROBO_SANTA;

        fun nextTurn(): Turn {
            return when (this) {
                Turn.SANTA -> Turn.ROBO_SANTA
                else -> Turn.SANTA
            }
        }
    }

    fun part1(input: String): Int {
        val directions = File(input).readText().toCharArray()
        var currentPosition = Position(0, 0)
        val positions = mutableSetOf<Position>()
        positions.add(currentPosition)
        for (direction in directions) {
            val nextPosition = currentPosition.move(direction)
            positions.add(nextPosition)
            currentPosition = nextPosition
        }
        return positions.size
    }

    fun part2(input: String): Int {
        val directions = File(input).readText().toCharArray()
        var currentSantaPosition = Position(0, 0)
        var currentRoboSantaPosition = Position(0, 0)
        val positions = mutableSetOf<Position>()
        positions.add(currentSantaPosition)

        var turn = Turn.SANTA
        for (direction in directions) {
            when (turn) {
                Turn.SANTA -> {
                    val nextPosition = currentSantaPosition.move(direction)
                    positions.add(nextPosition)
                    currentSantaPosition = nextPosition

                }
                Turn.ROBO_SANTA -> {
                    val nextPosition = currentRoboSantaPosition.move(direction)
                    positions.add(nextPosition)
                    currentRoboSantaPosition = nextPosition
                }
            }
            turn = turn.nextTurn()
        }

        return positions.size
    }

}


data class Position(val x: Int, val y: Int) {
    fun move(direction: Char): Position {
        return when (direction) {
            '^' -> Position(this.x, this.y + 1)
            '>' -> Position(this.x + 1, this.y)
            'v' -> Position(this.x, this.y - 1)
            '<' -> Position(this.x - 1, this.y)
            else -> throw IllegalArgumentException("Input contained invalid character: $direction")
        }
    }
}

