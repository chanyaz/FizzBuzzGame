package com.savekirk.fizzbuzzgame.Data

import java.util.*

object GameDataRepository : GameDataSource {


    var gameCache : Map<String, Int>? = null

    val SCORE = "score"
    val LIFE = "life"

    override fun getScore(callback: GameDataSource.GetScoreCallback) {
        if (gameCache().containsKey(SCORE)) {
            callback.onScoreLoaded(gameCache().get(SCORE)!!)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun saveScore(score: Int) {
        gameCache().put(SCORE, score)
    }

    override fun getLives(callback: GameDataSource.GetLivesCallback) {
        if (gameCache().containsKey(LIFE)) {
            callback.onLivesLoaded(gameCache().get(LIFE)!!)
        } else {
            callback.onLivesNotAvailable()
        }
    }

    override fun saveLives(lives: Int) {
        gameCache().put(LIFE, lives)
    }

    private fun gameCache() : LinkedHashMap<String, Int> {
        return if (gameCache == null) {
            LinkedHashMap<String, Int>()
        } else {
            gameCache as LinkedHashMap<String, Int>
        }
    }


}