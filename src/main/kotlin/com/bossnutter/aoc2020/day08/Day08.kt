package com.bossnutter.aoc2020.day08

import java.io.File
import java.util.stream.Collectors
import java.util.stream.IntStream

fun main() {
    println("Example: ${Computer().executeProgram(getProgram("example.txt"))}")
    println("Answer 1: ${Computer().executeProgram(getProgram("input.txt"))}")
    println("Answer 2: ${answer2(getProgram("input.txt"))}")
}

fun answer2(program: List<Instruction>): Int {
    val instructionIndexesToMutate = IntStream.range(0, program.size)
        .filter { program[it].operation in setOf("nop", "jmp") }
        .boxed()
        .collect(Collectors.toList())

    for (i in instructionIndexesToMutate) {
        println("Mutating instruction ${i}")
        val (accumulator, programExited) = Computer().executeProgram(mutateInstructionInProgram(program, i))
        println("${accumulator}, ${programExited}")
        if (programExited) {
            return accumulator
        }
    }
    throw RuntimeException("bad")
}

fun mutateInstructionInProgram(program: List<Instruction>, instructionToMutate: Int): List<Instruction> {
    val newProgram = program.toMutableList()
    val originalInstruction = newProgram[instructionToMutate]
    newProgram.removeAt(instructionToMutate)
    when (originalInstruction.operation) {
        "jmp" -> {
            newProgram.add(instructionToMutate, Instruction("nop", originalInstruction.argument))
        }
        "nop" -> {
            newProgram.add(instructionToMutate, Instruction("jmp", originalInstruction.argument))
        }
        else -> {
            throw RuntimeException("baddd")
        }
    }
    return newProgram
}

fun getProgram(file: String): List<Instruction> {
    return File("src/main/resources/day08/${file}").readLines().stream()
        .map { Instruction.parse(it) }
        .collect(Collectors.toList())
}

class Computer {
    val operations = mapOf<String, (ip: Int, accumulator: Int, argument: Int) -> Pair<Int, Int>>(
        Pair("nop", { ip, accumulator, argument -> Pair(ip + 1, accumulator) }),
        Pair("acc", { ip, accumulator, argument -> Pair(ip + 1, accumulator + argument) }),
        Pair("jmp", { ip, accumulator, argument -> Pair(ip + argument, accumulator) })
    )

    fun executeProgram(program: List<Instruction>): Pair<Int, Boolean> {
        var ip = 0
        var accumulator = 0
        val instructionsExecuted = mutableListOf<Int>()

        while(!instructionsExecuted.contains(ip) && ip < program.size) {
            instructionsExecuted.add(ip)
            val instruction = program[ip]
            val (newIp, newAccumulator) = operations[instruction.operation]!!(ip, accumulator, instruction.argument)
            ip = newIp
            accumulator = newAccumulator
        }

        return Pair(accumulator, ip >= program.size)
    }

}

class Instruction(val operation: String, val argument: Int) {
    companion object Parser {
        fun parse(instruction: String): Instruction {
            val (operation, argument) = """^(\w+) ([+-]\d+)${'$'}""".toRegex().find(instruction)!!.destructured
            return Instruction(operation, argument.toInt())
        }
    }
}
