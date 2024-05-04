package com.ptut.pmovie.core.database.util

import com.ptut.pmovie.core.database.paging.RemoteKeyEntity

data class RemoteKeys(
    val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
)

fun RemoteKeyEntity.toRemoteKeys(): RemoteKeys {
    return RemoteKeys(
        repoId = this.repoId,
        prevKey = this.prevKey,
        nextKey = this.nextKey,
    )
}

fun RemoteKeys.toEntity(): RemoteKeyEntity {
    return RemoteKeyEntity(
        repoId = this.repoId,
        prevKey = this.prevKey,
        nextKey = this.nextKey,
    )
}
