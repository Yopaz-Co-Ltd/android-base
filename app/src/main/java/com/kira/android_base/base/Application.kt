package com.kira.android_base.base

import androidx.multidex.MultiDexApplication
import com.kira.android_base.base.api.APIsModule
import com.kira.android_base.base.database.databaseModule
import com.kira.android_base.base.reactivex.reactivexModule
import com.kira.android_base.base.repository.repositoryModule
import com.kira.android_base.base.sharedpreference.sharedPreferencesModule
import com.kira.android_base.main.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(
                APIsModule,
                databaseModule,
                reactivexModule,
                repositoryModule,
                sharedPreferencesModule,
                mainModule
            )
        }
    }
}
