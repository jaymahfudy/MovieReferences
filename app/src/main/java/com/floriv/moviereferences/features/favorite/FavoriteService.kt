package com.floriv.moviereferences.features.favorite

import android.app.Application
import android.database.Cursor
import com.floriv.moviereferences.core.room.AppDao
import com.floriv.moviereferences.core.room.AppDatabase
import com.floriv.moviereferences.features.movie.MovieR
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteService
@Inject constructor(application: Application) : AppDao {

    private val appDao by lazy {
        val db = AppDatabase.getDatabase(application)
        db.appDao()
    }

    private val executorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    override val count: Int
        get() = appDao.count

    override val all: List<MovieR>
        get() = appDao.all

    override val allByCursor: Cursor?
        get() = appDao.allByCursor

    override fun findById(id: String?): MovieR {
        return appDao.findById(id)
    }

    override fun findIdByCursor(id: Long): Cursor? {
        return appDao.findIdByCursor(id)
    }

    override fun insertMovie(movie: MovieR?): Long {
        return appDao.insertMovie(movie)
    }

    override fun deleteMovie(movie: MovieR?): Int {
        return appDao.deleteMovie(movie)
    }

    override fun deleteById(id: Long): Int {
        return appDao.deleteById(id)
    }

    override fun update(movieR: MovieR?): Int {
        return appDao.update(movieR)
    }

}