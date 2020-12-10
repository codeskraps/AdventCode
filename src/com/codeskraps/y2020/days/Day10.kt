package com.codeskraps.y2020.days

class Day10 : Day() {

    override val day: String
        get() = "Ten"

    private val jolts = arrayListOf<Int>()

    init {
        jolts.addAll(INPUT)
        jolts.add(0)
        jolts.sort()
        jolts.add(jolts.last() + 3)
    }

    override fun partOne(): String {
        var oneJolt = 0
        var threeJolt = 0

        jolts.forEachIndexed { index, jolt ->
            if (index > 0) {
                when (jolt - jolts[index - 1]) {
                    1 -> oneJolt++
                    3 -> threeJolt++
                }
            }
        }

        return (oneJolt * threeJolt).toString()
    }

    override fun partTwo(): String {
        val count = ArrayList<Long>(jolts.size)

        fun find(i: Int, lower: Int): Long {
            return when (lower) {
                1, 2 -> if (jolts[i] - jolts[i - lower] < 4) count[i - lower] else 0L
                3 -> if (i > 2 && jolts[i] - jolts[i - lower] < 4) count[i - lower] else 0L
                else -> 0L
            }
        }

        for (i in jolts.indices) {
            when (i) {
                0, 1 -> count.add(1)
                else -> count.add(find(i, 1) + find(i, 2) + find(i, 3))
            }
        }

        return count.last().toString()
    }

    companion object {
        private val INPUT = arrayOf(
                99,
                104,
                120,
                108,
                67,
                136,
                80,
                44,
                129,
                113,
                158,
                157,
                89,
                60,
                138,
                63,
                35,
                57,
                61,
                153,
                116,
                54,
                7,
                22,
                133,
                130,
                5,
                72,
                2,
                28,
                131,
                123,
                55,
                145,
                151,
                42,
                98,
                34,
                140,
                146,
                100,
                79,
                117,
                154,
                9,
                83,
                132,
                45,
                43,
                107,
                91,
                163,
                86,
                115,
                39,
                76,
                36,
                82,
                162,
                6,
                27,
                101,
                150,
                30,
                110,
                139,
                109,
                1,
                64,
                56,
                161,
                92,
                62,
                69,
                144,
                21,
                147,
                12,
                114,
                18,
                137,
                75,
                164,
                33,
                152,
                23,
                68,
                51,
                8,
                95,
                90,
                48,
                29,
                26,
                165,
                81,
                13,
                126,
                14,
                143,
                15
        )
    }
}