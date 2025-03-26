package com.ticketmaster.testapp.remotemediatortests

import com.ticketmaster.testapp.data.remote.City
import com.ticketmaster.testapp.data.remote.Country
import com.ticketmaster.testapp.data.remote.EmbeddedResponse
import com.ticketmaster.testapp.data.remote.EmbeddedVenues
import com.ticketmaster.testapp.data.remote.EventDate
import com.ticketmaster.testapp.data.remote.EventResponse
import com.ticketmaster.testapp.data.remote.ImageData
import com.ticketmaster.testapp.data.remote.LocalEventDate
import com.ticketmaster.testapp.data.remote.Page
import com.ticketmaster.testapp.data.remote.Response
import com.ticketmaster.testapp.data.remote.State
import com.ticketmaster.testapp.data.remote.TicketMasterApi
import com.ticketmaster.testapp.data.remote.Venue

class FakeTicketMasterApi : TicketMasterApi {

    private val mockEventList = listOf(
        createMockEvent(mockString = "FakeEvent1"),
        createMockEvent(mockString = "FakeEvent2"),
        createMockEvent(mockString = "FakeEvent3"),
        createMockEvent(mockString = "FakeEvent4"),
        createMockEvent(mockString = "FakeEvent5"),
        createMockEvent(mockString = "FakeEvent6"),
        createMockEvent(mockString = "FakeEvent7"),
        createMockEvent(mockString = "FakeEvent8"),
        createMockEvent(mockString = "FakeEvent9"),
        createMockEvent(mockString = "FakeEvent10"),
        createMockEvent(mockString = "FakeEvent11"),
        createMockEvent(mockString = "FakeEvent12"),
        createMockEvent(mockString = "FakeEvent13"),
        createMockEvent(mockString = "FakeEvent14"),
        createMockEvent(mockString = "FakeEvent15"),
        createMockEvent(mockString = "FakeEvent16"),
        createMockEvent(mockString = "FakeEvent17"),
        createMockEvent(mockString = "FakeEvent18"),
        createMockEvent(mockString = "FakeEvent19"),
        createMockEvent(mockString = "FakeEvent20"),
       )

    private val mockResponse = Response(
        embeddedResponse = EmbeddedResponse(mockEventList),
        page = Page(size = 20, totalPages = 80, number = 0)
    )

    private val mockEndResponse = Response (null,
        page=Page(size = 20, totalPages = 20, number = 20))

    private val mockInvalidResponse = Response (null,
        page = Page(size = 0, totalPages = 0, number = 0))


    override suspend fun getEvents(city: String?, page: String): Response {
        return if(city.equals("FakeCity"))
            mockResponse
        else if (city.equals("FakeCityLastPage"))
            mockEndResponse
        else mockInvalidResponse
    }

    private fun createMockEvent(mockString : String)  : EventResponse {
        return  EventResponse(
            name = mockString,
            eventDate = EventDate(LocalEventDate(mockString)),
            allVenues = EmbeddedVenues(
                arrayOf(
                    Venue(
                        City("FakeCity"),
                        State(mockString, "AB"),
                        Country(mockString, "CD")
                    ),
                )
            ),
            images = arrayOf (
                ImageData(
                    ratio = "4_3",
                    imageUrl = "dummy image url",
                    width = 400,
                    height = 300,
                ),
            ),
        )
    }


}