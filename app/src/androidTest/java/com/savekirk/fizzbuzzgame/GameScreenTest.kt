package com.savekirk.fizzbuzzgame

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is` as _is
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before


@RunWith(AndroidJUnit4::class)
@LargeTest
class GameScreenTest {



    /**
     * A custom {@link Matcher} which matches the number of children a view has
     */
    private fun hasChildren(numChildrenMatcher : Matcher<Int>) : Matcher<View> {

        return object : TypeSafeMatcher<View>() {

            /**
             * matching with viewgroup.getChildCount()
             */
            override fun matchesSafely(view : View) : Boolean {
                return view is ViewGroup && numChildrenMatcher.matches(view.getChildCount());
            }

            /**
             * gets the description
             */
            override fun describeTo(description : Description) {
                description.appendText(" a view with # children is ");
                numChildrenMatcher.describeTo(description);
            }
        }
    }


    @Rule @JvmField
    val testRule : ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun initTest() {
        testRule.activity.newGame()
    }

    @Test
    fun defaultScoreIsZero() {
        onView(withId(R.id.score_view)).check(matches(withText("0")))
    }

    @Test
    fun threeinitialGameLives() {
        onView(withId(R.id.life_holder)).check(matches(allOf(
                isDisplayed(),
                hasChildren(_is(3))
        )))
    }

    @Test
    fun wrongAnswer_shouldReduceLife() {
        onView(withId(R.id.number)).perform(doubleClick())
        onView(withId(R.id.number)).perform(click())
        onView(withId(R.id.life_holder)).check(matches(allOf(
                isDisplayed(),
                hasChildren(_is(2))
        )))
    }


    @Test
    fun clickingNumberView_shouldIncreaseNumber() {

        onView(withId(R.id.number)).perform(click())

        onView(withId(R.id.number)).check(matches(withText("1")))

    }

    @Test
    fun correctButtonClick_shouldIncreaseNumber() {

        onView(withId(R.id.number)).perform(doubleClick())

        onView(withId(R.id.fizz)).perform(click())

        onView(withId(R.id.number)).check(matches(withText("3")))
    }

}