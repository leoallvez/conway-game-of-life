package io.github.leoallvez.conway.gol

data class Cell(val row: Int, val col: Int, var isAlive: Boolean = false) {

    fun die() {
        isAlive = false
    }

    fun reborn() {
        isAlive = true
    }

    fun invert() {
        isAlive = isAlive.not()
    }
}