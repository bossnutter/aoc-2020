package com.bossnutter.aoc2020.day08

import java.io.File
import java.util.stream.Collectors

fun main() {
    println("Example: ${Computer().executeProgram(getProgram("example.txt"))}")
    println("Answer 1: ${Computer().executeProgram(getProgram("input.txt"))}")
    println("Answer 2: ${answer2()}")

}

fun answer2(): Int {
    val program = getProgram("input.txt")
    var instructionToMutate = findNextInstructionToMutate(program, 0)

//    val instructionsToMutate = program.stream()
//        .filter { it.operation in setOf("nop", "jmp") }
//        .collect(Collectors.toList())
//        .iterator()

    val (accumulator, programExited) = Computer().executeProgram(program)

    var acc = 0

    while(!programExited) {
        println("Mutating instruction ${instructionToMutate}")
        val (accumulator, programExited) = Computer().executeProgram(mutateInstruction(program, instructionToMutate))
        println("${accumulator}, ${programExited}")
        acc = accumulator
        instructionToMutate = findNextInstructionToMutate(program, instructionToMutate + 1)
    }

    return acc
}

fun mutateInstruction(program: List<Instruction>, instructionToMutate: Int): List<Instruction> {
    val newProgram = program.toMutableList()
    val originalInstruction = newProgram[instructionToMutate]
    newProgram.removeAt(instructionToMutate)
    if (originalInstruction.operation == "jmp") {
        newProgram.add(instructionToMutate, Instruction("nop", originalInstruction.argument))
    } else if (originalInstruction.operation == "nop") {
        newProgram.add(instructionToMutate, Instruction("jmp", originalInstruction.argument))
    } else {
        throw RuntimeException("baddd")
    }
    return newProgram
}



fun findNextInstructionToMutate(program: List<Instruction>, startFromIndex: Int): Int {
    for (i in startFromIndex until program.size) {
        if (program[i].operation in setOf("jmp", "nop")) {
            return i
        }
    }
    return -1
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
