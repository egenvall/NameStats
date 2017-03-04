package com.egenvall.namestats.common.di.component

import com.egenvall.namestats.common.di.module.ActivityModule
import com.egenvall.namestats.common.di.module.DetailViewModule
import com.egenvall.namestats.common.di.scope.PerScreen
import com.egenvall.namestats.search.DetailController
import dagger.Component

@PerScreen
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class, DetailViewModule::class))
interface DetailViewComponent : ActivityComponent {
    fun inject(detailController: DetailController)
}