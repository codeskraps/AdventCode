@file:Suppress("ConstantConditionIf")

package com.codeskraps.y2015

import com.codeskraps.y2015.days.Day
import com.codeskraps.y2015.days.Day1
import com.codeskraps.y2015.days.Day2
import com.codeskraps.y2015.days.Day3
import java.util.*
import java.util.function.Consumer

object Main {

    // Settings
    private const val RUN_ALL = false
    const val DEBUG = true

    @JvmStatic
    fun main(args: Array<String>) {
        val days = ArrayList<Day>()

        days.add(Day1())
        days.add(Day2())
        days.add(Day3())

        if (RUN_ALL) {
            days.forEach(Consumer { obj: Day -> obj.run() })
        } else days[days.size - 1].run()
    }
}