package com.ticketmaster.testapp.remotemediatortests

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ticketmaster.testapp.data.TicketMasterRemoteMediator
import com.ticketmaster.testapp.data.localcache.EventEntity
import com.ticketmaster.testapp.data.localcache.TicketMasterDB
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class TicketMasterRemoteMediatorTest {

    private val mockApi = FakeTicketMasterApi()

    private val mockDB = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        TicketMasterDB::class.java
    ).build()


    @After
    fun tearDown() {
        mockDB.clearAllTables()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = TicketMasterRemoteMediator(
            mockDB,
            mockApi,
            "FakeCity"
        )

        val pagingState = PagingState<Int, EventEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue( result is RemoteMediator.MediatorResult.Success )
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached )
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        val remoteMediator = TicketMasterRemoteMediator(
            mockDB,
            mockApi,
            "FakeCityLastPage"
        )

        val pagingState = PagingState<Int, EventEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue( result is RemoteMediator.MediatorResult.Success )
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached )
    }



    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {

        val remoteMediator = TicketMasterRemoteMediator(
            mockDB,
            mockApi,
            "abc"
        )
        val pagingState = PagingState<Int, EventEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}