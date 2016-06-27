package com.savekirk.fizzbuzzgame.home

import com.savekirk.fizzbuzzgame.Data.GameDataSource

class HomePresenter(data: GameDataSource,view : HomeContract.View) : HomeContract.Presenter {

    private var gameView : HomeContract.View = view
    private var dataSource : GameDataSource = data

    init {
        gameView.setPresenter(this)
    }

    override fun getHighScore() {
        dataSource.getScore(object : GameDataSource.GetScoreCallback{
            override fun onScoreLoaded(score: Int) {
                gameView.showHighScore(score)
            }

            override fun onDataNotAvailable() {
                gameView.showHighScore(0)
            }

        })
    }
}