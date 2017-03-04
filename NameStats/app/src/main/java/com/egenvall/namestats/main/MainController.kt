package com.egenvall.namestats.main

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.egenvall.namestats.NameStatsApp
import com.egenvall.namestats.R
import com.egenvall.namestats.base.presentation.BaseController
import com.egenvall.namestats.common.di.component.DaggerMainViewComponent
import com.egenvall.namestats.common.di.component.MainViewComponent
import com.egenvall.namestats.common.di.module.ActivityModule
import com.egenvall.namestats.extension.hide
import com.egenvall.namestats.extension.show
import com.egenvall.namestats.extension.showSnackbar
import com.egenvall.namestats.model.Contact
import com.egenvall.namestats.model.ContactItem
import com.egenvall.namestats.search.DetailController
import com.genius.groupie.ExpandableGroup
import com.genius.groupie.GroupAdapter
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.screen_main.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.Subscriptions
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainController : BaseController<MainPresenter.View, MainPresenter>(),
        MainPresenter.View {
    private lateinit var mainComponent: MainViewComponent
    override val passiveView: MainPresenter.View = this
    @Inject override lateinit var presenter: MainPresenter
    @LayoutRes override val layoutResId: Int = R.layout.screen_main
    private lateinit var contactsRecycler: RecyclerView
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var searchTextView: EditText
    private var textSub = Subscriptions.unsubscribed()
    private lateinit var searchButton : ImageView


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
        groupAdapter = GroupAdapter()
        contactsRecycler = view.contacts_recycler
        contactsRecycler.setHasFixedSize(false)
        contactsRecycler.layoutManager = LinearLayoutManager(applicationContext)
        contactsRecycler.adapter = groupAdapter

        searchButton = view.searchImageView
        searchButton.setOnClickListener {
            transitionToDetailsScreen(Contact(name = searchTextView.text.toString().substringBefore(" ")))
        }
        searchTextView = view.searchTextView
        setupTextSubscription()

    }

    fun hideKeyboard(){
        activity?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
    }

    fun setupTextSubscription(){
        textSub = RxTextView.textChanges(searchTextView)
                .map { s -> s.toString()}
                .throttleLast(200, TimeUnit.MILLISECONDS) //Emit only the last item in 200ms interval
                .debounce (500, TimeUnit.MILLISECONDS)   //Emit the last item if 500ms has passed with no more emits
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ finalText ->
                    if (finalText.length >=2){
                        showSearchButton()
                    }
                    else{
                        hideSearchButton()
                    }
                })
    }

    fun hideSearchButton(){
        with(searchButton) {
            this.animate().alpha(0.0f).translationX(-50f).setDuration(300)
                    .withEndAction { searchButton.hide() }
        }
    }
    //Looks ugly but will do or now
    fun showSearchButton(){
        if (searchButton.visibility != View.VISIBLE){
            with(searchButton){
                this.show()
                this.alpha = 0.0f
                this.animate().alpha(1.0f).translationX(10f).setDuration(600)
            }
        }
    }
    override fun onAttach(view: View) {
        super.onAttach(view)
        searchTextView.clearFocus()
        hideKeyboard()
        presenter.getContacts()
    }

//===================================================================================
// Navigation
//===================================================================================

    fun transitionToDetailsScreen(contact: Contact){
        textSub.unsubscribe()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.getWindowToken(), 0)
        router.pushController(RouterTransaction.with(DetailController(contact))
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler()))
    }

//===================================================================================
// Dependency injection
//===================================================================================

    override fun initInjection() {
        val act = activity as AppCompatActivity
        mainComponent = DaggerMainViewComponent.builder()
                .appComponent((act.application as NameStatsApp).appComponent)
                .activityModule(ActivityModule(act))
                .build()
        mainComponent.inject(this)
    }


//===================================================================================
// View methods
//===================================================================================
    override fun setContactList(group: List<ExpandableGroup>) {
        group.forEach { groupAdapter.add(it) }
    }

    override fun showMessage(message: String) {
        view?.showSnackbar(message)
    }

    override fun clicked(contact: ContactItem) {
        transitionToDetailsScreen(Contact(contact.name.substringBefore(" "),contact.number))
    }
}

