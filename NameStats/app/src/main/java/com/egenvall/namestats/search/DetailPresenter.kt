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
               performViewAction { setPercentageInformation(calculateGender(name,details)) }
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

    private fun calculateGender(name: String, details: NameInfo): String {
        var gender = ""
        var certainty: Double = 0.0
        val fC = details.femaleCount.toInt()
        val mC = details.maleCount.toInt()
        if ((fC == 0) and (mC == 0)) return "No people with that name"
        else {
            when (fC > mC) {
                true -> {
                    certainty = 1 - (mC.toDouble() / fC.toDouble())
                    gender += "female"
                }
                false -> {
                    certainty = 1 - (fC.toDouble() / mC.toDouble())
                    gender += "male"
                }
            }

            val result: String
            if (certainty.toString()[0] == '1') result = "100"
            else {
                result = certainty.toString().substringAfter(".").take(2)
            }
            return "$name is a $gender with $result% certainty"
        }
    }

//===================================================================================
// View Interface
//===================================================================================

    interface View : BaseView, BaseDataView {
        fun setDetails(details: NameInfo)
        fun showMessage(message: String)
        fun setPercentageInformation(info: String)
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