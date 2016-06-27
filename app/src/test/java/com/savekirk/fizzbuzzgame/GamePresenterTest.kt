package com.savekirk.fizzbuzzgame

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.capture
import com.savekirk.fizzbuzzgame.Data.GameDataSource
import com.savekirk.fizzbuzzgame.game.GameContract
import com.savekirk.fizzbuzzgame.game.GamePresenter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import com.nhaarman.mockito_kotlin.verify as kverify


class GamePresenterTest {

    private var gameView = Mockito.mock(GameContract.View::class.java)

    private var dataRepository  = Mockito.mock(GameDataSource::class.java)

    lateinit private var presenter : GamePresenter

     val getScoreCallbackCaptor  = argumentCaptor<GameDataSource.GetScoreCallback>()



    @Before
    fun setupMocksAndView() {
        MockitoAnnotations.initMocks(this)
        presenter = GamePresenter(dataRepository, gameView)
    }

    @Test
    fun increaseGameScore_showGameScore() {
        presenter.increaseScore(1)
        verify(gameView).showScore(1)
    }

    @Test
    fun increaseScore_shouldIncreaseCurrentScore() {
        presenter.increaseScore(4)
        presenter.increaseScore(5)
        Assert.assertEquals(9, presenter.getScore())
    }

    @Test
    fun gameOver_shouldSaveScoreAndShowGameOver() {
        presenter.increaseScore(5)
        presenter.gameOver()
        verify(dataRepository).saveScore(5)
        verify(gameView).showGameOver()
    }

    @Test
    fun gameOver_resetCurrentScore() {
        presenter.increaseScore(4)
        presenter.gameOver()
        Assert.assertEquals(0, presenter.getScore())
    }

    @Test
    fun saveScore_shouldSaveScoreToDataRepository() {
        presenter.saveScore(6)
        verify(dataRepository).saveScore(6)
    }

    @Test
    fun clickingNumberView_shouldIncreaseNumber() {
        presenter.checkResult(GameButton.NONE,0)
        verify(gameView).increaseNumber()
    }

    @Test
    fun correctButton_shouldIncreaseNumber() {
        presenter.checkResult(GameButton.FIZZ, 2)
        verify(gameView).increaseNumber()
    }

    @Test
    fun wrongAnswerWith1life_shouldCallGameOver() {
        `when`(gameView.totalLife()).thenReturn(0)
        presenter.checkResult(GameButton.FIZZ, 4)
        verify(gameView).removeLife()
        verify(gameView).showGameOver()
    }

    @Test
    fun wrongAnswer_shouldRemoveLifeAndIncreaseNumber() {
        `when`(gameView.totalLife()).thenReturn(3)
        presenter.checkResult(GameButton.NONE, 2)
        verify(gameView).removeLife()
        verify(gameView).increaseNumber()
    }



    @Test
    fun savedScore_isTheCurrentHighScore() {
        presenter.saveScore(5)
        //kverify(dataRepository).getScore(capture(getScoreCallbackCaptor))
        //getScoreCallbackCaptor.value.onScoreLoaded(5)
    }




}