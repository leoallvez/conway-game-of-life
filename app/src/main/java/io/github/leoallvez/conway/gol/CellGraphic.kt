package io.github.leoallvez.conway.gol

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class CellGraphic(private val width: Int, private val height: Int) {

    private val rectangle: Rect = Rect()
    private val paint: Paint = Paint()

    fun draw(cell: Cell, canvas: Canvas) {
        rectangle.set(
            getRectangleLeft(cell.row),
            getRectangleTop(cell.col),
            getRectangleRight(cell.row),
            getRectangleBottom(cell.col)
        )

        // fill
        paint.style = Paint.Style.FILL
        paint.color = getCellColor(cell.isAlive)
        canvas.drawRect(rectangle, paint)

        // border
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        canvas.drawRect(rectangle, paint)
    }

    private fun getCellColor(cellIsAlive: Boolean): Int = if (cellIsAlive) {
        Color.GRAY
    } else {
        Color.BLACK
    }

    private fun getRectangleLeft(row: Int): Int {
        return row * width - 1
    }

    private fun getRectangleTop(col: Int): Int {
        return col * height - 1
    }

    private fun getRectangleRight(row: Int): Int {
        return row * width + width - 1
    }

    private fun getRectangleBottom(col: Int): Int {
        return col * height + height - 1
    }
}
