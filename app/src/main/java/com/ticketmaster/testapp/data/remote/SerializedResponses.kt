package com.ticketmaster.testapp.data.remote

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("_embedded")
    val embeddedResponse : EmbeddedResponse?,

    @SerializedName("page")
    val page: Page
)

data class EmbeddedResponse(
    @SerializedName("events")
    val eventResponseList: List<EventResponse>
)

data class EventResponse(

    @SerializedName("name")
    val name: String,

    @SerializedName("dates")
    val eventDate: EventDate,

    @SerializedName("_embedded")
    val allVenues: EmbeddedVenues,

    @SerializedName("images")
    val images : Array<ImageData>,

    )


data class EmbeddedVenues (
    @SerializedName("venues")
    val venues : Array<Venue>
)

data class Venue (

    @SerializedName("city")
    val city :City,

    @SerializedName("state")
    val state:State?,

    @SerializedName("country")
    val country:Country
)

data class City (
    @SerializedName("name")
    val name : String
)

data class State(
    @SerializedName("name")
    val name: String?,
    @SerializedName("stateCode")
    val stateCode: String?
)

data class Country(
    @SerializedName("name")
    val name: String?,
    @SerializedName("countryCode")
    val countryCode: String?
)

data class ImageData(
    @SerializedName("ratio")
    val ratio : String,
    @SerializedName("url")
    val imageUrl: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
)
data class EventDate(
    @SerializedName("start")
    val startDate : LocalEventDate
)
data class LocalEventDate(
    @SerializedName("localDate")
    val localDate: String
)

data class Page (
    @SerializedName("size")
    val size: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("number")
    val number: Int
)
