package com.android.dependency_inject

import android.app.Application
import android.content.Context

/**
 * To initiate dependency helper
 *
 * @author Olly
 */
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        DependencyInjectionHelper.startKoin(applicationContext)
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}
