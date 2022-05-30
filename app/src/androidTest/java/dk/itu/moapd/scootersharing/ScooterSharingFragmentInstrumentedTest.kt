package dk.itu.moapd.scootersharing

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScooterSharingFragmentInstrumentedTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(ScooterSharingActivity::class.java)

    @Test
    fun checkStartRideButtonText() {
        onView(withId(R.id.start_button)).check(matches(withText("Start Ride")))
    }

    @Test
    fun checkRideHistoryButtonText() {
        onView(withId(R.id.rental)).check(matches(withText("Ride History")))
    }

    @Test
    fun pressStartRideButton() {
        onView(withId(R.id.start_button)).perform(ViewActions.click())
        onView(withId(R.id.map)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun pressRideHistoryButton() {
        onView(withId(R.id.rental)).perform(ViewActions.click())
        //TODO onView(withId(R.id.<>)).check(matches(isDisplayed()))
    }
}