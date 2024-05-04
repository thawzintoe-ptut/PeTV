package com.ptut.pmovie.core.database.movie.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movie_categories",
    primaryKeys = ["movieId", "categoryName"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["name"],
            childColumns = ["categoryName"],
        ),
    ],
)
data class MovieCategoryEntity(
    val movieId: Int,
    val categoryName: String,
)
