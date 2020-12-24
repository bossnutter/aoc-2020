package com.bossnutter.aoc2020.day07

import java.io.File
import java.util.stream.Collectors

fun main() {
    println("Example: ${answer1(getRuleList("example.txt"))}")
    println("Example (rec): ${answer1rec(getRuleList("example.txt"), setOf("shiny gold")).size}")
    println("Answer 1: ${answer1(getRuleList("input.txt"))}")
    println("Answer 1 (rec): ${answer1rec(getRuleList("input.txt"), setOf("shiny gold")).size}")
    println("Example 2: ${answer2(getRuleList("example2.txt").map { it.colour to it }.toMap(), "shiny gold")}")
    println("Answer 2: ${answer2(getRuleList("input.txt").map { it.colour to it }.toMap(), "shiny gold")}")
}

fun answer1(rules: List<Rule>): Int {
    var toCheck = mutableSetOf("shiny gold")
    var toCheckNext = mutableSetOf<String>()
    val matchedRules = mutableSetOf<Rule>()

    while (toCheck.size > 0) {
        for (colour in toCheck) {
            for (rule in rules) {
                if (rule.canContain(colour)) {
                    matchedRules.add(rule)
                    toCheckNext.add(rule.colour)
                }

            }
        }
        toCheck = toCheckNext
        toCheckNext = mutableSetOf()
    }
    return matchedRules.size
}

fun answer1rec(rules: List<Rule>, colours: Set<String>): Set<Rule> {
    val allMatchedRules = colours.stream()
            .map { findRulesThatCanContainThisColour(rules, it) }
            .reduce { a, b -> a union b }
            .get()
    if (allMatchedRules.isNotEmpty()) {
        return allMatchedRules union answer1rec(rules, allMatchedRules.stream()
                .map { it.colour }
                .collect(Collectors.toSet()))
    }
    return allMatchedRules
}

fun findRulesThatCanContainThisColour(rules: List<Rule>, colour: String): Set<Rule> {
    return rules.stream()
            .filter { it.canContain(colour) }
            .collect(Collectors.toSet())
}

fun answer2(rules: Map<String, Rule>, colour: String): Int {
    val rule = rules[colour]!!
    var count = getNumberOfContainedBags(rule)
    for (bagQuantity in rule.bagQuantites) {
        count += bagQuantity.quantity * answer2(rules, bagQuantity.colour)
    }
    return count
}

fun getNumberOfContainedBags(rule: Rule): Int {
    return rule.bagQuantites.stream().map { it.quantity }.reduce(0, Integer::sum)
}

fun getRuleList(file: String): List<Rule> {
    return File("src/main/resources/day07/${file}").readLines()
            .stream()
            .map { Rule.parse(it) }
            .collect(Collectors.toList())
}

data class Rule(val colour: String, val bagQuantites: Set<BagQuantity>) {
    fun canContain(colour: String): Boolean {
        return bagQuantites.stream()
                .anyMatch { it.colour == colour }
    }

    companion object Parser {
        fun parse(rule: String): Rule {
            val colour = """([\w ]+) bags contain""".toRegex().find(rule)!!.groupValues[1]
            val bagQuantitesString = rule.split("contain")[1]
            if (bagQuantitesString.contains("no other bags")) {
                return Rule(colour, emptySet())
            } else {
                val bagQuantites = bagQuantitesString.split(",")
                        .stream()
                        .map { BagQuantity.parse(it) }
                        .collect(Collectors.toSet())
                return Rule(colour, bagQuantites)
            }
        }
    }
}

data class BagQuantity(val colour: String, val quantity: Int) {
    companion object Parser {
        fun parse(bagQuantity: String): BagQuantity {
            val (quantity, colour) = """(\d+) ([\w ]+) bags?""".toRegex().find(bagQuantity)!!.destructured
            return BagQuantity(colour, quantity.toInt())
        }
    }
}