package com.bossnutter.aoc2020.day04

import java.io.File

fun main() {
    println("Answer 1: ${answer1(getInput("input.txt"))}")
    println("All invalid (should be 0): ${answer2(getInput("all-invalid.txt"))}")
    println("All valid (should be 4): ${answer2(getInput("all-valid.txt"))}")
    println("Answer 2: ${answer2(getInput("input.txt"))}")
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
        {
            var result = false
            val match = """^(\d+)(cm|in)$""".toRegex().find(getField(it, "hgt"))
            if (match != null) {
                val (height, units) = match.destructured
                when (units) {
                    "cm" -> result = height.toIntOrNull() in 150..193
                    "in" -> result = height.toIntOrNull() in 59..76
                }
            }
            result
        },
        { """^#[0-9a-f]{6}$""".toRegex().find(getField(it, "hcl")) != null },
        { """^(amb|blu|brn|gry|grn|hzl|oth)$""".toRegex().find(getField(it, "ecl")) != null },
        { """^\d{9}$""".toRegex().find(getField(it, "pid")) != null }
    )

    return passports
        .stream()
        .filter { passport -> validators.stream().allMatch { it(passport) } }
        .count()
}

fun getField(passport: String, field: String): String {
    val match = """${field}:([^ \n]+)""".toRegex().find(passport)
    return if (match != null) match.groupValues[1] else ""
}