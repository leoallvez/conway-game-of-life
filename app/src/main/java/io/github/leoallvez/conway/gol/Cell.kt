package io.github.leoallvez.conway.gol

class Cell(val col: Int, val row: Int, var isAlive: Boolean) {
    fun invert() {
        isAlive = isAlive.not()
    }
}