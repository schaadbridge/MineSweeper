package com.example.minesweeper.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.minesweeper.MainActivity
import com.example.minesweeper.R
import com.example.minesweeper.model.MineSweeperModel

class MineSweeperView (context: Context?, attrs: AttributeSet?) : View(context, attrs){
    lateinit var paintBackground : Paint
    lateinit var paintLine : Paint
    lateinit var paintText: Paint
    val numColumns : Int = 5
    val numRows : Int = 5

    var bitmapUnclicked = BitmapFactory.decodeResource(resources, R.drawable.blankcell)
    var bitmapFlagged = BitmapFactory.decodeResource(resources, R.drawable.flaggedcell)
    var bitmapClickedBomb = BitmapFactory.decodeResource(resources, R.drawable.bombclicked)
    var bitmapUnclickedBomb = BitmapFactory.decodeResource(resources, R.drawable.bombunclicked)

    init{
        paintBackground = Paint()
        paintBackground.setColor(Color.GRAY)
        paintBackground.style = Paint.Style.FILL

        paintLine = Paint()
        paintLine.setColor(Color.DKGRAY)
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintText = Paint()
        paintText.setColor(Color.GREEN)
        paintText.textSize = 20f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paintText.textSize = (height / numRows).toFloat()

        bitmapUnclicked = Bitmap.createScaledBitmap(
            bitmapUnclicked, width/numRows, height/numColumns, false)
        bitmapFlagged = Bitmap.createScaledBitmap(
            bitmapFlagged, width/numRows, height/numColumns, false)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawGameArea(canvas)

        drawMineField(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && !MineSweeperModel.gameLost) {
            val tX = event.x.toInt() / (width / numColumns)
            val tY = event.y.toInt() / (height / numRows)
            Log.d("Cell_Clicked", "$tX, $tY clicked")

            if (tX < numRows && tY < numColumns) {
                if ((context as MainActivity).isFlagModeOn()) {
                    MineSweeperModel.setFieldFlagged(tX,tY)
                }
                else if  (!MineSweeperModel.getIsFlagged(tX,tY)) {
                    MineSweeperModel.setFieldClicked(tX, tY)
                    if (MineSweeperModel.getIsSafe(tX,tY)) MineSweeperModel.safeRemain--
                }
            }

            invalidate()
        }
        return true
    }
    private fun drawGameArea(canvas: Canvas) {
        // border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // horizontal lines
        for (i in 1..numRows) {
            canvas.drawLine(0f, (i * height / numRows).toFloat(), width.toFloat(), (i * height / numRows).toFloat(),
                paintLine)
        }
        // horizontal lines
        for (i in 1..numColumns) {
            canvas.drawLine((i * width/numColumns).toFloat(), 0f, (i * width/numColumns).toFloat(), height.toFloat(), paintLine)
        }
    }

    fun drawMineField (canvas: Canvas) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (MineSweeperModel.getWasClicked(i, j)) {
                    if (MineSweeperModel.getIsSafe(i,j)) {
                        canvas.drawText(MineSweeperModel.getFieldMinesAround(i,j).toString(), ((i)*width/numRows).toFloat(), ((j+1)*height/numColumns).toFloat(), paintText)
                    }
                    else {
                        // lose game
                        canvas.drawBitmap(bitmapClickedBomb, (i * width / numRows).toFloat(), (j * height / numColumns).toFloat(), null)
                        (context as MainActivity).setWinStatus(1)
                        MineSweeperModel.setGameLost()
                    }
                }
                else {
                    if (MineSweeperModel.getIsFlagged(i,j)) {
                        canvas.drawBitmap(bitmapFlagged, (i * width / numRows).toFloat(), (j * height / numColumns).toFloat(), null)
                    }
                    else {
                        canvas.drawBitmap(bitmapUnclicked, (i * width / numRows).toFloat(), (j * height / numColumns).toFloat(), null)
                    }
                }
            }
        }
        if (MineSweeperModel.safeRemain <=0) {
            (context as MainActivity).setWinStatus(2)
        }
    }

    fun resetGame() {
        MineSweeperModel.resetModel()
        (context as MainActivity).setWinStatus(0)
        invalidate()
    }
}