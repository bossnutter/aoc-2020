package com.bossnutter.aoc2020.day03

import java.io.File

fun main() {
    val input = File("src/main/resources/day03/input.txt").readLines()
    println("Answer 1: ${answer1(input, 3, 1)}")
    println("Answer 2: ${answer2(input)}")
}

fun answer1(input: List<String>, xStep: Int, yStep: Int): Int {
    var x = 0
    var trees = 0

    for (y in yStep until input.size step yStep) {
        x = (x + xStep) % input[0].length
        if (input[y][x] == '#') {
            trees++
        }
    }

    return trees
}

fun answer2(input: List<String>): Int {
    println(answer1(input, 1, 1))
    println(answer1(input, 3, 1))
    println(answer1(input, 5, 1))
    println(answer1(input, 7, 1))
    println(answer1(input, 1, 2))

    return answer1(input, 1, 1) *
            answer1(input, 3, 1) *
            answer1(input, 5, 1) *
            answer1(input, 7, 1) *
            answer1(input, 1, 2)
}

// 1438223688 is too high