package dk.itu.moapd.scootersharing

import android.R
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import dk.itu.moapd.scootersharing.fragments.RideHistoryFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScooterSharingRideHistoryInstrumentedTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(ScooterSharingActivity::class.java)


    @Test
    fun rentalHistoryButtonOpensRentalHistory() {
        onView(withId(dk.itu.moapd.scootersharing.R.id.rental)).perform(ViewActions.click())
    }
}