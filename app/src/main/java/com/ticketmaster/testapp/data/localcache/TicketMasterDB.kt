package com.ticketmaster.testapp.data.localcache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [EventEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class TicketMasterDB : RoomDatabase() {

    abstract val dao: EventDao
    abstract val remoteDao: RemoteKeysDao

    companion object {
        fun create(context: Context): TicketMasterDB {
            return Room.databaseBuilder(
                context,
                TicketMasterDB::class.java, "events.db"
            ).build()
        }
    }
}