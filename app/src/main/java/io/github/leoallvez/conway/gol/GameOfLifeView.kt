package io.github.leoallvez.conway.gol

import android.R.attr.columnWidth
import android.R.attr.rowHeight
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView

class GameOfLifeView(context: Context?, attrs: AttributeSet?) :
    SurfaceView(context, attrs),
    Runnable {

    private val thread: Thread by lazy {
        Thread(this@GameOfLifeView)
    }

    private var isRunning = false
    //TODO: find a new name for CellGraphic;
    private lateinit var cellGraphic: CellGraphic
    private var screenWidth = 1
    private var screenHeight = 1

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
            val canvas: Canvas? = holder.lockCanvas()
            canvas?.let {
                universe.nextGeneration()
                drawCells(canvas)
                holder.unlockCanvasAndPost(canvas)
            }
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
        val (width, height) = getWidthAndHeight(metrics)
        cellGraphic = CellGraphic(width, height)
        universe = Universe(screenWidth, screenHeight)
    }

    private fun getDisplayMetrics(): DisplayMetrics {
        val metrics = DisplayMetrics()
        context?.display?.apply {
            getRealMetrics(metrics)
        }
        return metrics
    }

    private fun calculateColumnsRowsNumber(metrics: DisplayMetrics) {
        screenWidth = metrics.widthPixels / DEFAULT_SIZE
        screenHeight = metrics.heightPixels/ DEFAULT_SIZE
    }

    private fun getWidthAndHeight(metrics: DisplayMetrics): Pair<Int, Int> {
        val width = metrics.widthPixels / screenWidth
        val height = metrics.heightPixels/ screenHeight
        return Pair(width, height)
    }

    private fun drawCells(canvas: Canvas) {
        for (row in 0 until screenWidth) {
            for (col in 0 until screenHeight) {
                val cell = universe.cells[row][col]
                cellGraphic.draw(cell, canvas)
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
            val cell: Cell = universe.cells[i][j]
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
