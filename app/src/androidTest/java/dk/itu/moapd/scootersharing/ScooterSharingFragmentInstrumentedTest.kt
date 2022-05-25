package dk.itu.moapd.scootersharing

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import dk.itu.moapd.scootersharing.fragments.ScooterSharingFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class ScooterSharingFragmentInstrumentedTest {

    private lateinit var scenario: ActivityScenario<ScooterSharingActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun checkStartRideButton(){
        launchFragmentInContainer<ScooterSharingFragment>()
        onView(withId(R.id.start_button)).check(matches(withText("Start Ride")))
    }

    /**@Test
    fun testStartRideButton(){
        onView(withId(R.id.start_button)).perform(click())

    }*/
}