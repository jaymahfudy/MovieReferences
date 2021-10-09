package com.floriv.moviereferences.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.floriv.moviereferences.features.movie.MovieR
import com.floriv.moviereferences.features.tvshow.TvShowR

@Database(entities = [MovieR::class, TvShowR::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "favorite"
                        ).allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}