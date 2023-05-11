package com.kira.android_base.main.fragments.login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.kira.android_base.R
import com.kira.android_base.base.repository.auth.DefaultAuthRepository
import com.kira.android_base.base.supports.DataBindingIdlingResource
import com.kira.android_base.base.supports.EspressoIdlingResource
import com.kira.android_base.base.supports.monitorActivity
import com.kira.android_base.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class LoginFragmentTest {

    companion object {
        private const val VALID_TEST_EMAIL = "kira@gmail.com"
        private const val VALID_TEST_PASSWORD = "kira1234"
    }

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var defaultAuthRepository: DefaultAuthRepository

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        // GIVEN
        runTest {
            defaultAuthRepository.logout()
            activityScenario = ActivityScenario.launch(MainActivity::class.java)
            dataBindingIdlingResource.monitorActivity(activityScenario)
        }
    }

    @After
    fun tearDown() {
        runTest {
            defaultAuthRepository.logout()
            activityScenario.close()
        }
    }

    @Test
    fun loginSuccessfully_opensHome() {
        // WHEN
        onView(withId(R.id.edit_text_email)).perform(replaceText(VALID_TEST_EMAIL))
        onView(withId(R.id.edit_text_password)).perform(replaceText(VALID_TEST_PASSWORD))
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withId(R.id.linear_layout_root_home)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithEmptyEmail_showsError() {
        // WHEN
        onView(withId(R.id.edit_text_password)).perform(replaceText(VALID_TEST_PASSWORD))
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withText(R.string.error_invalid_email)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithEmptyPassword_showsError() {
        // WHEN
        onView(withId(R.id.edit_text_email)).perform(replaceText(VALID_TEST_EMAIL))
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withText(R.string.error_password_does_not_have_enough_characters)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithEmptyEmailAndPassword_showError() {
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withText(R.string.error_invalid_email)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithInvalidEmail_showsError() {
        // WHEN
        onView(withId(R.id.edit_text_email)).perform(replaceText("kira"))
        onView(withId(R.id.edit_text_password)).perform(replaceText(VALID_TEST_PASSWORD))
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withText(R.string.error_invalid_email)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithInvalidPassword_showsError() {
        // WHEN
        onView(withId(R.id.edit_text_email)).perform(replaceText(VALID_TEST_EMAIL))
        onView(withId(R.id.edit_text_password)).perform(replaceText("kira"))
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withText(R.string.error_password_does_not_have_enough_characters)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithInvalidEmailAndPassword_showsError() {
        // WHEN
        onView(withId(R.id.edit_text_email)).perform(replaceText("kira"))
        onView(withId(R.id.edit_text_password)).perform(replaceText("kira"))
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withText(R.string.error_invalid_email)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithInvalidEmailAndValidPassword_showsError() {
        // WHEN
        onView(withId(R.id.edit_text_email)).perform(replaceText("kira"))
        onView(withId(R.id.edit_text_password)).perform(replaceText(VALID_TEST_PASSWORD))
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withText(R.string.error_invalid_email)).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithValidEmailAndInvalidPassword_showsError() {
        // WHEN
        onView(withId(R.id.edit_text_email)).perform(replaceText(VALID_TEST_EMAIL))
        onView(withId(R.id.edit_text_password)).perform(replaceText("kira"))
        onView(withId(R.id.b_login)).perform(click())

        // THEN
        onView(withText(R.string.error_password_does_not_have_enough_characters)).check(matches(isDisplayed()))
    }
}
