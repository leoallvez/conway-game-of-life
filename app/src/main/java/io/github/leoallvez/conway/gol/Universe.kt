package io.github.leoallvez.conway.gol

import java.util.ArrayList
import kotlin.random.Random

class Universe(private val width: Int, private val height: Int) {

    private val nextLiveCells: MutableList<Cell> = ArrayList()
    private val nextDeadCells: MutableList<Cell> = ArrayList()

    val currentCells: Array<Array<Cell>> by lazy {
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
                    val cell: Cell = currentCells[k][l]
                    if (cell.isAlive) {
                        count++
                    }
                }
            }
        }

        return count
    }

    fun nextGeneration() {
        nextLiveCells.clear()
        nextDeadCells.clear()

        for (row in 0 until width) {
            for (col in 0 until height) {
                val cell: Cell = currentCells[row][col]
                val cellIsAlive = cell.isAlive
                val livingNeighbors = countNeighboursAlive(cell.row, cell.col)

                // rule 1 & rule 2
                if (cellIsAlive && lessThanTwoOrMoreThanThree(livingNeighbors)) {
                    nextDeadCells.add(cell)
                }

                // rule 2 & rule 4
                if (cellIsAlive && threeOrTwo(livingNeighbors) || !cellIsAlive && livingNeighbors == 3) {
                    nextLiveCells.add(cell)
                }
            }
        }

        for (cell in nextLiveCells) {
            cell.isAlive = true
        }

        for (cell in nextDeadCells) {
            cell.isAlive = false
        }
    }

    private fun lessThanTwoOrMoreThanThree(livingNeighbors: Int): Boolean {
        return (livingNeighbors < 2 || livingNeighbors > 3)
    }

    private fun threeOrTwo(livingNeighbors: Int): Boolean {
        return (livingNeighbors == 3 || livingNeighbors == 2)
    }
}