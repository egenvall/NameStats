package com.egenvall.namestats.common.di.component

import android.app.Activity
import com.egenvall.namestats.common.di.module.ActivityModule
import com.egenvall.namestats.common.di.scope.PerScreen
import dagger.Component

@PerScreen
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun activity(): Activity
}