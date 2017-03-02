package com.egenvall.namestats.contacts

import com.egenvall.namestats.model.ContactList
import rx.Observable


interface ContentResolverRepository {
    fun getContacts() : Observable<ContactList>
}