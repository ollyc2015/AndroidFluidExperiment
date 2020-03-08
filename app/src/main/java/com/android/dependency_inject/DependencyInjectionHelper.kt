package com.android.dependency_inject


import android.content.Context
import com.android.ui.loadSettings.SettingsRepo
import com.lush.retrofit.soa.service.RetrofitApiClient
import com.lush.retrofit.soa.service.settings.SettingsApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * Base class for dependency injection
 *
 * @author Olly Curtis
 */

object DependencyInjectionHelper : KoinComponent {

    fun startKoin(context: Context) {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(
                    getRepoModules(),
                    getServiceModules()
            )
        }
    }


    private fun getRepoModules(): Module {
        return module {
            single { SettingsRepo() }
        }
    }

    private fun getServiceModules(): Module {
        return module {
            single {
                RetrofitApiClient.getClient()?.create(SettingsApi::class.java)
                        ?: throw Exception("Retrofit Client Error")
            }
        }
    }
}

