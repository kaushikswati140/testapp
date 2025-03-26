package com.ticketmaster.testapp.data.localcache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKeys)

    @Query("SELECT * FROM remote_keys WHERE city = :city")
    suspend fun remoteKeyByCity(city: String): RemoteKeys

    @Query("DELETE FROM remote_keys WHERE city = :city")
    suspend fun deleteByCity(city: String)
}