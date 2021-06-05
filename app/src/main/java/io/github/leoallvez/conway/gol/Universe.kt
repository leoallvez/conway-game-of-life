package io.github.leoallvez.conway.gol

import java.util.ArrayList
import kotlin.random.Random

class Universe(private val width: Int, private val height: Int) {

    private val cells: Array<Array<Cell>> by lazy {
        Array(width) { row ->
            Array(height) { col ->
                val isAlive = Random.Default.nextBoolean()
                Cell(col, row, isAlive)
            }
        }
    }

    fun getCell(col: Int, row: Int): Cell {
        return cells[col][row]
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
        val liveCells: MutableList<Cell> = ArrayList()
        val deadCells: MutableList<Cell> = ArrayList()
        for (row in 0 until width) {
            for (col in 0 until height) {
                val cell = cells[row][col]
                val nbNeighbours = countNeighboursAlive(cell.row, cell.col)

                // rule 1 & rule 3
                if (cell.isAlive && (nbNeighbours < 2 || nbNeighbours > 3)) {
                    deadCells.add(cell)
                }

                // rule 2 & rule 4
                if (cell.isAlive && (nbNeighbours == 3 || nbNeighbours == 2) || !cell.isAlive && nbNeighbours == 3
                ) {
                    liveCells.add(cell)
                }
            }
        }

        // update future live and dead cells
        for (cell in liveCells) {
            cell.isAlive = true
        }

        for (cell in deadCells) {
            cell.isAlive = false
        }
    }
}