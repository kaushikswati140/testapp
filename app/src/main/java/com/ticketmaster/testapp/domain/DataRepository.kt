package com.ticketmaster.testapp.domain

import androidx.paging.PagingData
import com.ticketmaster.testapp.data.localcache.EventEntity
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    fun getEventListByCity( city : String) : Flow<PagingData<EventEntity>>
}