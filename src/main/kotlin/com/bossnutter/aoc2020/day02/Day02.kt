package com.bossnutter.aoc2020.day02

import java.io.File

val pattern = """(\d+)-(\d+) (\w+): (\w+)""".toRegex()

fun main() {
    val input = File("src/main/resources/day02/input.txt").readLines()
    println("Answer 1: " + answer1(input));
    println("Answer 2: " + answer2(input));
}

fun answer1(passwords: List<String>): Int {
    return passwords
        .stream()
        .map { pattern.find(it)!!.destructured }
        .filter { (min, max, char, password) -> password.count() { char.contains(it) } in min.toInt()..max.toInt() }
        .count().toInt()
}

fun answer2(passwords: List<String>): Int {
    return passwords
        .stream()
        .map { pattern.find(it)!!.destructured }
        .filter { (first, second, char, password) -> (password[first.toInt() - 1].toString() == char).xor(password[second.toInt() - 1].toString() == char) }
        .count().toInt()
}