package io.github.leoallvez.conway.gol

import java.util.ArrayList
import kotlin.random.Random

class Universe(private val width: Int, private val height: Int) {

    private val futureLiveCells: MutableList<Cell> = ArrayList()
    private val futureDeadCells: MutableList<Cell> = ArrayList()

    val cells: Array<Array<Cell>> by lazy {
        Array(width) { row ->
            Array(height) { col ->
                val isAlive = Random.Default.nextBoolean()
                Cell(row, col, isAlive)
            }
        }
    }

    private fun countNeighboursAlive(row: Int, col: Int): Int {
        var count = 0

        for (k in row - 1..row + 1) {
            for (l in col - 1..col + 1) {
                if ((k != row || l != col) && k >= 0 && k < width && l >= 0 && l < height) {
                    val cell: Cell = cells[k][l]
                    if (cell.isAlive) {
                        count++
                    }
                }
            }
        }

        return count
    }

    fun nextGeneration() {
        futureLiveCells.clear()
        futureDeadCells.clear()

        for (row in 0 until width) {
            for (col in 0 until height) {
                val cell = cells[row][col]
                val nbNeighbours = countNeighboursAlive(cell.row, cell.col)

                // rule 1 & rule 3
                if (cell.isAlive && (nbNeighbours < 2 || nbNeighbours > 3)) {
                    futureDeadCells.add(cell)
                }

                // rule 2 & rule 4
                if (cell.isAlive && (nbNeighbours == 3 || nbNeighbours == 2) || !cell.isAlive && nbNeighbours == 3
                ) {
                    futureLiveCells.add(cell)
                }
            }
        }

        // update future live and dead cells
        for (cell in futureLiveCells) {
            cell.isAlive = true
        }

        for (cell in futureDeadCells) {
            cell.isAlive = false
        }
    }
}