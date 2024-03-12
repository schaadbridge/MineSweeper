package com.example.minesweeper.model

import kotlin.random.Random

object MineSweeperModel {
    val SAFE: Int = 0
    val BOMB: Int = 1
    var initialBombs = 3
    var gameLost: Boolean = false
    var rows: Int = 5
    var cols: Int = 5
    var safeRemain: Int = rows * cols - initialBombs
    data class Field(var type: Int = 0, var minesAround: Int = 0,
                     var isFlagged: Boolean = false, var wasClicked: Boolean = false)

    var fieldMatrix: Array<Array<Field>> = arrayOf(arrayOf(Field(1, 0), Field(0,1), Field(0, 0), Field(0,1), Field(1,0)),
        arrayOf(Field(0, 1), Field(0,1), Field(0,0), Field(0,1), Field(0,1)),
        arrayOf(Field(0, 0), Field(0, 0), Field(0, 0), Field(0,0), Field(0,0), Field(0,0)),
        arrayOf(Field(0,0), Field(0,0), Field(0,0), Field(0,1), Field(0,1)),
        arrayOf(Field(0,0), Field(0,0), Field(0,0), Field(0,1), Field(1,0)))

    fun placeBombs(initialBombCount: Int = 3) {
        initialBombs = initialBombCount
        val randomInts = generateSequence { Random.nextInt(1, rows*cols) }.distinct().take(MineSweeperModel.initialBombs).toSet()
        for (i in randomInts) {
            fieldMatrix[i/rows][i%cols].type = BOMB

            // Add to count of all around
            for (j in -1..1) {
                for (k in -1..1) {
                    if (i/rows + j in 0..<rows && i%rows + k in 0..<cols) {
                        fieldMatrix[i/rows + j][i%rows + k].minesAround ++
                    }
                }
            }
        }
    }

    fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].wasClicked = false
                fieldMatrix[i][j].minesAround = 0
                fieldMatrix[i][j].type = SAFE
            }
        }
        placeBombs()
        safeRemain = rows*cols - initialBombs
        gameLost = false
    }

    fun getIsSafe(i: Int, j: Int) = fieldMatrix[i][j].type == SAFE
    fun getFieldMinesAround(i: Int, j: Int) = fieldMatrix[i][j].minesAround
    fun getIsFlagged(i: Int, j: Int) = fieldMatrix[i][j].isFlagged
    fun getWasClicked(i: Int, j: Int) = fieldMatrix[i][j].wasClicked
    fun setFieldFlagged(i: Int, j: Int) {
        fieldMatrix[i][j].isFlagged =  !fieldMatrix[i][j].isFlagged
    }
    fun setFieldClicked(i: Int, j: Int) {
        fieldMatrix[i][j].wasClicked =  true
    }

    fun setGameLost() {
        gameLost = true
    }
}