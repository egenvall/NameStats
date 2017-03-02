package com.egenvall.namestats.common.threading


import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AndroidUiExecutor @Inject constructor() : UiExecutor {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}