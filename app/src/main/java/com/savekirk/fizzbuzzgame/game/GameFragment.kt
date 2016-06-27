package com.savekirk.fizzbuzzgame.game

import android.annotation.TargetApi
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.support.v4.app.Fragment
import android.transition.ChangeBounds
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import com.savekirk.fizzbuzzgame.GameButton

import com.savekirk.fizzbuzzgame.R
import com.savekirk.fizzbuzzgame.ViewHolderListener
import com.savekirk.fizzbuzzgame.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_game.*
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment(), GameContract.View {

    lateinit private var gamePresenter : GameContract.Presenter
    private var parentActivity: ViewHolderListener? = null
    lateinit private var lifeView : ImageView
    lateinit private var subscriptions: CompositeSubscription
    private var currentNumber = 0
    lateinit private var numberSubscription : Subscription

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment GameFragment.
         */
        fun newInstance(): GameFragment {
            val fragment = GameFragment()
            return fragment
        }
    }

    override fun setPresenter(presenter: GameContract.Presenter) {
        gamePresenter = presenter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ViewHolderListener) {
            parentActivity = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement ViewHolderListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        parentActivity = null
    }

    override fun removeLife() {
        vibrate()
        val childCount = life_holder.childCount
        if (childCount > 0) {
            life_holder.removeViewAt(life_holder.childCount - 1)
        }
    }

    override fun addLife() {
        life_holder.addView(lifeView)
    }

    override fun showLives() {
    }


    override fun showScore(score: Int) {
        score_view.text = Integer.toString(score)
    }

    override fun showGameOver() {
        number.text = "0"
        parentActivity?.gameOver(true, score_view)
        Toast.makeText(context, "Game Over.", Toast.LENGTH_LONG).show()

    }


    override fun totalLife(): Int {
        return life_holder.childCount
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater!!.inflate(R.layout.fragment_game, container, false)

        val inf  = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        lifeView = inf.inflate(R.layout.game_life, null) as ImageView

        return root;
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscriptions = subscribeAll()

    }

    override fun increaseNumber() {
        val current = Integer.valueOf(number.text.toString())
        number.text = "${current + 1}"
    }

    override fun onStop() {
        super.onStop()
        subscriptions.unsubscribe()
    }

    override fun onResume() {
        super.onResume()
        subscriptions = subscribeAll()
    }


    private fun vibrate() {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(600);
    }

    override fun playSound() {
        val  mp = MediaPlayer.create(context, R.raw.click);
        mp.start();
    }

    private fun subscribeAll() : CompositeSubscription {
        return CompositeSubscription(
                RxView.clicks(number).subscribe {
                    gamePresenter.checkResult(GameButton.NONE, currentNumber)
                },

                Observable.interval(1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { increaseNumber() },

                RxView.clicks(fizz).subscribe {
                    gamePresenter.checkResult(GameButton.FIZZ, currentNumber)
                },

                RxView.clicks(buzz).subscribe {
                    gamePresenter.checkResult(GameButton.BUZZ, currentNumber)
                },

                RxView.clicks(fizzbuzz).subscribe {
                    gamePresenter.checkResult(GameButton.FIZZBUZZ, currentNumber)
                },

                RxTextView.textChanges(number).map {
                    Integer.valueOf(it.toString())
                }.subscribe {
                    currentNumber = it
                }
        )
    }



}
