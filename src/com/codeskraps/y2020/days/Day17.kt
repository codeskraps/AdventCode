package com.codeskraps.y2020.days

typealias Coordinate = List<Int>
typealias Grid = HashSet<Coordinate>

class Day17 : Day() {
    override val day: String
        get() = "Seventeen"

    private fun convert(dimensions: Int): Grid =
            INPUT.mapIndexed { y, line ->
                line.mapIndexedNotNull { x, char ->
                    if (char == '#') {
                        List(dimensions) { i ->
                            when (i) {
                                0 -> x
                                1 -> y
                                else -> 0
                            }
                        }
                    } else {
                        null
                    }
                }
            }.flatten().toHashSet()

    override fun partOne(): String {
        var grid = convert(3)

        repeat(6) {
            grid = tick(grid)
        }

        return grid.size.toString()
    }

    override fun partTwo(): String {
        var grid = convert(4)

        repeat(6) {
            grid = tick(grid)
        }

        return grid.size.toString()
    }

    private fun tick(grid: Grid): Grid {
        val neighbourInfo = HashMap<Coordinate, Pair<Boolean, Int>>()

        for (cell in grid) {
            for (neighbour in getAllAdjacent(cell)) {
                neighbourInfo[neighbour] = if (neighbourInfo.containsKey(neighbour)) {
                    val old = neighbourInfo[neighbour]!!

                    if (cell == neighbour) {
                        Pair(true, old.second)
                    } else {
                        Pair(old.first, old.second + 1)
                    }
                } else {
                    if (cell == neighbour) {
                        Pair(true, 0)
                    } else {
                        Pair(false, 1)
                    }
                }
            }
        }

        return neighbourInfo.filterValues { (active, neighbourCount) ->
            if (active) {
                neighbourCount in 2..3
            } else {
                neighbourCount == 3
            }
        }.keys.toHashSet()
    }

    private fun getAllAdjacent(cell: Coordinate): Sequence<Coordinate> = sequence {
        fun getAllAdjacentRecur(cell: Coordinate, depth: Int): Sequence<Coordinate> = sequence {
            if (depth < 0) {
                yield(cell)
            } else {
                val minus = cell.toMutableList()
                minus[depth] = minus[depth] - 1
                val plus = cell.toMutableList()
                plus[depth] = plus[depth] + 1

                yieldAll(getAllAdjacentRecur(minus, depth - 1))
                yieldAll(getAllAdjacentRecur(cell, depth - 1))
                yieldAll(getAllAdjacentRecur(plus, depth - 1))
            }
        }

        yieldAll(getAllAdjacentRecur(cell, cell.size - 1))
    }

    companion object {
        private val INPUT = arrayOf(
                "#....#.#",
                "..##.##.",
                "#..#..#.",
                ".#..#..#",
                ".#..#...",
                "##.#####",
                "#..#..#.",
                "##.##..#"
        )
    }
}

