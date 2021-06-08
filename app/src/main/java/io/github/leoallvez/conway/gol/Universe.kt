package io.github.leoallvez.conway.gol

import java.util.ArrayList
import kotlin.random.Random

class Universe(private val width: Int, private val height: Int) {

    var currentCells = createCells(true)

    private lateinit var nextCells: Array<Array<Cell>>

    private fun createCells(randomIsLive: Boolean): Array<Array<Cell>> {
        return Array(width) { row ->
            Array(height) { col ->
                val isAlive = if(randomIsLive) Random.Default.nextBoolean() else false
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

        nextCells = currentCells.map { it.clone().clone() }.toTypedArray()

        for (row in 0 until width) {
            for (col in 0 until height) {

                val currCell: Cell = currentCells[row][col]
                val nextCell: Cell = startNextCell(currCell)

                val livingNeighbors = countNeighboursAlive(row, col)

                // rule 1 & rule 2
                if (currCell.isAlive && lessThanTwoOrMoreThanThree(livingNeighbors)) {
                    nextCell.die()
                }

                // rule 2 & rule 4
                if (currCell.isAlive && threeOrTwo(livingNeighbors) || !currCell.isAlive && livingNeighbors == 3) {
                    nextCell.reborn()
                }
            }
        }
        currentCells = nextCells
    }

    private fun startNextCell(currCell: Cell): Cell {
        val nextCell: Cell = currCell.copy()
        nextCells[currCell.row][currCell.col] = nextCell
        return nextCell
    }

    private fun lessThanTwoOrMoreThanThree(livingNeighbors: Int): Boolean {
        return (livingNeighbors < 2 || livingNeighbors > 3)
    }

    private fun threeOrTwo(livingNeighbors: Int): Boolean {
        return (livingNeighbors == 3 || livingNeighbors == 2)
    }
}