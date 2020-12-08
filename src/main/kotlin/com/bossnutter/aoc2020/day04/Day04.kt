package com.bossnutter.aoc2020.day04

import java.io.File

fun main() {
    val input =
    println("Answer 1: ${answer1(getInput("input.txt"))}")
    println("All invalid (should be 0): ${answer2(getInput("all-invalid.txt"))}")
    println("All valid (should be 4): ${answer2(getInput("all-valid.txt"))}")
}

fun getInput(file: String): List<String> {
    return File("src/main/resources/day04/${file}").readText().split("""\n\n""".toRegex())
}

fun answer1(passports: List<String>): Long {
    val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    return passports
        .stream()
        .filter { input -> requiredFields.stream().allMatch { it in input } }
        .count()
}

fun answer2(passports: List<String>): Long {
    val validators = setOf<(String) -> Boolean>(
        { getField(it, "byr").toIntOrNull() in 1920..2002 },
        { getField(it, "iyr").toIntOrNull() in 2010..2020 },
        { getField(it, "eyr").toIntOrNull() in 2020..2030 },
        { it ->
            val field = getField(it, "hgt")
        },


    )

    return 1

}

fun getField(passport: String, field: String): String {
    return """${field}:([^ \n]+)""".toRegex().find(passport)!!.groupValues[1]
}