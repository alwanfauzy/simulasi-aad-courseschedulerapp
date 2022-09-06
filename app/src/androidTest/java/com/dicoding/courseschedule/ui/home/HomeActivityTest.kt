package com.dicoding.courseschedule.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    var mActivityTestRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Intents.init()
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun whenClickAddCourse_ShouldDisplayAddCourseActivity() {
        onView(withId(R.id.action_add)).perform(click())

        intended(hasComponent(AddCourseActivity::class.java.name))
    }
}
