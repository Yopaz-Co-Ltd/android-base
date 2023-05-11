package com.kira.android_base.base.supports

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.kira.android_base.R
import com.kira.android_base.main.MainActivity

const val THEME_EXTRAS_BUNDLE_KEY =
    "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY"

/**
 * launchFragmentInContainer from the androidx.fragment:fragment-testing library
 * is NOT possible to use right now as it uses a hardcoded Activity under the hood
 * (i.e. [EmptyFragmentActivity]) which is not annotated with @AndroidEntryPoint.
 *
 * As a workaround, use this function that is equivalent to launchFragmentInContainer
 */
inline fun <reified T : Fragment> launchFragmentInMainContainer(
    dataBindingIdlingResource: DataBindingIdlingResource,
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.Theme,
    crossinline action: Fragment.() -> Unit = {}
): ActivityScenario<MainActivity> {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            MainActivity::class.java
        )
    ).putExtra(THEME_EXTRAS_BUNDLE_KEY, themeResId)

    val activityScenario = ActivityScenario.launch<MainActivity>(startActivityIntent)
    dataBindingIdlingResource.monitorActivity(activityScenario)
    return activityScenario.onActivity { activity ->
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitAllowingStateLoss()

        fragment.action()
    }
}
