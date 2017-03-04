package com.egenvall.namestats.model


data class Contact(val name: String, val number: String = "")
data class ContactList(val contacts : MutableList<Contact>)


data class NameInfo(val maleCount: String = "0", val femaleCount: String = "0")

//JSON Request to API
data class Request(val query: List<Query>, val response: Response)
data class Query(val code : String, val selection: Selection)
data class Selection(val filter: String, val values: List<String>)
data class Response(val format: String = "json")

//JSON Response from API
data class ServerResponse(val data: List<PersonData>)
data class PersonData(val values: List<String>)