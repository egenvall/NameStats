package com.egenvall.namestats.common.threading

import rx.Scheduler

interface UiExecutor {
    val scheduler: Scheduler
}