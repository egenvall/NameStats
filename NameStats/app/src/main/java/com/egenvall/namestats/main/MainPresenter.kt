package com.egenvall.namestats.main

import com.egenvall.namestats.base.presentation.BasePresenter
import com.egenvall.namestats.common.di.scope.PerScreen
import com.egenvall.namestats.base.presentation.BaseView
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor() : BasePresenter<MainPresenter.View>() {

  //Get list of contacts somehow

    fun getContacts(){

    }

//===================================================================================
// View Interface
//===================================================================================

    interface View : BaseView {
        fun setContactList()
        fun showMessage(str: String)
    }


    //===================================================================================
//  Lifecycle Methods
//===================================================================================
    override fun onViewAttached() {}
    override fun onViewDetached() {}
    override fun unsubscribe() {

    }
}