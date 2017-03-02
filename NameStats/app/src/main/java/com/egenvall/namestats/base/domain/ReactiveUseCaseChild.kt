package com.egenvall.namestats.base.domain

import rx.Observable

abstract class ReactiveUseCaseChild<ObservableType> {

    abstract fun observable(): Observable<ObservableType>
}