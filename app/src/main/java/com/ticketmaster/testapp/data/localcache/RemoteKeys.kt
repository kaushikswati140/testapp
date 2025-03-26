package com.ticketmaster.testapp.data.localcache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Since remote keys are not directly associated with list items,
* storing them in a separate table in database
* */

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    var city: String,
    val nextPageKey: Int
)