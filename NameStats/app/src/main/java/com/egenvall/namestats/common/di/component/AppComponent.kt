package com.egenvall.namestats.common.di.component

import android.content.Context
import com.egenvall.namestats.common.di.module.AppModule
import com.egenvall.namestats.common.di.module.SCBModule
import com.egenvall.namestats.network.Repository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, SCBModule::class))
interface AppComponent {
        fun context(): Context
        fun repository() : Repository
}
