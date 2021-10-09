package com.floriv.moviereferences.core.room

import android.database.Cursor
import androidx.room.*
import com.floriv.moviereferences.features.movie.MovieR

@Dao
interface AppDao {
    @get:Query("SELECT COUNT(*) FROM " + MovieR.TABLE_NAME)
    val count: Int

    @get:Query("SELECT *  FROM movie")
    val all: List<MovieR>

    @get:Query("SELECT *  FROM movie")
    val allByCursor: Cursor?

    @Query("SELECT * FROM movie WHERE web_id = :id")
    fun findById(id: String?): MovieR

    @Query("SELECT * FROM movie WHERE web_id = :id")
    fun findIdByCursor(id: Long): Cursor?

    @Insert
    fun insertMovie(movie: MovieR?): Long

    @Delete
    fun deleteMovie(movie: MovieR?): Int

    @Query("DELETE FROM " + MovieR.TABLE_NAME + " WHERE " + MovieR.COLUMN_ID + " = :id")
    fun deleteById(id: Long): Int

    @Update
    fun update(movieR: MovieR?): Int
}