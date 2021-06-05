package io.github.leoallvez.conway.gol

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class CellGraphic(private val columnWidth: Int, private val rowHeight: Int) {

    val rectangle: Rect = Rect()
    val paint: Paint = Paint()

    fun draw(col: Int, row: Int, cellIsAlive: Boolean, canvas: Canvas) {
        rectangle.set(
            getRectangleLeft(col),
            getRectangleTop(row),
            getRectangleRight(col),
            getRectangleBottom(row)
        )
        //paint.style = Paint.Style.STROKE;
        //paint.color = getCellColor(cellIsAlive)

        // fill
        paint.style = Paint.Style.FILL
        //paint.setColor(Color.MAGENTA);
        paint.color = getCellColor(cellIsAlive)
        canvas.drawRect(rectangle, paint);

        // border
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        canvas.drawRect(rectangle, paint)
    }

    private fun getCellColor(cellIsAlive: Boolean): Int = if (cellIsAlive) {
        Color.WHITE
    } else {
        Color.BLACK
    }

    private fun getRectangleLeft(col: Int): Int {
        return col * columnWidth - 1
    }

    private fun getRectangleTop(row: Int): Int {
        return row * rowHeight - 1
    }

    private fun getRectangleRight(col: Int): Int {
        return col * columnWidth + columnWidth - 1
    }

    private fun getRectangleBottom(row: Int): Int {
        return row * rowHeight + rowHeight - 1
    }
}
