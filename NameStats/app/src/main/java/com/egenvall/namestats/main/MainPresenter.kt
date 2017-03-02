package com.egenvall.namestats.main

import com.egenvall.namestats.base.presentation.BasePresenter
import com.egenvall.namestats.common.di.scope.PerScreen
import com.egenvall.namestats.base.presentation.BaseView
import com.egenvall.namestats.model.ContactItem
import com.egenvall.namestats.model.ExpandableContactHeader
import com.genius.groupie.ExpandableGroup
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor() : BasePresenter<MainPresenter.View>() {
  //Get list of contacts somehow

    fun getContacts(){
        val listy = mutableListOf<ContactItem>()
        val retrievedList = listOf("Arne","Anka","Berit","Bertil","Sara","Ragnar","Paul","Asse")
        retrievedList.forEach {
            listy.add(ContactItem(it){view.clicked(it.name)})
        }
        val map = listy.groupBy{ it.name[0]}.toSortedMap()
        val groups = mutableListOf<ExpandableGroup>()
        for ((letter,li) in map) {
            val headerItem = ExpandableContactHeader(letter.toString(), li.size)
            val expandableGroup = ExpandableGroup(headerItem)
            for (item in li) {
                expandableGroup.add(item)
            }
            headerItem.setExpandableGroup(expandableGroup)
            groups.add(expandableGroup)
        }
        performViewAction { setContactList(groups) }
    }

//===================================================================================
// View Interface
//===================================================================================

    interface View : BaseView {
        fun setContactList(group: List<ExpandableGroup>)
        fun showMessage(str: String)
        fun clicked(name: String)
    }

//===================================================================================
//  Lifecycle Methods
//===================================================================================
    override fun onViewAttached() {}
    override fun onViewDetached() {}
    override fun unsubscribe() {

    }
}