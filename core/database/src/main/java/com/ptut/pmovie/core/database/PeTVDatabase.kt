package com.ptut.pmovie.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.ptut.pmovie.core.database.movie.entity.CategoryEntity
import com.ptut.pmovie.core.database.movie.entity.MovieCategoryEntity
import com.ptut.pmovie.core.database.movie.entity.MovieEntity
import com.ptut.pmovie.core.database.paging.RemoteKeyEntity
import com.ptut.pmovie.core.database.paging.dao.RemoteKeysDao
import com.ptut.pmovie.core.database.util.typeconverter.GenreIdsConverter

@Database(
    entities = [
        MovieEntity::class,
        RemoteKeyEntity::class,
        CategoryEntity::class,
        MovieCategoryEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(GenreIdsConverter::class)
abstract class PeTVDatabase : RoomDatabase() {
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        private const val DATABASE_NAME = "petv.db"

        private fun migrations() = arrayOf<Migration>()

        @Volatile
        private var instance: PeTVDatabase? = null

        fun getInstance(context: Context): PeTVDatabase =
            instance ?: synchronized(this) {
                instance
                    ?: create(context).also { instance = it }
            }

        @Suppress("SpreadOperator")
        private fun create(applicationContext: Context): PeTVDatabase {
            return Room
                .databaseBuilder(
                    applicationContext,
                    PeTVDatabase::class.java,
                    DATABASE_NAME,
                )
                .addMigrations(*migrations())
                .build()
        }
    }

    suspend fun reset() {
        remoteKeysDao().deleteAll()
    }
}
