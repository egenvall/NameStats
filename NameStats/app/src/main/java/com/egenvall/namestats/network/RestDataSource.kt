package com.egenvall.namestats.network


import rx.Observable
import javax.inject.Inject


class RestDataSource @Inject constructor(private val service: SCBService) : Repository{

}

