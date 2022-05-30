package dk.itu.moapd.scootersharing

import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScooterSharingMapInstrumentedTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(ScooterSharingActivity::class.java)

    //Tests that selecting a scooter makes the qr button appear
    @Test
    fun pressScooterMarkerButton() {
        onView(withId(R.id.start_button)).perform(click())
        onView(withId(R.id.map)).check(matches(isDisplayed()))

        val device = UiDevice.getInstance(getInstrumentation())
        val marker = device.findObject(UiSelector().apply {
            descriptionContains("Lime")
        })
        marker.click()

        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun pressScooterQRScannerButton() {
        onView(withId(R.id.start_button)).perform(click())
        onView(withId(R.id.map)).check(matches(isDisplayed()))

        val device = UiDevice.getInstance(getInstrumentation())
        val marker = device.findObject(UiSelector().apply {
            descriptionContains("Lime")
        })
        marker.click()

        onView(withId(R.id.fab)).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())
    }
}