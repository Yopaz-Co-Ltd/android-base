package com.kira.android_base.base.sharedpreferences

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DefaultSharedPreferencesTest {

    companion object {
        private const val TEST_KEY = "test_key"
    }

    private lateinit var defaultSharedPreferences: DefaultSharedPreferences

    @Before
    fun setUp() {
        defaultSharedPreferences = DefaultSharedPreferences(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        defaultSharedPreferences.remove(TEST_KEY)
    }

    @Test
    fun getStringSuccessfully_returnsString() {
        val key = TEST_KEY
        val value = "value"
        defaultSharedPreferences.putString(key, value)
        val gottenValue = defaultSharedPreferences.getString(key, "")
        assert(gottenValue == value)
    }

    @Test
    fun putStringSuccessfully() {
        val key = TEST_KEY
        val value = "value"
        defaultSharedPreferences.putString(key, value)
        val gottenValue = defaultSharedPreferences.getString(key, "")
        assert(gottenValue == value)
    }

    @Test
    fun putStringSuccessfully_overwriteOldValue() {
        val key = TEST_KEY
        val oldValue = "old_value"
        val newValue = "new_value"
        defaultSharedPreferences.putString(key, oldValue)
        defaultSharedPreferences.putString(key, newValue)
        val gottenValue = defaultSharedPreferences.getString(key, "")
        assert(gottenValue == newValue)
    }

    @Test
    fun removeSuccessfully() {
        val key = TEST_KEY
        val value = "value"
        defaultSharedPreferences.putString(key, value)
        defaultSharedPreferences.remove(key)
        val gottenValue = defaultSharedPreferences.getString(key, "")
        assert(gottenValue == "")
    }
}
