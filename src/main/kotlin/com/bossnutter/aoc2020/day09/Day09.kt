package com.bossnutter.aoc2020.day09

import com.bossnutter.aoc2020.common.getNumbersFromFile

fun main() {
    println("Example: ${answer1(getInput("example.txt"), 5)}")
    println("Answer 1: ${answer1(getInput("input.txt"), 25)}")
    println("Example 2: ${answer2(getInput("example.txt"), 127L)}")
    println("Answer 2: ${answer2(getInput("input.txt"), 15353384L)}")

}

fun getInput(file: String): List<Long> {
    return getNumbersFromFile("src/main/resources/day09/${file}")
}

fun answer1(numbers: List<Long>, preambleLength: Int): Long {
    for (i in preambleLength until numbers.size) {
        if (!numberIsValid(numbers[i], numbers.subList(i - preambleLength, i))) {
            return numbers[i]
        }
    }
    throw RuntimeException("bad")
}

fun numberIsValid(nextNumber: Long, previousNumbers: List<Long>): Boolean {
    return previousNumbers
        .flatMap { l -> previousNumbers.map { r -> l to r }.filter { it.first != it.second } }
        .map { it.first + it.second }
        .contains(nextNumber)
}

fun answer2(numbers: List<Long>, sum: Long): Long {
    val subList = findSublist(numbers, sum)
    return subList.minByOrNull { it }!! + subList.maxByOrNull { it }!!
}

fun findSublist(numbers: List<Long>, sum: Long): List<Long> {
    for (i in numbers.indices) {
        for (n in i + 1 until numbers.size) {
            val subList = numbers.subList(i, n + 1)
            if (subList.stream().reduce(0) { a, b -> a + b } == sum ) {
                return subList
            }
        }
    }
    throw RuntimeException("bad")
}