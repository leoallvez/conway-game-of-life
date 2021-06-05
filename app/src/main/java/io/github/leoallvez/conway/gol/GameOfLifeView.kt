package io.github.leoallvez.conway.gol

import android.R.attr.columnWidth
import android.R.attr.rowHeight
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import kotlin.random.Random


class GameOfLifeView(context: Context?, attrs: AttributeSet?) :
    SurfaceView(context, attrs),
    Runnable {

    private val thread: Thread by lazy {
        Thread(this@GameOfLifeView)
    }

    private var isRunning = false
    //TODO: find a new name for CellGraphic;
    private lateinit var cellGraphic: CellGraphic
    private var columns = 1
    private var rows = 1

    private lateinit var universe: Universe

    init {
        initWorld()
    }

    override fun run() {
        while (isRunning) {
            try {
                Thread.sleep(300)
            } catch (e: InterruptedException) {
                Log.e("error:", "${e.printStackTrace()}")
            }
            val canvas: Canvas = holder.lockCanvas()
            universe.nextGeneration()
            drawCells(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun start() {
        isRunning = true
        thread.start()
    }

    fun stop() {
        isRunning = false
        while (true) {
            try {
                thread.join()
            } catch (e: InterruptedException) {
                Log.e("error:", "${e.printStackTrace()}")
            }
            break
        }
    }

    private fun initWorld() {
        val metrics = getDisplayMetrics()
        calculateColumnsRowsNumber(metrics)
        val (columnWidth, rowHeight) = calculateColumnWidthRowHeight(metrics)
        cellGraphic = CellGraphic(columnWidth, rowHeight)
        universe = Universe(columns, rows)
    }

    private fun getDisplayMetrics(): DisplayMetrics {
        val metrics = DisplayMetrics()
        context?.display?.apply {
            getRealMetrics(metrics)
        }
        return metrics
    }

    private fun calculateColumnsRowsNumber(metrics: DisplayMetrics) {
        columns = metrics.widthPixels / DEFAULT_SIZE
        rows = metrics.heightPixels/ DEFAULT_SIZE
    }

    private fun calculateColumnWidthRowHeight(metrics: DisplayMetrics): Pair<Int, Int> {
        val columnWidth = metrics.widthPixels / columns
        val rowHeight = metrics.heightPixels/ rows
        return Pair(columnWidth, rowHeight)
    }

    private fun drawCells(canvas: Canvas) {
        for (col in 0 until columns) {
            for (row in 0 until rows) {
                val cellIsAlive = universe.getCell(col, row).isAlive
                //val cellIsAlive = (Random.nextInt(0, 10) % 2 == 0)
                cellGraphic.draw(col, row, cellIsAlive, canvas)
                //canvas.drawRect(cellGraphic.rectangle, cellGraphic.paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d("test", "We is here!")
        if (event.action == MotionEvent.ACTION_DOWN) {
            // we get the coordinates of the touch and we convert it in coordinates for the board
            val i = (event.x / columnWidth).toInt()
            val j = (event.y / rowHeight).toInt()
            // we get the cell associated to these positions
            val cell: Cell = universe.getCell(i, j)
            // we call the invert method of the cell got to change its state
            cell.isAlive = true
            //invalidate()
        }
        return super.onTouchEvent(event)
    }

    companion object {
        const val DEFAULT_SIZE = 10
    }
}
