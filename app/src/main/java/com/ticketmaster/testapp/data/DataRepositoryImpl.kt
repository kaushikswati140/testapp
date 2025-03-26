package com.ticketmaster.testapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ticketmaster.testapp.data.localcache.TicketMasterDB
import com.ticketmaster.testapp.data.remote.TicketMasterApi
import com.ticketmaster.testapp.domain.DataRepository
import javax.inject.Inject

/*
* Implementation of DataRepository interface that uses a database backed [androidx.paging.PagingSource] and
* [androidx.paging.RemoteMediator] to load pages from network when there are no more items cached
* in the database to load.
* */

class DataRepositoryImpl  @Inject constructor(
    private val db: TicketMasterDB,
    private val api: TicketMasterApi) : DataRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getEventListByCity(city:String) = Pager(
        config = PagingConfig(PAGE_SIZE),
        remoteMediator = TicketMasterRemoteMediator(db,api,city)) {
        db.dao.pagingSource()
        }.flow

    companion object{
        const val PAGE_SIZE = 20
    }
}