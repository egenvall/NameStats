package com.egenvall.namestats

import android.app.Application
import com.egenvall.namestats.common.di.component.AppComponent
import com.egenvall.namestats.common.di.component.DaggerAppComponent
import com.egenvall.namestats.common.di.module.AppModule
import com.egenvall.namestats.common.di.module.SCBModule

class NameStatsApp : Application() {

    internal lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initInjection()
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .sCBModule(SCBModule(this))
                .build()
    }
}