package com.bossnutter.aoc2020.day05

import kotlin.test.Test
import kotlin.test.assertEquals

class SeatTest {
    data class TestCase(val seatSpec: String, val expectedRow: Int, val expectedColumn: Int, val expectedId: Int)

    @Test
    fun from() {
        for (testCase in listOf(
                TestCase("BFFFBBFRRR", 70, 7, 567),
                TestCase("FFFBBBFRRR", 14, 7, 119),
                TestCase("BBFFBBFRLL", 102, 4, 820)
        )) {
            val seat = Seat.from(testCase.seatSpec)
            assertEquals(testCase.expectedRow, seat.row)
            assertEquals(testCase.expectedColumn, seat.column)
            assertEquals(testCase.expectedId, seat.getId())
        }
    }
}