package com.egenvall.namestats.common.di.module

import com.egenvall.namestats.network.Repository
import com.egenvall.namestats.network.RestDataSource
import com.egenvall.namestats.network.SCBService
import com.egenvall.namestats.NameStatsApp
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Creates the dependencies needed to communicate with a REST API
 */
@Module
class SCBModule(private val app : NameStatsApp) {
    val baseUrl = "http://api.scb.se/OV0104/v1/doris/sv/ssd/START/BE/BE0001/BE0001FNamn10/"


    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        return retrofit
    }

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): SCBService {
        return retrofit.create(SCBService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(service: SCBService) : Repository{
        return RestDataSource(service)
    }
}