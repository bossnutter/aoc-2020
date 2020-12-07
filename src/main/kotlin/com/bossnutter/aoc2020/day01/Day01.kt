package com.bossnutter.aoc2020.day01

import com.bossnutter.aoc2020.common.getNumbersFromFile
import com.bossnutter.aoc2020.common.measureTimeMillisAndReturn

fun main() {
    val expenses = getNumbersFromFile("src/main/resources/day01/input.txt")

    println(measureTimeMillisAndReturn({ time -> println("Answer 1 took: ${time} (ms)")}, { answer1(expenses) }))
    println(measureTimeMillisAndReturn({ time -> println("Answer 2 took: ${time} (ms)")}, { answer2(expenses) }))
}

fun answer1(numbers: List<Int>): Int {
    for (x in numbers) {
        for (y in numbers) {
            if (x + y == 2020) {
                return x * y;
            }
        }
    }
    throw RuntimeException()
}

fun answer2(numbers: List<Int>): Int {
    for (x in numbers) {
        for (y in numbers) {
            for (z in numbers) {
                if (x + y + z == 2020) {
                    return x * y * z;
                }
            }
        }
    }
    throw RuntimeException()
}