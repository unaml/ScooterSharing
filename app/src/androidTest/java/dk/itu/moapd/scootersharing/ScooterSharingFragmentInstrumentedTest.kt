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

    //Checks that the text on the button says Start Ride
    @Test
    fun checkStartRideButtonText() {
        onView(withId(R.id.start_button)).check(matches(withText("Start Ride")))
    }

    //Checks that the text on the button says Ride History
    @Test
    fun checkRideHistoryButtonText() {
        onView(withId(R.id.rental)).check(matches(withText("Ride History")))
    }

    //Checks that the map opens when pressing the start ride button
    @Test
    fun pressStartRideButton() {
        onView(withId(R.id.start_button)).perform(ViewActions.click())
        onView(withId(R.id.map)).check(matches(ViewMatchers.isDisplayed()))
    }

}