package com.bossnutter.aoc2020.day24

import java.io.File

fun main() {
    println(getVectors(listOf("esenee"))[0]) // 3, 0
    println(getVectors(listOf("esew"))[0]) // 0.5, -0.75
    println(getVectors(listOf("nwwswee"))[0]) // 0, 0

    println("Example: ${answer1(getVectors(getInput("example.txt")))}")
    println("Answer 1: ${answer1(getVectors(getInput("input.txt")))}")
    println("Example 2: ${answer2(getVectors(getInput("example.txt")))}")
    println("Answer 2: ${answer2(getVectors(getInput("input.txt")))}")
}

fun getInput(file: String): List<String> {
    return File("src/main/resources/day24/${file}").readLines()
}

fun answer1(vectors: List<Vector>): Long {
    return getTileMap(vectors).map { it.value }.count { it }.toLong()
}

fun answer2(vectors: List<Vector>): Long {
    val tileMap = getTileMap(vectors).toMutableMap()

    for (day in 0 until 100) {
        val toFlip = mutableListOf<Vector>()
        for (tile in getAllTiles(tileMap)) {
            val blackNeighbours = getBlackNeighbours(tile, tileMap)
            if (tileMap.getOrDefault(tile, false)) {
                if (blackNeighbours == 0 || blackNeighbours > 2) {
                    toFlip.add(tile)
                }
            } else {
                if (blackNeighbours == 2) {
                    toFlip.add(tile)
                }
            }
        }

        for (tile in toFlip) {
            if (tileMap.containsKey(tile)) {
                tileMap[tile] = !tileMap[tile]!!
            } else {
                tileMap[tile] = true
            }
        }
        println("Day ${day + 1}: ${tileMap.map { it.value }.count { it }}")
    }
    return tileMap.map { it.value }.count { it }.toLong()
}

fun getAllTiles(tileMap: Map<Vector, Boolean>): List<Vector> {
    val minX = tileMap.keys.minByOrNull { it.x }!!.x - 1
    val minY = tileMap.keys.minByOrNull { it.y }!!.y - 1.5
    val maxX = tileMap.keys.maxByOrNull { it.x }!!.x + 1
    val maxY = tileMap.keys.maxByOrNull { it.y }!!.y + 1.5

    val tiles = mutableListOf<Vector>()

    var y = minY
    do {
        var x = minX
        do {
            tiles.add(Vector(x, y))
            x += 0.5
        } while (x <= maxX)
        y += 0.75
    } while (y <= maxY)
    return tiles
}

fun getBlackNeighbours(tile: Vector, tileMap: Map<Vector, Boolean>): Int {
    var count = 0
    for (direction in setOf("ne", "e", "se", "sw", "w", "nw")) {
        val neighbour = add(tile, getVector(direction))
        if (tileMap.getOrDefault(neighbour, false)) {
            count++
        }
    }
    return count
}

fun add(a: Vector, b: Vector): Vector {
    return Vector(a.x + b.x, a.y + b.y)
}

fun getTileMap(vectors: List<Vector>): Map<Vector, Boolean> {
    val tileMap = mutableMapOf<Vector, Boolean>()

    for (tile in vectors) {
        if (tileMap.containsKey(tile)) {
            tileMap[tile] = !tileMap[tile]!!
        } else {
            tileMap[tile] = true
        }
    }
    return tileMap
}

fun getVectors(lines: List<String>): List<Vector> {
    val vectors = mutableListOf<Vector>()
    for (line in lines) {
        val tempVectors = mutableListOf<Vector>()
        var i = 0
        do {
            if (i == line.length - 1) {
                tempVectors.add(getVector(line[i].toString()))
                break
            }

            try {
                tempVectors.add(getVector(line.substring(i, i + 2)))
                i += 2
            } catch (e: VectorNotFoundException) {
                tempVectors.add(getVector(line[i].toString()))
                i++
            }
        } while (i < line.length)
        vectors.add(tempVectors.stream().reduce(Vector(0.0, 0.0)) { a, b -> Vector(a.x + b.x, a.y + b.y) } )
    }

    return vectors
}

fun getVector(text: String): Vector {
    return when (text) {
        "ne" -> Vector(0.5, 0.75)
        "e" -> Vector(1.0, 0.0)
        "se" -> Vector(0.5, -0.75)
        "sw" -> Vector(-0.5, -0.75)
        "w" -> Vector(-1.0, 0.0)
        "nw" -> Vector(-0.5, 0.75)
        else -> { throw VectorNotFoundException(text) }
    }
}

class VectorNotFoundException(text: String): Exception(text)

data class Vector(val x: Double, val y: Double)

