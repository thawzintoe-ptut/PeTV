package com.ptut.pmovie.core.database

import com.ptut.pmovie.core.database.util.RemoteKeys

interface RemoteKeyLocalDataSource {
    suspend fun getRemoteKeysByMovieId(movieId: Int): RemoteKeys?

    suspend fun saveRemoteKeys(remoteKeys: List<RemoteKeys>)

    suspend fun deleteAllRemoteKeys()
}
