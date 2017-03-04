package com.egenvall.namestats.contacts

import android.content.Context
import android.provider.ContactsContract
import com.egenvall.namestats.model.Contact
import com.egenvall.namestats.model.ContactList
import rx.Observable
import javax.inject.Inject


class ContentResolverRepositoryImpl @Inject constructor(val context: Context) : ContentResolverRepository{

    /**
     * Retrieves contacts from the phone
     */
    override fun getContacts(): Observable<ContactList> {
        val contactInfo = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
        val listOfNames = ContactList(mutableListOf())
        if (contactInfo != null){
            while (contactInfo.moveToNext()) { listOfNames.contacts.add(
                        Contact(transformNameToFirstUpperCase(
                                contactInfo.getString(contactInfo.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))),

                        contactInfo.getString(contactInfo.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.NUMBER))
                        )
                )
            }
        }
        contactInfo?.close()
        return Observable.just(listOfNames)
    }

    fun transformNameToFirstUpperCase(name: String) : String{
        return name[0].toUpperCase() + name.subSequence(1,name.length).toString()
    }
}