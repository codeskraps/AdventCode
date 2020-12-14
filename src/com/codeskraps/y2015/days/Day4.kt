package com.codeskraps.y2015.days

import java.math.BigInteger
import java.security.MessageDigest

class Day4 : Day() {
    override val day: String
        get() = "Four"

    override fun partOne(): String = findNumber(5).toString()

    override fun partTwo(): String = findNumber(6).toString()

    private fun findNumber(min: Int): Int {
        var number = 0
        val md = MessageDigest.getInstance("MD5")

        fun md5(input: String): String {
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

        while (true) {
            val first5 = md5(INPUT + number).substring(0, min).toCharArray()
            val zeros = first5.filter { it.isDigit() && Character.getNumericValue(it) == 0 }
            if (zeros.size == min) break
            number++
        }

        return number
    }

    @Suppress("SpellCheckingInspection")
    companion object {
        private const val INPUT = "ckczppom"
    }
}