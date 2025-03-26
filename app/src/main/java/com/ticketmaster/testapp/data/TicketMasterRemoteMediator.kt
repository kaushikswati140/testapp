package com.ticketmaster.testapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ticketmaster.testapp.data.localcache.EventDao
import com.ticketmaster.testapp.data.localcache.EventEntity
import com.ticketmaster.testapp.data.localcache.RemoteKeys
import com.ticketmaster.testapp.data.localcache.RemoteKeysDao
import com.ticketmaster.testapp.data.localcache.TicketMasterDB
import com.ticketmaster.testapp.data.mappers.toEventEntity
import com.ticketmaster.testapp.data.remote.TicketMasterApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class TicketMasterRemoteMediator(
    private val db: TicketMasterDB,
    private val api: TicketMasterApi,
    private val query: String
) : RemoteMediator<Int, EventEntity>() {

    private val eventDao: EventDao = db.dao
    private val remoteKeyDao: RemoteKeysDao = db.remoteDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {

        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.remoteKeyByCity(query)
                    }
                    remoteKey.nextPageKey
                }
            }

            val response = api.getEvents(query, loadKey.toString())
            val totalResponsePageCount = response.page.totalPages

            if (totalResponsePageCount == 0) {
                return MediatorResult.Error(Throwable(message = "City $query not found!\n Please enter a valid city name"))
            }
            val nextPage = response.page.number + 1
            //as per server response Max paging depth exceeded. (page * size) must be less than 1,000
            //limiting the next page key
            if (nextPage > response.page.totalPages || nextPage >= 1000 / response.page.size) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            val events =
                api.getEvents(query, loadKey.toString()).embeddedResponse?.eventResponseList
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            val items = events.map {
                it.toEventEntity()
            }
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    eventDao.clearAll()
                    remoteKeyDao.deleteByCity(query)
                }
                remoteKeyDao.insert(RemoteKeys(query, nextPage))
                eventDao.insertAll(items)
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}