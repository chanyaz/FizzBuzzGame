package com.savekirk.fizzbuzzgame.home

interface  HomeContract {

    interface View {
        fun setPresenter(presenter: Presenter)
        fun showHighScore(score: Int)
        fun startGame()
    }

    interface Presenter {
        fun getHighScore()
    }

}

