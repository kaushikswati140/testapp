package com.ticketmaster.testapp.data.localcache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey (autoGenerate = true)
    val _id : Int = 0,
    val name: String,
    val date: String,
    val city: String,
    val state: String?,
    val country: String?,
    val imageUrl: String?,

)
