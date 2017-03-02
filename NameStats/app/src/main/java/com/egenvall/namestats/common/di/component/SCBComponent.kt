package com.egenvall.namestats.common.di.component

import com.egenvall.namestats.NameStatsApp
import com.egenvall.namestats.common.di.module.SCBModule
import com.egenvall.namestats.network.Repository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SCBModule::class))
interface SCBComponent {
    fun inject(application : NameStatsApp)
    fun repository(): Repository
}