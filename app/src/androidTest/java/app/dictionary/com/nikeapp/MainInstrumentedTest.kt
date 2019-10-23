package app.dictionary.com.nikeapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import app.dictionary.com.nikeapp.ui.MainActivity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainInstrumentedTest {

    private lateinit var stringToBetyped: String

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso"
    }

    @Test
    fun changeText_sameActivity() {

        onView(withId(R.id.thumbs_up_title)).perform(click())
        onView(withId(R.id.thumbs_up_title)).check(matches(withTagValue(equalTo(R.drawable.ic_keyboard_arrow_up_24px))))

        onView(withId(R.id.thumbs_up_title)).perform(click())
        onView(withId(R.id.thumbs_up_title)).check(matches(withTagValue(equalTo(R.drawable.ic_keyboard_arrow_down_24px))))

        onView(withId(R.id.thumbs_up_title)).perform(click())
        onView(withId(R.id.thumbs_up_title)).check(matches(withTagValue(equalTo(0))))

        onView(withId(R.id.thumbs_down_title)).perform(click())
        onView(withId(R.id.thumbs_down_title)).check(matches(withTagValue(equalTo(R.drawable.ic_keyboard_arrow_up_24px))))

        onView(withId(R.id.thumbs_down_title)).perform(click())
        onView(withId(R.id.thumbs_down_title)).check(matches(withTagValue(equalTo(R.drawable.ic_keyboard_arrow_down_24px))))

        onView(withId(R.id.thumbs_down_title)).perform(click())
        onView(withId(R.id.thumbs_down_title)).check(matches(withTagValue(equalTo(0))))


    }
}
