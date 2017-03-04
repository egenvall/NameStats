package com.egenvall.namestats.network


import com.egenvall.namestats.model.*
import rx.Observable
import javax.inject.Inject


class RestDataSource @Inject constructor(private val service: SCBService) : Repository{
    /**
     * Makes two calls to the API, and zips the result of each response into one [NameInfo] object
     * When the server does not find any person with a name for that gender, it returns a 404.
     * So when none is found, [onErrorResumeNext] is applied to return 0 for that name.
     */
    override fun getDetails(name: String): Observable<NameInfo> {
        val male = getMaleDetails(name).onErrorResumeNext { Observable.just(ServerResponse(listOf(PersonData(listOf("0")))))}
        val female = getFemaleDetails(name).onErrorResumeNext { Observable.just(ServerResponse(listOf(PersonData(listOf("0")))))}
        return Observable.zip(male,female,{m,f ->
            NameInfo(m.data[0].values[0],f.data[0].values[0])})

    }

    private fun getMaleDetails(name: String) : Observable<ServerResponse>{
        return service.getNameInfo(request = constructRequest(name,"M"))
    }

    private fun getFemaleDetails(name: String): Observable<ServerResponse>{
        return service.getNameInfo(request = constructRequest(name,"F"))
    }

    /**
     * Constructs a valid [Request] with the searchterm [name] and [gender]
     */
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

