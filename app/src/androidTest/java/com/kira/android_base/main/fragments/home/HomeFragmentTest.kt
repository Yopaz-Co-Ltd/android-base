package com.kira.android_base.main.fragments.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.kira.android_base.R
import com.kira.android_base.base.repository.auth.DefaultAuthRepository
import com.kira.android_base.base.supports.DataBindingIdlingResource
import com.kira.android_base.base.supports.EspressoIdlingResource
import com.kira.android_base.base.supports.monitorActivity
import com.kira.android_base.main.MainActivity
import com.kira.android_base.main.fragments.login.LoginFragmentTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var defaultAuthRepository: DefaultAuthRepository

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun registerIdlingResources() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        // GIVEN
        runTest {
            // Logout and Re-login
            defaultAuthRepository.logout()
            activityScenario = ActivityScenario.launch(MainActivity::class.java)
            dataBindingIdlingResource.monitorActivity(activityScenario)

            onView(withId(R.id.edit_text_email)).perform(ViewActions.replaceText(LoginFragmentTest.VALID_TEST_EMAIL))
            onView(withId(R.id.edit_text_password)).perform(ViewActions.replaceText(LoginFragmentTest.VALID_TEST_PASSWORD))
            onView(withId(R.id.b_login)).perform(click())
        }
    }

    @After
    fun tearDown() {
        runTest {
            defaultAuthRepository.logout()
            activityScenario.close()
        }
    }
}
