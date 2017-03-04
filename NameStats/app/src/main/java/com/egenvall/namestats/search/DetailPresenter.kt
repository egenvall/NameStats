package com.egenvall.namestats.search

import android.util.Log
import com.egenvall.namestats.base.presentation.BasePresenter
import com.egenvall.namestats.base.presentation.BaseView
import com.egenvall.namestats.common.di.scope.PerScreen
import com.egenvall.namestats.model.NameInfo
import com.egenvall.namestats.model.ServerResponse
import rx.Observer
import javax.inject.Inject


@PerScreen
class DetailPresenter @Inject constructor(val detailsUsecase: GetDetailsUsecase) : BasePresenter<DetailPresenter.View>() {
   fun getNameInfo(name: String){
       detailsUsecase.getDetailsFor(name, object : Observer<NameInfo> {
           override fun onNext(details: NameInfo) {
               performViewAction { setDetails(details) }
           }

           override fun onError(e: Throwable?) {
               Log.e("Error", e.toString())
               performViewAction { showMessage("Sorry. Could not retrieve information") }
           }

           override fun onCompleted() {}
       })
   }

//===================================================================================
// View Interface
//===================================================================================

    interface View : BaseView {
        fun setDetails(details: NameInfo)
        fun showMessage(message: String)
    }

//===================================================================================
//  Lifecycle Methods
//===================================================================================
    override fun onViewAttached() {}
    override fun onViewDetached() {}
    override fun unsubscribe() {
    }
}