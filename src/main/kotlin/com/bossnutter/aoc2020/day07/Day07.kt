package com.bossnutter.aoc2020.day07

import java.io.File
import java.util.stream.Collectors

fun main() {
    println("Example: ${answer1()}")
}

fun getRuleList(file: String): List<String> {
    return File("src/main/resources/day06/${file}").readLines()
}

data class Rule(val colour: String, val bagQuantites: Set<BagQuantity>) {
    companion object Parser {
        fun parse(rule: String): Rule {
            val colour = """([\w ]+) bags contain""".toRegex().find(rule)!!.groupValues[1]
            val bagQuantites = rule.split("contain")[1].split(",")
                    .stream()
                    .map { BagQuantity.parse(it) }
                    .collect(Collectors.toSet())
            return Rule(colour, bagQuantites)
        }
    }
}

data class BagQuantity(val colour: String, val quantity: Int) {
    companion object Parser {
        fun parse(bagQuantity: String): BagQuantity {
            (quantity, colour)
        }
    }
}