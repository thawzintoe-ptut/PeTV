package com.ptut.pmovie.core.database.paging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
)
