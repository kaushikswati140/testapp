package com.ticketmaster.testapp

import android.content.Context
import com.ticketmaster.testapp.data.DataRepositoryImpl
import com.ticketmaster.testapp.data.localcache.TicketMasterDB
import com.ticketmaster.testapp.data.remote.TicketMasterApi
import com.ticketmaster.testapp.domain.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTicketMasterDatabase(@ApplicationContext context: Context): TicketMasterDB {
        return TicketMasterDB.create(context)
    }

    @Provides
    @Singleton
    fun provideTicketMasterApi(): TicketMasterApi {
        return TicketMasterApi.create()
    }

    @Provides
    @Singleton
    fun provideDataRepository(db: TicketMasterDB, api: TicketMasterApi): DataRepository {
        return DataRepositoryImpl(db, api)
    }
}