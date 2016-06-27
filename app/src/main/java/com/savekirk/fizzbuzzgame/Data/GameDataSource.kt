package com.savekirk.fizzbuzzgame.Data

interface  GameDataSource {
    interface GetScoreCallback {
        fun onScoreLoaded(score: Int)
        fun onDataNotAvailable()
    }

    interface GetLivesCallback {
        fun onLivesLoaded(lives: Int)
        fun onLivesNotAvailable()
    }

    fun getScore(callback: GetScoreCallback)

    fun saveScore(score: Int)

    fun getLives(callback: GetLivesCallback)

    fun saveLives(lives: Int)
}