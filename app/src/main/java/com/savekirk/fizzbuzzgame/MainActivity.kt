package com.savekirk.fizzbuzzgame

import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.ChangeBounds
import android.transition.Explode
import android.transition.Slide
import android.transition.TransitionInflater
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.savekirk.fizzbuzzgame.Data.GameDataRepository
import com.savekirk.fizzbuzzgame.game.GameFragment

import com.savekirk.fizzbuzzgame.game.GamePresenter
import com.savekirk.fizzbuzzgame.home.HomeFragment
import com.savekirk.fizzbuzzgame.home.HomePresenter

class MainActivity : AppCompatActivity(), ViewHolderListener {

    private var homeFragment : HomeFragment? = null
    private var gameFragment : GameFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment.newInstance()
        changeView(homeFragment!!)
        HomePresenter(GameDataRepository, homeFragment!!)

    }

    override fun gameOver(shareElement : Boolean, sharedElement : View?) {
        homeFragment = HomeFragment.newInstance()
        if (shareElement && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupTransition(homeFragment!!)
        }

        changeView(homeFragment!!, true, sharedElement)
        HomePresenter(GameDataRepository, homeFragment!!)
    }


    override fun newGame() {
        gameFragment = GameFragment.newInstance()
        GamePresenter(GameDataRepository, gameFragment!!)
        changeView(gameFragment!!)
    }

    fun changeView(fragment: Fragment, shareElement: Boolean = false, sharedElement: View? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame, fragment)
        if (shareElement) {
            transaction.addSharedElement(sharedElement, getString(R.string.score_view))
        }
        transaction.commit()
    }

    @TargetApi(21)
    private fun setupTransition(fragment: HomeFragment) {
        // Defines enter transition for all fragment views
        val slideTransition = Explode();
        slideTransition.setDuration(1000);
        fragment.setEnterTransition(slideTransition);
        val changeBoundsTransition = ChangeBounds();
        fragment.setSharedElementEnterTransition(changeBoundsTransition);

    }
}
