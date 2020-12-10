package com.bossnutter.aoc2020.day05

import java.io.File
import java.lang.RuntimeException
import java.util.stream.Collectors

fun main() {
    val seats = File("src/main/resources/day05/input.txt").readLines()
            .stream()
            .map { Seat.from(it) }
            .collect(Collectors.toList())
    println("Answer 1: ${answer1(seats)}")
    println("Answer 2: ${answer2(seats)}")
}

fun answer1(seats: List<Seat>): Int {
    return seats.maxByOrNull { it.getId() }!!.getId()
}

fun answer2(seats: List<Seat>): Int {
    val seatMap = seats.map { it.getId() to it }.toMap()
    for (i in 0..answer1(seats)) {
        if (seatMap.get(i) != null && seatMap.get(i+1) == null && seatMap.get(i+2) != null) {
            return i+1
        }
    }
    throw RuntimeException("bad")
}

data class Seat(val row: Int, val column: Int) {
    fun getId(): Int {
        return row * 8 + column
    }

    companion object Factory {
        fun from(seatSpec: String): Seat {
            return Seat(
                    seatSpec.substring(0, 7).replace('F', '0').replace('B', '1').toInt(2),
                    seatSpec.substring(7, 10).replace('L', '0').replace('R', '1').toInt(2)
            )
        }
    }
}
