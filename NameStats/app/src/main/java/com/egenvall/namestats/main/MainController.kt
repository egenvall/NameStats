package com.egenvall.namestats.main

import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egenvall.namestats.NameStatsApp
import com.egenvall.namestats.R
import com.egenvall.namestats.base.presentation.BaseController
import com.egenvall.namestats.common.di.component.DaggerMainViewComponent
import com.egenvall.namestats.common.di.component.MainViewComponent
import com.egenvall.namestats.common.di.module.ActivityModule
import com.genius.groupie.ExpandableGroup
import com.genius.groupie.GroupAdapter
import kotlinx.android.synthetic.main.screen_main.view.*
import javax.inject.Inject


class MainController : BaseController<MainPresenter.View, MainPresenter>(),
        MainPresenter.View {
    private lateinit var mainComponent: MainViewComponent
    override val passiveView: MainPresenter.View = this
    @Inject override lateinit var presenter: MainPresenter
    @LayoutRes override val layoutResId: Int = R.layout.screen_main
    private lateinit var contactsRecycler: RecyclerView
    private lateinit var groupAdapter: GroupAdapter


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
    }

    fun openSearchScreen(origin: Boolean){
        //Childrouter?
        /* router.pushController(RouterTransaction.with(SearchController())
                 .pushChangeHandler(HorizontalChangeHandler())
                 .popChangeHandler(HorizontalChangeHandler()))*/
    }


    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.getContacts()
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

    override fun showMessage(str: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clicked(name: String) {
        Log.d("TAG","Clicked $name")
    }

}

