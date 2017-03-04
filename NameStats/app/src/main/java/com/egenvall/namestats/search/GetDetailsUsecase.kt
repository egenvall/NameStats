package com.egenvall.namestats.search

import com.egenvall.namestats.base.domain.ReactiveUseCase
import com.egenvall.namestats.common.threading.AndroidUiExecutor
import com.egenvall.namestats.common.threading.RxIoExecutor
import com.egenvall.namestats.model.NameInfo
import com.egenvall.namestats.network.Repository
import rx.Observable
import rx.Observer
import javax.inject.Inject


class GetDetailsUsecase @Inject constructor(val repository: Repository, uiExec : AndroidUiExecutor, ioExec : RxIoExecutor) :
        ReactiveUseCase<NameInfo>(uiExec,ioExec) {
    private var name: String = ""
    fun getDetailsFor(name: String, observer: Observer<NameInfo>){
        this.name = name
        executeUsecase(observer)
    }
    private fun executeUsecase(observer: Observer<NameInfo>) {
        super.executeUseCase({ observer.onNext(it) }, { observer.onError(it) }, { observer.onCompleted() })
    }

    override fun useCaseObservable(): Observable<NameInfo> {
        return repository.getDetails(name)
    }
}