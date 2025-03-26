package com.ticketmaster.testapp.data.localcache

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun clearAll()

    @Query("SELECT * FROM events")
    fun pagingSource(): PagingSource<Int, EventEntity>
}