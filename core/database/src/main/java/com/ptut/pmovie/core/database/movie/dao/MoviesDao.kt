package com.ptut.pmovie.core.database.movie.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ptut.pmovie.core.database.movie.entity.CategoryEntity
import com.ptut.pmovie.core.database.movie.entity.MovieCategoryEntity
import com.ptut.pmovie.core.database.movie.entity.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovie(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCategory(movieCategory: MovieCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovieCategory(movieCategories: List<MovieCategoryEntity>)

    @Query("SELECT * FROM movies WHERE id IN (SELECT movieId FROM movie_categories WHERE categoryName = :category)")
    fun getMoviesByCategory(category: String): PagingSource<Int, MovieEntity>

    @Transaction
    suspend fun deleteMovieAndCategories(movieId: Int) {
        deleteMovieCategoriesByMovieId(movieId)
        deleteMovieById(movieId)
    }

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

    @Query("DELETE FROM movie_categories WHERE movieId = :movieId")
    suspend fun deleteMovieCategoriesByMovieId(movieId: Int)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM movie_categories")
    suspend fun deleteAllMovieCategories()

    @Transaction
    suspend fun deleteAllData() {
        deleteAllMovieCategories()
        deleteAllMovies()
    }
}
