package uk.co.drnaylor.lbdmaze.solver

import uk.co.drnaylor.lbdmaze.data.Command
import uk.co.drnaylor.lbdmaze.data.Direction

interface MazeSolver {

    fun nextStep(id: String, enteredFrom: Direction, availableDirections: List<Direction>): Direction
}