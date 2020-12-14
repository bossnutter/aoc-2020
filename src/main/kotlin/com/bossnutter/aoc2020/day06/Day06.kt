package com.bossnutter.aoc2020.day06

import java.io.File
import java.util.stream.Collectors

fun main() {
    println("Example 1: ${answer1(getGroups("example.txt"))}")
    println("Answer 1: ${answer1(getGroups("input.txt"))}")
    println("Example 2: ${answer2(getGroups("example.txt"))}")
    println("Answer 2: ${answer2(getGroups("input.txt"))}")
}

fun getGroups(file: String): List<String> {
    return File("src/main/resources/day06/${file}").readText().split("""\n\n""".toRegex())
}

fun answer1(groups: List<String>): Long {
    return groups.stream().map { getQuestionsAnswered(it) }.reduce(0) { a, b -> a + b }

}

fun getQuestionsAnswered(group: String): Long {
    return group.chars().filter { ('a'..'z').contains(it.toChar()) }.distinct().count()
}

fun answer2(groups: List<String>): Long {
    return groups.stream().map { getQuestionsAnsweredByAll(it) }.reduce(0) { a, b -> a + b }
}

fun getQuestionsAnsweredByAll(group: String): Long {
    return group.split("\n").stream()
            .map { it.toSet() }
            .reduce { a, b -> a.intersect(b)}
            .orElseGet { setOf() }.size.toLong()
}