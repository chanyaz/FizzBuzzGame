package com.savekirk.fizzbuzzgame

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.savekirk.fizzbuzzgame.home.HomeFragment
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

    fun setTextInTextView(value: String) : ViewAction {
        return object : ViewAction {
            @SuppressWarnings("unchecked")
            override fun getConstraints() : Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(TextView::class.java));
            }

            override fun perform(uiController : UiController, view: View) {
                (view as TextView).setText(value);
            }

            override fun getDescription() : String {
                return "replace text";
            }
        };
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
        onView(withId(R.id.number)).perform(setTextInTextView("4"))
        onView(withId(R.id.fizz)).perform(click())
        onView(withId(R.id.life_holder)).check(matches(allOf(
                isDisplayed(),
                hasChildren(_is(2))
        )))
    }

    @Test
    fun threeWrongAnswers_shouldTakeAllLives() {
        onView(withId(R.id.number)).perform(setTextInTextView("4"))
        onView(withId(R.id.fizz)).perform(click())
        onView(withId(R.id.number)).perform(setTextInTextView("6"))
        onView(withId(R.id.buzz)).perform(click())
        onView(withId(R.id.number)).perform(setTextInTextView("9"))
        onView(withId(R.id.fizzbuzz)).perform(click())
        onView(withId(R.id.play)).check(matches(isDisplayed()))
    }


}