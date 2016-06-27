package com.savekirk.fizzbuzzgame.home

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import com.savekirk.fizzbuzzgame.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentTest {

    @Rule @JvmField
    val testRule : ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun shouldAlways_displayHighScore() {
    }


}