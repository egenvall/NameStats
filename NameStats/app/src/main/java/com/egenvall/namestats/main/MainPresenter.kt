package com.egenvall.namestats.main

import com.egenvall.namestats.base.presentation.BaseDataView
import com.egenvall.namestats.base.presentation.BasePresenter
import com.egenvall.namestats.common.di.scope.PerScreen
import com.egenvall.namestats.base.presentation.BaseView
import com.egenvall.namestats.contacts.GetContactsUsecase
import com.egenvall.namestats.model.Contact
import com.egenvall.namestats.model.ContactItem
import com.egenvall.namestats.model.ContactList
import com.egenvall.namestats.model.ExpandableContactHeader
import com.genius.groupie.ExpandableGroup
import rx.Observer
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor(val getContactsUsecase: GetContactsUsecase) : BasePresenter<MainPresenter.View>() {


    fun getContacts(){
        getContactsUsecase.executeUsecase(object: Observer<ContactList>{
            override fun onNext(response: ContactList) {
                performViewAction { setContactList(formatContacts(response.contacts)) }
            }
            override fun onError(e: Throwable?) {
                performViewAction { showMessage("Could not fetch contacts") }
            }
            override fun onCompleted() {}
        })
    }

    /**
     * Format the retrieved List of [Contact] into [ContactItem]
     * These are then grouped by first character in the [ContactItem.name]
     * into a [Map] <Character, List<ContactItem>>
     * The Map is then traversed to construct[ExpandableGroup] [ExpandableContactHeader]
     * that are used by Groupie for databinding & to create the expandable list headers.
     */
    private fun formatContacts(input: List<Contact>) : List<ExpandableGroup>{
        val list = mutableListOf<ContactItem>()
        input.forEach {
            list.add(ContactItem(it.name,it.number){view.clicked(it)})
        }
        val map = list.groupBy{ it.name[0]}.toSortedMap()
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
        return groups
    }


//===================================================================================
// View Interface
//===================================================================================

    interface View : BaseView{
        fun setContactList(group: List<ExpandableGroup>)
        fun showMessage(message: String)
        fun clicked(contact: ContactItem)
    }

//===================================================================================
//  Lifecycle Methods
//===================================================================================
    override fun onViewAttached() {}
    override fun onViewDetached() {}
    override fun unsubscribe() {
        getContactsUsecase.unsubscribe()
    }
}