package com.egenvall.namestats.network

import com.egenvall.namestats.model.NameInfo
import com.egenvall.namestats.model.Request
import com.egenvall.namestats.model.ServerResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Url
import rx.Observable


interface SCBService {
    @POST
    fun getNameInfo(@Url url: String = "http://api.scb.se/OV0104/v1/doris/sv/ssd/START/BE/BE0001/BE0001FNamn10",@Body request: Request): Observable<ServerResponse>


}