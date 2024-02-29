package com.example.minesweeper.model

object MineSweeperModel {
    val SAFE: Int = 0
    val BOMB: Int = 1
    var safeRemain: Int = 5 * 5 - 3
    var gameLost: Boolean = false
    data class Field(val type: Int = 0, val minesAround: Int,
                     var isFlagged: Boolean = false, var wasClicked: Boolean = false)

    var fieldMatrix: Array<Array<Field>> = arrayOf(arrayOf(Field(0, 1), Field(0,1), Field(0, 1), Field(0,1), Field(0,0)),
        arrayOf(Field(1, 0), Field(0,2), Field(1,0), Field(0,1), Field(0,0)),
        arrayOf(Field(0, 1), Field(0, 2), Field(0, 1), Field(0,1), Field(0,1), Field(0,0)),
        arrayOf(Field(0,1), Field(0,1), Field(0,0), Field(0,0), Field(0,0)),
        arrayOf(Field(1,0), Field(0,1), Field(0,0), Field(0,0), Field(0,0)))

    fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].wasClicked = false
            }
        }
        safeRemain = 22
        gameLost = false
    }

    fun getFieldContent(i: Int, j: Int) = fieldMatrix[i][j]
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
//    fun getGameLost(): Boolean {
//        return gameLost
//    }
    fun setGameLost() {
        gameLost = true
    }
}