package com.codeskraps.y2020.days

import com.codeskraps.Main.DEBUG

abstract class Day {

    fun run() {
        kotlin.io.println("**** Day $day ****")

        if (DEBUG) {
            println("**** Part One ****")
            val answerOne = partOne()
            print("\n")
            println(spacing + "ANSWER: " + answerOne)
            print("\n\n")

        } else println("**** Part One - ANSWER: " + partOne())

        if (DEBUG) {
            println("**** Part Two ****")
            val answerTwo = partTwo()
            print("\n")
            println(spacing + "ANSWER: " + answerTwo)
            print("\n\n")

        } else println("**** Part Two - ANSWER: " + partTwo())
    }

    private val spacing: String = "     "

    private fun println(x: String) {
        kotlin.io.println(spacing + x)
    }

    fun debugPrintf(format: String, vararg args: Any?) {
        if (DEBUG) System.out.printf(spacing + spacing + format, *args)
    }

    abstract val day: String
    abstract fun partOne(): String
    abstract fun partTwo(): String
}