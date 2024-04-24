package com.ptut.pmovie.core.database.di

import android.content.Context
import com.ptut.pmovie.core.database.PeTVDatabase
import com.ptut.pmovie.core.database.paging.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {
    @Provides
    @Singleton
    fun providePeTVRoomDatabase(
        @ApplicationContext appContext: Context,
    ): PeTVDatabase {
        return PeTVDatabase.getInstance(appContext)
    }

    @Provides
    fun provideRemoteKeysDao(db: PeTVDatabase): RemoteKeysDao {
        return db.remoteKeysDao()
    }
}
