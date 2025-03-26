package com.ticketmaster.testapp.remoteapitests

import com.ticketmaster.testapp.data.remote.TicketMasterApi.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TestApiImpl {

    fun provideApi(): TestApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TestApi::class.java)

}