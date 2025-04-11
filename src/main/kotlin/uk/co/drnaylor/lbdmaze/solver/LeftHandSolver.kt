package uk.co.drnaylor.lbdmaze.solver

import uk.co.drnaylor.lbdmaze.data.Direction
import uk.co.drnaylor.lbdmaze.data.Direction.Companion.turnLeft

class LeftHandSolver : MazeSolver {
    override fun nextStep(id: String, enteredFrom: Direction, availableDirections: List<Direction>): Direction
        = rotate(enteredFrom, availableDirections)

    private tailrec fun rotate(from: Direction, availableDirections: List<Direction>): Direction {
        val rotated = from.turnLeft()
        return if (availableDirections.contains(rotated)) rotated else rotate(rotated, availableDirections)
    }

}