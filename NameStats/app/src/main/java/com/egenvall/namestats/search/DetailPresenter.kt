package com.egenvall.namestats.search

import com.egenvall.namestats.base.presentation.BaseDataView
import com.egenvall.namestats.base.presentation.BasePresenter
import com.egenvall.namestats.base.presentation.BaseView
import com.egenvall.namestats.common.di.scope.PerScreen
import com.egenvall.namestats.model.NameInfo
import rx.Observer
import javax.inject.Inject


@PerScreen
class DetailPresenter @Inject constructor(val detailsUsecase: GetDetailsUsecase) : BasePresenter<DetailPresenter.View>() {

   fun getNameInfo(name: String){
       performViewAction { showProgress() }
       detailsUsecase.getDetailsFor(name, object : Observer<NameInfo> {
           override fun onNext(details: NameInfo) {
               performViewAction { setDetails(details) }
           }

           override fun onError(e: Throwable?) {
               performViewAction { showMessage("Sorry. Could not retrieve information") }
           }
           override fun onCompleted() {
               performViewAction { hideProgress() }
           }
       })
   }

//===================================================================================
// View Interface
//===================================================================================

    interface View : BaseView, BaseDataView {
        fun setDetails(details: NameInfo)
        fun showMessage(message: String)
    }

//===================================================================================
//  Lifecycle Methods
//===================================================================================
    override fun onViewAttached() {}
    override fun onViewDetached() {}
    override fun unsubscribe() {
        detailsUsecase.unsubscribe()
    }
}