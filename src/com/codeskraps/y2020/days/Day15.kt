package com.codeskraps.y2020.days

class Day15 : Day() {
    override val day: String
        get() = "Fifteen"

    override fun partOne(): String = findTheNthSpoken(2020).toString()

    override fun partTwo(): String = findTheNthSpoken(30000000).toString()

    private fun findTheNthSpoken(nth: Int): Int {
        var spoken = Spoken(0, 0, 0)
        val map = HashMap<Int, Spoken>()

        for (i in 1..nth) {
            if (i <= INPUT.size) {
                spoken = Spoken(INPUT[i - 1], i, i)
            } else {
                val was = map[spoken.number] ?: Spoken(0, i, i)
                val number = if (was.fistTurn == was.lastTurn) 0 else spoken.lastTurn - spoken.fistTurn
                spoken = map[number] ?: Spoken(number, i, i)
                spoken.fistTurn = spoken.lastTurn
                spoken.lastTurn = i
            }

            map[spoken.number] = spoken
        }

        return spoken.number
    }

    data class Spoken(val number: Int, var fistTurn: Int, var lastTurn: Int)

    companion object {
        private val INPUT = arrayOf(0, 1, 4, 13, 15, 12, 16)
    }
}