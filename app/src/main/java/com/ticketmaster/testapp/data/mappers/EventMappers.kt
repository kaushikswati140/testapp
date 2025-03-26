package com.ticketmaster.testapp.data.mappers

import android.content.res.Resources
import com.ticketmaster.testapp.data.localcache.EventEntity
import com.ticketmaster.testapp.data.remote.EventResponse
import com.ticketmaster.testapp.data.remote.ImageData
import com.ticketmaster.testapp.domain.EventDetail

/*
* This file defines Mappings and utility methods
* */

fun EventResponse.toEventEntity(): EventEntity {

    return EventEntity(
        name = name,
        date = eventDate.startDate.localDate,
        city = allVenues.venues[0].city.name,
        state = allVenues.venues[0].state?.stateCode,
        country = allVenues.venues[0].country.countryCode,
        imageUrl = fetchImageUrl(images)
    )
}

fun EventEntity.toEventDetail(): EventDetail {
    return EventDetail(
        name = name,
        date = date,
        city = city,
        state = state ?: country,
        imageUrl = imageUrl
    )
}


fun fetchImageUrl(images: Array<ImageData>): String {

    val width = Resources.getSystem().displayMetrics.widthPixels
    val height = Resources.getSystem().displayMetrics.heightPixels

    val aspectRatio = if(width > height)
        (width/height).toFloat()
    else
        (height/width).toFloat()

    val filteredOnAspectRatio = if (aspectRatio >= 1.77) {
        "16_9"
    } else if (aspectRatio >= 1.5) {
        "3_2"
    } else {
        "4_3"
    }

    val imagesWithAspectRatio = images.filter { it.ratio == filteredOnAspectRatio }
    if (imagesWithAspectRatio.size > 1) {
        val filterOnOrientation = if (width < height) {
            "PORTRAIT"
        } else {
            "LANDSCAPE"
        }
        val imagesWithOrientation = imagesWithAspectRatio.filter {
            it.imageUrl.contains(filterOnOrientation)
        }
        return if(imagesWithOrientation.isEmpty()) {
            imagesWithAspectRatio.minBy { it.width }.imageUrl
        } else if (imagesWithOrientation.size > 1) {
            imagesWithOrientation.minBy { it.width }.imageUrl
        } else {
            imagesWithOrientation[0].imageUrl
        }
    } else {
        return imagesWithAspectRatio[0].imageUrl
    }
}
