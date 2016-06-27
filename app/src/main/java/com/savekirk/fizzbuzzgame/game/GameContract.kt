package com.savekirk.fizzbuzzgame.game

import com.savekirk.fizzbuzzgame.GameButton

interface GameContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showScore(score: Int)
        fun showGameOver()
        fun increaseNumber()
        fun removeLife()
        fun addLife()
        fun showLives()
        fun totalLife() : Int
        fun playSound()
    }

    interface Presenter {
        fun getHighScore()
        fun gameOver()
        fun saveScore(score: Int)
        fun increaseScore(score: Int)
        fun getScore() : Int
        fun checkResult(button: GameButton, currentNumber: Int)
        fun saveLife(number: Int)
        fun getLife()
    }
}