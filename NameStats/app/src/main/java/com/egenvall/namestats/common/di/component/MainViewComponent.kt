package com.egenvall.namestats.common.di.component

import com.egenvall.namestats.common.di.module.ActivityModule
import com.egenvall.namestats.common.di.module.MainViewModule
import com.egenvall.namestats.common.di.scope.PerScreen
import com.egenvall.namestats.main.MainController
import dagger.Component


@PerScreen
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class, MainViewModule::class))
interface MainViewComponent : ActivityComponent {
    fun inject(mainController: MainController)
}