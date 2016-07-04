package com.savekirk.fizzbuzzgame.game

import com.savekirk.fizzbuzzgame.Data.GameDataSource
import com.savekirk.fizzbuzzgame.GameButton
import java.util.*

class GamePresenter(data: GameDataSource, view: GameContract.View) : GameContract.Presenter {

    private var gameView : GameContract.View = view
    private var dataSource : GameDataSource = data
    private var tempScore : Int = 0
    private val checkedNumbers : HashSet<Int> = hashSetOf(0)

    init {
        gameView.setPresenter(this)
    }

    override fun saveLife(number: Int) {
        dataSource.saveLives(number)
    }

    override fun getLife() {
        dataSource.getLives(object : GameDataSource.GetLivesCallback {
            override fun onLivesLoaded(lives: Int) {
                for (i in 1..lives) {
                    gameView.addLife()
                }
            }

            override fun onLivesNotAvailable() {
            }

        })
    }

    override fun increaseScore(score: Int) {
        tempScore += score
        gameView.showScore(tempScore)
    }

    override fun getScore() : Int {
        return tempScore
    }

    override fun getHighScore() {
        dataSource.getScore(object : GameDataSource.GetScoreCallback{
            override fun onScoreLoaded(score: Int) {
                gameView.showScore(score)
            }

            override fun onDataNotAvailable() {
                gameView.showScore(0)
            }

        })
    }

    override fun gameOver() {
        dataSource.saveScore(getScore())
        gameView.showGameOver()
        tempScore = 0
    }

    override fun saveScore(score: Int) {
        dataSource.saveScore(score)
    }

    override fun isNumberChecked(number: Int): Boolean {
        return checkedNumbers.contains(number)
    }

    override fun checkResult(button: GameButton, currentNumber: Int) {
        checkedNumbers.add(currentNumber)
        val fb = fizzBuzz(currentNumber)
        if (button.equals(fb)) {
            gameView.playSound()
            this.increaseScore(1)
            //gameView.increaseNumber()
        } else {
            gameView.removeLife()
            if (gameView.totalLife() == 0) {
                this.gameOver()
            } else {
                //gameView.increaseNumber()
            }
        }
    }

    fun fizzBuzz(value : Int) : GameButton {
        val by3 = value % 3 == 0
        val by5 = value % 5 == 0

        return if (by3 && by5) {
            GameButton.FIZZBUZZ
        } else if (by3) {
            GameButton.FIZZ
        } else if (by5) {
            GameButton.BUZZ
        } else {
            GameButton.NONE
        }
    }



}

