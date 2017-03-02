package com.egenvall.namestats.model


data class Contact(val name: String, val number: String)
data class ContactList(val contacts : MutableList<Contact>)