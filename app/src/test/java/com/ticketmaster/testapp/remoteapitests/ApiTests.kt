package com.ticketmaster.testapp.remoteapitests

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiTests {

    @Test
    fun test_get_events_api_success() {
        val api = TestApiImpl.provideApi()
        val test = runBlocking {
            api.getEvents("chicago", "0")
        }
        assertEquals(test.page.totalPages > 0, true)
    }


    @Test
    fun test_get_events_api_invalid() {
        val api = TestApiImpl.provideApi()
        val test = runBlocking {
            api.getEvents("abc", "0")
        }
        assertEquals(test.page.totalPages == 0, true)
    }
}