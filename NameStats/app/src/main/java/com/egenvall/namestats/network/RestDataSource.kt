package com.egenvall.namestats.network


import android.util.Log
import com.egenvall.namestats.model.*
import rx.Observable
import javax.inject.Inject


class RestDataSource @Inject constructor(private val service: SCBService) : Repository{
    override fun getDetails(name: String): Observable<NameInfo> {
        val male = getMaleDetails(name)
        val female = getFemaleDetails(name)
        return Observable.zip(male,female,{m,f ->
            NameInfo(m.data[0].values[0],f.data[0].values[0])})
    }
    private fun getMaleDetails(name: String) : Observable<ServerResponse>{
        return service.getNameInfo(request = constructRequest(name,"M"))
    }

    private fun getFemaleDetails(name: String): Observable<ServerResponse>{
        return service.getNameInfo(request = constructRequest(name,"F"))
    }

    private fun constructRequest(name: String, gender : String): Request {
        var filter : String = "vs:NamnFornamn"
        var value: String = ""
        if (gender == "M"){
            value = name+"M"
            filter +="M"
        }
        else{
            value = name+"K"
            filter +="K"
        }

        val nameSelection = Selection(filter = filter,values = listOf(value))
        val timeSelection = Selection(filter = "item", values = listOf("2016"))
        val nameQuery = Query(code = "Fornamn", selection = nameSelection)
        val timeQuery = Query(code = "Tid", selection = timeSelection)
        val response = Response()
        val request = Request(query = listOf<Query>(nameQuery,timeQuery),response = response)
        return request
    }

}

