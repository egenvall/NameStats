package com.egenvall.namestats.contacts

import com.egenvall.namestats.base.domain.ReactiveUseCase
import com.egenvall.namestats.common.threading.AndroidUiExecutor
import com.egenvall.namestats.common.threading.RxIoExecutor
import com.egenvall.namestats.model.Contact
import com.egenvall.namestats.model.ContactList
import rx.Observable
import rx.Observer
import javax.inject.Inject

class GetContactsUsecase  @Inject constructor(val repository: ContentResolverRepository, uiExec : AndroidUiExecutor, ioExec : RxIoExecutor) :
        ReactiveUseCase<ContactList>(uiExec,ioExec) {


    fun executeUsecase(observer: Observer<ContactList>) {
        super.executeUseCase({ observer.onNext(it) }, { observer.onError(it) }, { observer.onCompleted() })
    }

    override fun useCaseObservable(): Observable<ContactList> {
        return repository.getContacts()
    }
}