@file:Suppress("ConstantConditionIf")

package com.codeskraps.y2020

import com.codeskraps.y2020.days.*
import java.util.*
import java.util.function.Consumer

object Main {

    // Settings
    private const val RUN_ALL = true
    const val DEBUG = false

    @JvmStatic
    fun main(args: Array<String>) {
        val days = ArrayList<Day>()
        days.add(Day1())
        days.add(Day2())
        days.add(Day3())
        //days.add(Day4())

        if (RUN_ALL) {
            days.forEach(Consumer { obj: Day -> obj.run() })
        } else days[days.size - 1].run()
    }
}