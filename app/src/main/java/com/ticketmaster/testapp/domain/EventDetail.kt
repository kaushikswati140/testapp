package com.ticketmaster.testapp.domain

data class EventDetail(
    val name: String,
    val date: String,
    val city: String,
    val state: String?,
    val imageUrl: String?
)
