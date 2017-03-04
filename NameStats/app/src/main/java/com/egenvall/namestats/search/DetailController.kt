package com.egenvall.namestats.search

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.egenvall.namestats.NameStatsApp
import com.egenvall.namestats.R
import com.egenvall.namestats.base.presentation.BaseController
import com.egenvall.namestats.common.di.component.DaggerDetailViewComponent
import com.egenvall.namestats.common.di.component.DetailViewComponent
import com.egenvall.namestats.common.di.module.ActivityModule
import com.egenvall.namestats.extension.hide
import com.egenvall.namestats.extension.show
import com.egenvall.namestats.extension.showSnackbar
import com.egenvall.namestats.model.Contact
import com.egenvall.namestats.model.NameInfo
import kotlinx.android.synthetic.main.screen_detail.view.*
import net.bohush.geometricprogressview.GeometricProgressView
import javax.inject.Inject


class DetailController(val contact: Contact = Contact("No name","")) : BaseController<DetailPresenter.View, DetailPresenter>(),
        DetailPresenter.View {
    private lateinit var detailComponent: DetailViewComponent
    override val passiveView: DetailPresenter.View = this
    @Inject override lateinit var presenter: DetailPresenter
    @LayoutRes override val layoutResId: Int = R.layout.screen_detail
    private lateinit var name: TextView
    private lateinit var maleAmount: TextView
    private lateinit var femaleAmount: TextView
    private lateinit var progressView: GeometricProgressView
    private lateinit var femaleIcon: ImageView
    private lateinit var maleIcon: ImageView
    private lateinit var sendTextButton: Button

//===================================================================================
// Lifecycle methods and initialization
//===================================================================================
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(layoutResId, container, false)
        onViewBound(view)
        return view
    }

    override fun onViewBound(view: View) {
        initInjection()
        name = view.header_name
        name.text = contact.name
        maleAmount = view.male_amount
        maleIcon = view.male_icon
        femaleIcon = view.female_icon
        femaleAmount = view.female_amount
        progressView = view.progressView
        sendTextButton = view.send_text_button
        sendTextButton.isEnabled = false
        sendTextButton.setOnClickListener { sendTextMessage() }
        configureProgressView()

    }

    fun configureProgressView(){
        progressView.setType(GeometricProgressView.TYPE.KITE);
        progressView.setNumberOfAngles(6);
        progressView.setColor(Color.parseColor("#FA5C68"))
        progressView.setDuration(500);
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.getNameInfo(contact.name)

    }

    override fun handleBack(): Boolean {
        router.popCurrentController()
        return true
    }



//===================================================================================
// UI Interactions
//===================================================================================

    fun showIconsAndNumbers(){
        applyAnimation(femaleIcon)
        applyAnimation(femaleAmount)
        applyAnimation(maleIcon)
        applyAnimation(maleAmount)
        displaySendButton()

    }
    fun applyAnimation(view: View){
        with(view){
            this.show()
            this.alpha = 0.0f
            this.animate().alpha(1.0f).setDuration(600)
        }
    }

    fun displaySendButton(){
        with(sendTextButton){
            this.show()
            this.alpha = 0.0f
            if (contactNumberExists()){
                this.animate().alpha(1.0f).translationY(-50f).setDuration(600)
            }
            else{
                sendTextButton.isClickable = false
                sendTextButton.text = "No number"
                sendTextButton.animate().alpha(0.4f).translationY(-50f).setDuration(600)
            }
        }
    }

    fun contactNumberExists() : Boolean = contact.number.isNotEmpty()

    fun calculateGender(details: NameInfo){
        var gender = ""
        var certainty: Double = 0.0
        with(details){
            if (femaleCount.toInt() <= maleCount.toInt()){
                certainty = 1-(femaleCount.toDouble()/maleCount.toDouble())
                gender += "male"
            }
            else{
                certainty = 1 - (maleCount.toDouble()/femaleCount.toDouble())
                gender += "female"
            }
        }
        val result: String
        if (certainty.toString()[0] == '1') result = "100"
        else{ result = certainty.toString().substringAfter(".").take(2) }
        setPercentageInformation(gender,result)
    }

    fun setPercentageInformation(gender: String, certainty: String){
        view?.gender_stats?.text = "${contact.name} is a $gender with $certainty% certainty"
        applyAnimation(view?.gender_stats as View)
    }

    fun sendTextMessage(){
        Log.d("TAG","Clicked button")
    }

//===================================================================================
// Dependency injection
//===================================================================================

    override fun initInjection() {
        val act = activity as AppCompatActivity
        detailComponent = DaggerDetailViewComponent.builder()
                .appComponent((act.application as NameStatsApp).appComponent)
                .activityModule(ActivityModule(act))
                .build()
        detailComponent.inject(this)
    }


    override fun setDetails(details: NameInfo) {
        progressView.hide()
        maleAmount.text = details.maleCount
        femaleAmount.text = details.femaleCount
        showIconsAndNumbers()
        calculateGender(details)
    }
    override fun showMessage(message: String) {
        view?.showSnackbar(message)
    }

}