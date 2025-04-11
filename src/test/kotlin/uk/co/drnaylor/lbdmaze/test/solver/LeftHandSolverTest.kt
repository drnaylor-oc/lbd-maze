package uk.co.drnaylor.lbdmaze.test.solver

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import uk.co.drnaylor.lbdmaze.data.Direction
import uk.co.drnaylor.lbdmaze.solver.LeftHandSolver
import kotlin.test.Test
import kotlin.test.assertEquals

class LeftHandSolverTest {

    @Test
    fun testLeftHandSolverOneRotation() {
        val allDirections = Direction.entries.toList()
        val sut = LeftHandSolver()

        assertEquals(Direction.RIGHT, sut.nextStep("", Direction.UP, allDirections))
        assertEquals(Direction.DOWN, sut.nextStep("", Direction.RIGHT, allDirections))
        assertEquals(Direction.LEFT, sut.nextStep("", Direction.DOWN, allDirections))
        assertEquals(Direction.UP, sut.nextStep("", Direction.LEFT, allDirections))
    }

    @Test
    fun testLeftHandSolverUpDown() {
        val allDirections = listOf(Direction.UP, Direction.DOWN)
        val sut = LeftHandSolver()

        assertEquals(Direction.DOWN, sut.nextStep("", Direction.UP, allDirections))
        assertEquals(Direction.DOWN, sut.nextStep("", Direction.RIGHT, allDirections))
        assertEquals(Direction.UP, sut.nextStep("", Direction.DOWN, allDirections))
        assertEquals(Direction.UP, sut.nextStep("", Direction.LEFT, allDirections))
    }

    @Test
    fun testLeftHandSolverLeftRight() {
        val allDirections = listOf(Direction.LEFT, Direction.RIGHT)
        val sut = LeftHandSolver()

        assertEquals(Direction.RIGHT, sut.nextStep("", Direction.UP, allDirections))
        assertEquals(Direction.LEFT, sut.nextStep("", Direction.RIGHT, allDirections))
        assertEquals(Direction.LEFT, sut.nextStep("", Direction.DOWN, allDirections))
        assertEquals(Direction.RIGHT, sut.nextStep("", Direction.LEFT, allDirections))
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("testLeftHandSolverDirections")
    fun testLeftHandSolverUpOnly(expectedDirection: Direction) {
        val allDirections = listOf(expectedDirection)
        val sut = LeftHandSolver()

        assertEquals(expectedDirection, sut.nextStep("", Direction.UP, allDirections))
        assertEquals(expectedDirection, sut.nextStep("", Direction.RIGHT, allDirections))
        assertEquals(expectedDirection, sut.nextStep("", Direction.DOWN, allDirections))
        assertEquals(expectedDirection, sut.nextStep("", Direction.LEFT, allDirections))
    }

    companion object {
        @JvmStatic
        fun testLeftHandSolverDirections() = Direction.entries.toList().stream()
    }
}