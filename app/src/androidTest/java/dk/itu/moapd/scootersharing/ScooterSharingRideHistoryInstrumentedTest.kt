package dk.itu.moapd.scootersharing

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScooterSharingRideHistoryInstrumentedTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(ScooterSharingActivity::class.java)

    //TODO
    @Test
    fun todo() {}
}