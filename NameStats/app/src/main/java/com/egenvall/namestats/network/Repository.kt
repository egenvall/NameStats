package com.egenvall.namestats.network

import com.egenvall.namestats.model.NameInfo
import rx.Observable


interface Repository {
    fun getDetails(name: String): Observable<NameInfo>
}