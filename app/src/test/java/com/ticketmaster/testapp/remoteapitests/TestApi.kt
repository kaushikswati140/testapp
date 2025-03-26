package com.ticketmaster.testapp.remoteapitests

import com.ticketmaster.testapp.data.remote.Response
import com.ticketmaster.testapp.data.remote.TicketMasterApi.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface TestApi {

    @GET("events.json?apikey=$API_KEY")
    suspend fun getEvents(
        @Query("city") city: String?,
        @Query("page") page: String
    ): Response

}