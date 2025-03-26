package com.ticketmaster.testapp.testviewmodel

import androidx.paging.map
import com.ticketmaster.testapp.data.localcache.EventEntity
import com.ticketmaster.testapp.domain.DataRepository
import com.ticketmaster.testapp.presentation.viewmodel.EventsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.junit.Before
import org.junit.Test

class EventsViewModelTest {

    private val mockDataRepository: DataRepository = mockk<DataRepository>()

    private val viewModel = EventsViewModel(mockDataRepository)

    private val itemList = listOf(
        EventEntity(
            _id = 0,
            name = "Test Event",
            date = "March 24,2025",
            city = "testCity",
            state = "testState",
            country = "testCountry",
            imageUrl = ""
        ),
        EventEntity(
            _id = 1,
            name = "Test Event 1",
            date = "March 24,2025",
            city = "testCity1",
            state = "testState1",
            country = "testCountry1",
            imageUrl = ""
        ),
        EventEntity(
            _id = 2,
            name = "Test Event 2",
            date = "March 24,2025",
            city = "testCity2",
            state = "testState2",
            country = "testCountry2",
            imageUrl = ""
        )
    )

    @Before
    fun setup() {
        every { mockDataRepository.getEventListByCity("") } returns
                flow { itemList }
    }

    @Test
    fun testVm() {
        viewModel.getEventLists().map { pagingData ->
            pagingData.map { assert(it.equals(itemList[0])) }
        }
    }

}