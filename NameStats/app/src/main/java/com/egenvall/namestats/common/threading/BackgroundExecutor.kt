package com.egenvall.namestats.common.threading

import rx.Scheduler

interface BackgroundExecutor {
    val scheduler: Scheduler
}