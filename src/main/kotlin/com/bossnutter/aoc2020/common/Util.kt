package com.bossnutter.aoc2020.common

import java.io.File
import java.util.stream.Collectors

fun getNumbersFromFile(path: String): List<Int> =
    File(path).readLines().stream().map { n -> Integer.valueOf(n) }.collect(Collectors.toList())

fun <T> measureTimeMillisAndReturn(printFunction: (Long) -> Unit, function: () -> T): T {
    val startTime = System.currentTimeMillis()
    val result: T = function.invoke()
    printFunction(System.currentTimeMillis() - startTime)
    return result
}