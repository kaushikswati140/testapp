package com.ticketmaster.testapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterApi {

    @GET("events.json?apikey=$API_KEY")
    suspend fun getEvents(
        @Query("city") city: String?,
        @Query("page") page: String
    ): Response

    companion object {

        const val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"

        /* keeping API_KEY here for simplicity
         For security, API KEY should be stored on server or outside project source
         directory such as in gradle properties
        */
        const val API_KEY = "DW0E98NrxUIfDDtNN7ijruVSm60ryFLX"

        fun create():TicketMasterApi {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TicketMasterApi::class.java)
        }
    }
}