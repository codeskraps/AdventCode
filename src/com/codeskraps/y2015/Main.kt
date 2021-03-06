@file:Suppress("ConstantConditionIf")

package com.codeskraps.y2015

import com.codeskraps.y2015.days.*
import java.util.*
import java.util.function.Consumer

object Main {

    // Settings
    private const val RUN_ALL = false
    const val DEBUG = true

    @JvmStatic
    fun main(args: Array<String>) {
        val days = ArrayList<Day>()

        /*-
        days.add(Day1())
        days.add(Day2())
        days.add(Day3())
        days.add(Day4())
        days.add(Day5())*/

        days.add(Day6())

        if (RUN_ALL) {
            days.forEach(Consumer { obj: Day -> obj.run() })
        } else days[days.size - 1].run()
    }
}