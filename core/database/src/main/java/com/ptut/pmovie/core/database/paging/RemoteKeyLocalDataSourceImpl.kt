package com.ptut.pmovie.core.database.paging

import androidx.room.withTransaction
import com.ptut.pmovie.core.database.PeTVDatabase
import com.ptut.pmovie.core.database.RemoteKeyLocalDataSource
import com.ptut.pmovie.core.database.util.RemoteKeys
import com.ptut.pmovie.core.database.util.toEntity
import com.ptut.pmovie.core.database.util.toRemoteKeys
import javax.inject.Inject

class RemoteKeyLocalDataSourceImpl
    @Inject
    constructor(
        private val db: PeTVDatabase,
    ) : RemoteKeyLocalDataSource {
        override suspend fun getRemoteKeysByMovieId(movieId: Int): RemoteKeys? {
            return db.remoteKeysDao().remoteKeysRepoId(movieId)?.toRemoteKeys()
        }

        override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKeys>) {
            db.remoteKeysDao().insertAll(remoteKeys.map(RemoteKeys::toEntity))
        }

        override suspend fun deleteAllRemoteKeys() {
            db.withTransaction {
                db.remoteKeysDao().deleteAll()
            }
        }
    }
