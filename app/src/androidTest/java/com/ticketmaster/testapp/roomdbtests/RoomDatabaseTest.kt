package com.ticketmaster.testapp.roomdbtests

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ticketmaster.testapp.data.localcache.EventDao
import com.ticketmaster.testapp.data.localcache.EventEntity
import com.ticketmaster.testapp.data.localcache.TicketMasterDB
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {

    private lateinit var roomDatabase: TicketMasterDB
    private lateinit var roomDao: EventDao

    private val itemList = listOf(
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
        val context = ApplicationProvider.getApplicationContext<Context>()
        roomDatabase = Room.inMemoryDatabaseBuilder(context, TicketMasterDB::class.java).build()
        roomDao = roomDatabase.dao
        runBlocking {
            roomDao.insertAll(events = itemList)
        }
    }

    @After
    fun closeDb() {
        roomDatabase.close()
    }

    @Test
    fun testPagingSource() {
        runBlocking {
            val result = roomDao.pagingSource()
            val actual = result.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 10,
                    placeholdersEnabled = false
                )

            )
            assertEquals((actual as? PagingSource.LoadResult.Page)?.data, itemList)
        }
    }

    @Test
    fun testDelete() {
        runBlocking {
            roomDao.clearAll()
            val result = roomDao.pagingSource()
            val actual = result.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
            assertEquals((actual as? PagingSource.LoadResult.Page)?.data?.size, 0)
        }
    }
}