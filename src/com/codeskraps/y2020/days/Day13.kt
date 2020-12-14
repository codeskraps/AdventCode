package com.codeskraps.y2020.days

import java.math.BigInteger

class Day13 : Day() {
    override val day: String
        get() = "Thirteen"

    override fun partOne(): String {
        val arrival = INPUT[0].toInt()
        val buses = INPUT[1].split(",").filter { it != "x" }
        var wait = Integer.MAX_VALUE
        var busID = 0

        buses.forEach { bus ->
            var time = 0
            while (time <= arrival) {
                time += bus.toInt()
            }
            if (time - arrival < wait) {
                busID = bus.toInt()
                wait = time - arrival
            }
        }

        return (busID * wait).toString()
    }

    override fun partTwo(): String {
        val buses = INPUT[1].split(",")
        var valid = false
        var adder = buses[0].toBigInteger()
        var time = adder
        var hits = 0

        while (!valid) {
            valid = true
            var h = 0

            var index = 0
            while (index < buses.size) {
                val bus = buses[index]
                if (bus != "x") {
                    if ((time.add(index.toBigInteger())) % bus.toBigInteger() != BigInteger.ZERO) {
                        valid = false
                        break
                    } else {
                        h++
                    }
                }
                index++
            }

            if (h > hits) {
                hits = h
                adder = BigInteger.ZERO
                buses.filter{it != "x"}.subList(0, h).forEach {
                    if (adder == BigInteger.ZERO) adder = it.toBigInteger()
                    else adder *= it.toBigInteger()
                }
            }

            if (valid) break

            time += adder
        }

        return time.toString()
    }

    companion object {
        private val INPUT = arrayOf(
                "1000340",
                "13,x,x,x,x,x,x,37,x,x,x,x,x,401,x,x,x,x,x,x,x,x,x,x,x,x,x,17,x,x,x,x,19,x,x,x,23,x,x,x,x,x,29,x,613,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,41"
        )
    }
}