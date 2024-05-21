package com.sagar.tendable

import android.app.Application
import com.sagar.tendable.di.networkModule
import com.sagar.tendable.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val modulesList = listOf(networkModule, repositoryModule)
        startKoin {
            androidContext(this@MyApplication) //private val context: Context by inject() in view model
            modules(modulesList)
        }
    }
}