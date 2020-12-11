package com.codeskraps.y2020.days

class Day11 : Day() {
    override val day: String
        get() = "Eleven"

    override fun partOne(): String {
        var seats = INPUT
        val copySeats = copy(seats)

        var changed = true

        while (changed) {
            changed = false

            for (x in seats.indices) {
                val row = seats[x]
                for (y in row.indices) {
                    when (row[y]) {
                        EMPTY -> copySeats[x][y] = if (adjacentEmpty(x, y, seats) == 8) OCCUPIED else seats[x][y]
                        OCCUPIED -> copySeats[x][y] = if (adjacentEmpty(x, y, seats) <= 4) EMPTY else seats[x][y]
                    }
                    if (!changed && copySeats[x][y] != seats[x][y]) changed = true
                }
            }

            seats = copy(copySeats)
        }

        var count = 0
        seats.forEach { row ->
            count += row.filter { it == OCCUPIED }.size
        }

        return count.toString()
    }

    private fun adjacentEmpty(x: Int, y: Int, seats: Array<CharArray>): Int {
        val empty = arrayOf(true, true, true, true, true, true, true, true)
        empty[0] = x == 0 || y == 0 || seats[x - 1][y - 1] != OCCUPIED
        empty[1] = x == 0 || y == seats[x].size - 1 || seats[x - 1][y + 1] != OCCUPIED
        empty[2] = x == 0 || seats[x - 1][y] != OCCUPIED
        empty[3] = y == 0 || seats[x][y - 1] != OCCUPIED
        empty[4] = y == seats[x].size - 1 || seats[x][y + 1] != OCCUPIED
        empty[5] = y == 0 || x == seats.size - 1 || seats[x + 1][y - 1] != OCCUPIED
        empty[6] = x == seats.size - 1 || seats[x + 1][y] != OCCUPIED
        empty[7] = x == seats.size - 1 || y == seats[x].size - 1 || seats[x + 1][y + 1] != OCCUPIED
        return empty.filter { it }.size
    }

    private fun copy(seats: Array<CharArray>): Array<CharArray> {
        var c = arrayOf<CharArray>()
        for (x in seats.indices) {
            val array = CharArray(seats[x].size)
            for (y in seats[x].indices) {
                array[y] = seats[x][y]
            }
            c += array
        }
        return c
    }

    override fun partTwo(): String {
        var seats = INPUT
        val copySeats = copy(seats)

        var changed = true

        while (changed) {
            changed = false

            for (x in seats.indices) {
                val row = seats[x]
                for (y in row.indices) {
                    when (row[y]) {
                        EMPTY -> copySeats[x][y] = if (adjacentEmptyPartTwo(x, y, seats) == 8) OCCUPIED else seats[x][y]
                        OCCUPIED -> copySeats[x][y] = if (adjacentEmptyPartTwo(x, y, seats) < 4) EMPTY else seats[x][y]
                    }
                    if (!changed && copySeats[x][y] != seats[x][y]) changed = true
                }
            }

            seats = copy(copySeats)
        }

        var count = 0
        seats.forEach { row ->
            count += row.filter { it == OCCUPIED }.size
        }

        return count.toString()
    }

    private fun adjacentEmptyPartTwo(x: Int, y: Int, seats: Array<CharArray>): Int {
        val empty = arrayOf(true, true, true, true, true, true, true, true)
        empty[0] = findFirstSeat(x, y, -1, -1, seats) != OCCUPIED
        empty[1] = findFirstSeat(x, y, -1, 1, seats) != OCCUPIED
        empty[2] = findFirstSeat(x, y, -1, 0, seats) != OCCUPIED
        empty[3] = findFirstSeat(x, y, 0, -1, seats) != OCCUPIED
        empty[4] = findFirstSeat(x, y, 0, 1, seats) != OCCUPIED
        empty[5] = findFirstSeat(x, y, 1, -1, seats) != OCCUPIED
        empty[6] = findFirstSeat(x, y, 1, 0, seats) != OCCUPIED
        empty[7] = findFirstSeat(x, y, 1, 1, seats) != OCCUPIED
        return empty.filter { it }.size
    }

    private fun findFirstSeat(x: Int, y: Int, dx: Int, dy: Int, seats: Array<CharArray>): Char {
        var px = x
        var py = y

        if (px == 0 && dx == -1) return FLOOR
        if (px == seats.size - 1 && dx == 1) return FLOOR
        if (py == 0 && dy == -1) return FLOOR
        if (py == seats[px].size - 1 && dy == 1) return FLOOR

        px += dx
        py += dy

        while (seats[px][py] == FLOOR) {

            if (px == 0 && dx == -1) return FLOOR
            if (px == seats.size - 1 && dx == 1) return FLOOR
            if (py == 0 && dy == -1) return FLOOR
            if (py == seats[px].size - 1 && dy == 1) return FLOOR

            px += dx
            py += dy
        }

        return seats[px][py]
    }

    @Suppress("SpellCheckingInspection")
    companion object {
        private const val FLOOR = '.'
        private const val EMPTY = 'L'
        private const val OCCUPIED = '#'
        private val INPUT = arrayOf(
                "LLLLL.L.LLLLLLL.LL.LLLLL.LLLLLLL..LLLLL.LLLLL.L..LLLLL..LLLLLLLL.LLLL.LLL.LLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL..LLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLL.LL.LLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLL.LLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLL.LLLLLL.LLLL.LLLLL".toCharArray(),
                "LLLLLL.LLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.L.LLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLL.LLLLLL.LLLL.LLLLL".toCharArray(),
                "LLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLL.LLL.L.LLLLLLLL.LLLLLLLLL.LLLLLLLL..LLLLLLLLLLLLL.LLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLL.LLLLLL.LLLLL.LL.LLLLLL.LLLLL.LL.LLLLLLLLL..LLLLLLLLLLLLLLLL".toCharArray(),
                "L.....L.....LL.LL.L.L..L............L.L.L.........L...L...LLLL..L.....L.L.L.....L.LLLL.....L".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLL..LLLLL.LLLLLLLL.LLLLL.LLL.LLLLL.LLLLLLLLL.LLLLLLLLLL.LLLLLL".toCharArray(),
                ".LLLLLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLL.LLL.LLLLL.LLLLLLLLL.LLLLLL.LLLLL.L.LL".toCharArray(),
                "LLLL.LL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLL".toCharArray(),
                "LL.LLL..LLLLL.LLLLLLL.L.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLLLLLLLL..LLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL..LLLLLLL..LLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLL..LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLLLL.LLL.LLLL.LLL.LLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLL".toCharArray(),
                "LLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLL.LL.LLLLLLLLLLLLLLLLL.LLL.LLLLLLLLL.L.LLLL.LLLL.LLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLL.LLLLLL.LLLLL.LLLLLLLL.L.LLLLLLLLLLLLLLLL..LLLLLL.LLLLLLLLLL".toCharArray(),
                "L..L..L.L.LLL.....L......LL.L.LL.L.LL....L.....LL..LL..LL...L....L.L.L......L..L.L.LL..LLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLL.L.LLLL.LLLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLLLLLLLL.LLLLLLLLL.LLLLLLL..LLLLLLLL.LLLLLL..LLLLLLLLLLLLLL.L.LLLLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.L.LLLLLL.LLLLLLLL.L".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLLLLLL.LLLL.LLLLLL.LLLLLLL.LLLLL.LLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.".toCharArray(),
                "LLLLL.LLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLL..LLLLLLLL.LLL.L.LLLLLL.LLL.LLLLLLLL.LLLLLL..LLLLLLLLL".toCharArray(),
                "LLLLL.LLLLL.L.LLLLL.LLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLL.LLL.LLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLL.LLL.LL.LLLLLLLLLL".toCharArray(),
                "..LL..L.L..LL..L..LL...LLL.L.L.L.......L........L........L.L......L.L..LL...L..LLLLL..L.....".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLL.L..L.LLLLLL.LLLLLL.LLLLL.LLLLLLLL.LLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLLLLL.LL.LLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.L.LLLLLLL.LLLLLLL.LLL.LLLL.LLLLLLL.LL".toCharArray(),
                "LL.LLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLL.LL.L".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLL.LLLLL.LLLLLLLLLLLL.LLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLL.LLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLL.LLLLLL.LLLLLLLL".toCharArray(),
                "..LLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLL.LLLLL.LLLLL.LLL.LLLLLLLLL.LLL.LL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "....L...L.....L.....LLLL......L.L......L........LL...L....LLLL....LL......LL...........L.L.L".toCharArray(),
                "LLLLLLL.LLLLLLLLLLLL.LL.L.LL.LLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LL.LL..LLLLLLLLLL".toCharArray(),
                "LLLL.LL.L.LLL.LLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.L.LLL.LLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLL.LLLL".toCharArray(),
                "LLLLLLL.LLLLL..LLLLLLLL.LLLLLLLL.LLLLL..LLLLL.LLL.LLLLLLLLLLLLLL.LLLLLLLLL.LLLL.LLLLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLL.LLLLL...LLLLLLLLLL.LL.LLLLLLLLLLLLLLLLL.L..LLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLLLLLLLLLLLL..LLL.LL..LLL....LLLLLLLL.LLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLL.LLLLL.LLLL".toCharArray(),
                "LLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLL..LLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LL.LLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLL.LLLLLLL.L.LLLLLLLLLLLLLLLLLLLLLLLLLLL".toCharArray(),
                "L.LLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLLLL.LLL.LLLLL.LLLL.LLLL.LLLLLLLLL.LL.LL..LLLLLLLLLL".toCharArray(),
                "LL......L.....LL....L.L..L.L.L..L..L......LL...L.L.L.LL..LLLL.LLL....L..L...L...L..L.LLL..L.".toCharArray(),
                "LLLLLL.LL.LLLLLLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLL.LLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLL.LLLLLLLLLLLLLLLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLL..LLLLLLLL.LLLLLLLLLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLL".toCharArray(),
                "LLLL.LL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.L.LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLL.LLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL..LLLL.LLLLLLLLLL.LLLLLL.LLL.LL.L.LLLLLLLL.L".toCharArray(),
                "LLLLLLL.LLLLL.LLLL.LLLLLLLLLLLLL.LL..LLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.L".toCharArray(),
                "LLLLL.LLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLL..LLLLLLLL.LLLLLLLLLLLLLL.LLL.LLLL.LLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLL".toCharArray(),
                ".....L....L.......L...L..LL.LL.LL.L.L.L..L..L..LLL..L.......LL.LL..LL......LL.L..L....L.....".toCharArray(),
                "LL.LLLL.LLLLL.LLLLLLLLL.LLLLL..LLLLLLLL..LLLLLLL.LLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLL.LLLL..LLLL".toCharArray(),
                "LLLLL.L.LLLLL.LLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLL".toCharArray(),
                "LLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLLL..LLLLLLLLLLLLLL.LLLLLLLLL.L.LLLLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.L.LLL.LL.LLLLLL.LLLLLLLL.LL..LLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                ".....LL.....L.L...............L..........LLL................LLLL.....L.L............L..L..LL".toCharArray(),
                "LLLLLL..LLL.L.LLLLLLLLLLLLLL.LLL.LL.LLL.LLLLLLLLLLL.LL.LLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLL".toCharArray(),
                "LLL.LLL.LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLL.LLLL.LLLLL.LLLLLLLLLL..LLLLL.LLLLLLLL.L".toCharArray(),
                "LLLLLL..LLLLL.LLLL.LLLL.LLLLLLLL.LLLL.L.LLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLLLLLL.LLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLL..LLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLL.L.LLLLLL.LLLLL.LLLLLLLLL..LLL.LLLLLLLLLLLLLLLLLLLLLL".toCharArray(),
                ".L.L.L.......L...LL....LLL.L.....LL..L.L..LLLL......L.LL......L.L.L.....LL...L....L..L.LL..L".toCharArray(),
                "LLLLLLL.L.LLL.LLLLLL.LL.LLLL.LLL.LLLLLL.LLLLLLLL.L..L.L.LLLLLLLL.LLLLLLLLL.LLL..LLLLL.LLLLLL".toCharArray(),
                "LLLLLL..LLLLL.LLLLLLLLL.L.LLLLLL.LLLLLL.LLLLLLLL.LLLLL.LLLLLLLLLL..LLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLLLLLLL..LLLLLLLLL.L.LLLLLLL.LLLLL.LLLL.LLL.LLLLL.LLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLL.L.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLL.LL.LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLL.LLL.LLLLLLLLLLLLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLL.L.LLLLL.LLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLL.LLL.".toCharArray(),
                "L.......LL.....L..L....LLL..L..LL....LL....L.LL.......LLL.....LL.L...LLLLL..L..LL.LL.LL..LL.".toCharArray(),
                "LLL.LLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLL.L.LLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLL".toCharArray(),
                "LLLLLLL.LL.LL.LLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLL...LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLL.L.L.LLLLLLLLLLLLL.LLLLLLLL..LLLLLLL..LL.LLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLL.LLLLLLL".toCharArray(),
                "LLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLL..LLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLL.LL..LL.LLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLL.LLLL.LLLLL".toCharArray(),
                "L.LLLLL.LLLLLLLLLLLLLLL.LLLLLL.L.LLLLLL.LLLLLLLL.LLLLL.LLLLLLLLL.LLL.LLLLLLLLLLLLLLLLLL.L.LL".toCharArray(),
                "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.L.LLLL.LLLLLLLLL.L.L..LLLL.LLLL.LLLLL.LLLLLLLLLL.LL.LLLLLLL".toCharArray(),
                "LLLLLLLLLLLLLLLLLLL.LLL.LLLLLLLL.LLLLL..LLLLLLLL.LLL.L.LLLL.LLL..LLLLLLLLLLLLLLLLLLLL.LLLLLL".toCharArray(),
                ".L..LL...LL...L..LL......L.LLLL.L...L.LL..LLL............LL....LL.LL..L..L...L...L...L...LL.".toCharArray(),
                "LL.LLLL.LLLLLL.LLLLLL.L.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLLLLL.L".toCharArray(),
                "LLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.".toCharArray(),
                "LLLLLLL.LLLLL.LLLLLLLLL.LLLLL.L..LLLLLLL.LLLLLL..LLLLL.LLLLLLLLL.LL.LLLLLL.LL.LL.LLLLLLLLLLL".toCharArray(),
                "LLL.LLL.LLLLL.LLL.LLLLL.LLLLLLL.LLLLLLL.L.LLLLLL.LLLLLLLLLL.LLLL.LLLLLLLLLL.LLLLLLLLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLL..L.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLL.LLLLL..LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLLLLLLLLLLLL..L.LLL.L.LLLLLL.LLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLL".toCharArray(),
                "LLLLLLL.LLLLLLLLLLLLLLLLLL.LLLLL.LLLLLL.LLL.L.LLLLLLLL.LLLLLLL.L..LLLLLLLL.LLLL.L.LLLLLLLLLL".toCharArray(),
                "LLLLLLLLLLLLL.L.LLLLLL.LLLLLLLLL.LLLLLL.LLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLL.LLLL.LLLL.".toCharArray(),
                "LLLLLLLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLL..LLLLLLLLL".toCharArray(),
                "LLLLLLL.LL.LL.LLLLLLLLL.LLLLLLLLLLLLLLL.L.LLLLLL.LLLLL.LLLLLLLLL.LLLLLLL.L.LLLLLLLLLLLLLLLLL".toCharArray()
        )
    }
}