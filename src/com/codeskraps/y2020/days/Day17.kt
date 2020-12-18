package com.codeskraps.y2020.days

class Day17 : Day() {
    override val day: String
        get() = "Seventeen"

    var input = INPUT1
    var cube = arrayOf<Array<Array<Char>>>()
    var hcube = arrayOf<Array<Array<Array<Char>>>>()
    private val width = input.size + (CYCLES * 2)
    private val middle = (width / 2) - (input.size / 2)

    init {
        // Initialize cube
        for (i in 0 until width) {
            var floor = arrayOf<Array<Char>>()
            for (j in 0 until width) {
                var row = arrayOf<Char>()
                for (k in 0 until width) {
                    row += INACTIVE
                }
                floor += row
            }
            cube += floor
        }
        for (h in 0 until width) {
            hcube += cube
        }


        // fill cube
        for (x in input.indices) {
            for (y in input.indices) {
                cube[middle][middle + y][middle + x] = input[y][x]
                hcube[middle][middle][middle + y][middle + x] = input[y][x]
            }
        }
    }

    override fun partOne(): String {
        var active = 0

        for (i in 0 until CYCLES) {
            active = cycle(cube)
        }

        return active.toString()
    }

    private fun neighbours(cube: Array<Array<Array<Char>>>, z: Int, y: Int, x: Int): Int {
        var active = 0
        val dx = arrayOf(-1, 0, 1, -1, 0, 1, -1, 0, 1)
        val dy = arrayOf(-1, -1, -1, 0, 0, 0, 1, 1, 1)

        for (dz in -1..2) {
            for (i in 0..8) {
                val yy = y + dy[i]
                val xx = x + dx[i]
                val zz = z + dz

                try {
                    if (cube[zz][yy][xx] == ACTIVE) {
                        active += 1
                    }
                } catch (e: Exception) {
                    // ignore
                }
            }
        }

        return active
    }

    private fun hneighbours(hcube: Array<Array<Array<Array<Char>>>>, w: Int, z: Int, y: Int, x: Int): Int {
        var active = 0
        val dx = arrayOf(-1, 0, 1, -1, 0, 1, -1, 0, 1)
        val dy = arrayOf(-1, -1, -1, 0, 0, 0, 1, 1, 1)

        for (dw in -1..2) {
            for (dz in -1..2) {
                for (i in 0..8) {
                    val yy = y + dy[i]
                    val xx = x + dx[i]
                    val zz = z + dz
                    val ww = w + dw

                    try {
                        if (hcube[ww][zz][yy][xx] == ACTIVE) {
                            active += 1
                        }
                    } catch (e: Exception) {
                        // Ignore
                    }
                }
            }
        }

        return active
    }

    private fun cycle(cube: Array<Array<Array<Char>>>): Int {
        var change = arrayOf<Array<Array<Int>>>()
        // Initialize change
        for (i in 0 until width) {
            var floor = arrayOf<Array<Int>>()
            for (j in 0 until width) {
                var row = arrayOf<Int>()
                for (k in 0 until width) {
                    row += 0
                }
                floor += row
            }
            change += floor
        }

        for (z in 0 until width) {
            for (y in 0 until width) {
                for (x in 0 until width) {
                    if (cube[z][y][x] == INACTIVE && neighbours(cube, z, y, x) == 3)
                        change[z][y][x] = 1
                    else if (cube[z][y][x] == ACTIVE && neighbours(cube, z, y, x) !in 2..3)
                        change[z][y][x] = -1
                }
            }
        }

        var active = 0

        for (z in 0 until width) {
            for (y in 0 until width) {
                for (x in 0 until width) {
                    if (change[z][y][x] == 1) cube[z][y][x] = ACTIVE
                    else if (change[z][y][x] == -1) cube[z][y][x] = INACTIVE

                    if (cube[z][y][x] == ACTIVE) active += 1
                }
            }
        }

        //printCube(cube)

        return active
    }

    private fun hcycle(hcube: Array<Array<Array<Array<Char>>>>): Int {
        var hchange = arrayOf<Array<Array<Array<Int>>>>()
        // Initialize change
        for (z in 0 until width) {
            var hyper = arrayOf<Array<Array<Int>>>()
            for (i in 0 until width) {
                var floor = arrayOf<Array<Int>>()
                for (j in 0 until width) {
                    var row = arrayOf<Int>()
                    for (k in 0 until width) {
                        row += 0
                    }
                    floor += row
                }
                hyper += floor
            }
            hchange += hyper
        }

        for (w in 0 until width) {
            for (z in 0 until width) {
                for (y in 0 until width) {
                    for (x in 0 until width) {
                        if (hcube[w][z][y][x] == INACTIVE && hneighbours(hcube, w, z, y, x) == 3) {
                            hchange[w][z][y][x] = 1
                        } else if (hcube[w][z][y][x] == ACTIVE && hneighbours(hcube, w, z, y, x) !in 2..3) {
                            hchange[w][z][y][x] = -1
                        }
                    }
                }
            }
        }

        var active = 0

        for (w in 0 until width) {
            for (z in 0 until width) {
                for (y in 0 until width) {
                    for (x in 0 until width) {
                        if (hchange[w][z][y][x] == 1) hcube[w][z][y][x] = ACTIVE
                        else if (hchange[w][z][y][x] == -1) hcube[w][z][y][x] = INACTIVE

                        if (hcube[w][z][y][x] == ACTIVE) active += 1
                    }
                }
            }
        }

        return active
    }

    private fun printCube(cube: Array<Array<Array<Char>>>) {
        var f = 1

        for (floor in cube) {
            print("Floor: $f")
            println()
            f += 1
            for (row in floor) {
                for (column in row) {
                    print("$column ")
                }
                println()
            }
            println("-----------------")
        }
    }

    override fun partTwo(): String {
        var active = 0

        for (i in 0 until CYCLES) {
            active = hcycle(hcube)
        }

        return active.toString()
    }

    companion object {
        private const val ACTIVE = '#'
        private const val INACTIVE = '.'
        private const val CYCLES = 6
        private val INPUT1 = arrayOf(
                ".#.",
                "..#",
                "###"
        )
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

