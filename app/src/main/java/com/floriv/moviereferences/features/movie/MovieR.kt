package com.floriv.moviereferences.features.movie

import android.content.ContentValues
import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
class MovieR {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private var id = 0

    @ColumnInfo(name = COLUMN_WEB_ID)
    var webId: String? = null

    @ColumnInfo(name = COLUMN_TITLE)
    var title: String? = null

    @ColumnInfo(name = COLUMN_OVERVIEW)
    var overview: String? = null

    @ColumnInfo(name = COLUMN_POSTER_PATH)
    var poster: String? = null

    @ColumnInfo(name = COLUMN_RELEASE_DATE)
    var release: String? = null
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    companion object {
        const val TABLE_NAME = "movie"
        const val COLUMN_ID = BaseColumns._ID
        private const val COLUMN_WEB_ID = "web_id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_OVERVIEW = "overview"
        private const val COLUMN_POSTER_PATH = "poster_path"
        private const val COLUMN_RELEASE_DATE = "release_date"
        fun fromContentValues(values: ContentValues): MovieR {
            val movieR = MovieR()
            if (values.containsKey(COLUMN_ID)) movieR.id = values.getAsInteger(COLUMN_ID)
            if (values.containsKey(COLUMN_WEB_ID) &&
                values.containsKey(COLUMN_TITLE) &&
                values.containsKey(COLUMN_OVERVIEW) &&
                values.containsKey(COLUMN_POSTER_PATH) &&
                values.containsKey(COLUMN_RELEASE_DATE)
            ) {
                movieR.webId = values.getAsString(COLUMN_WEB_ID)
                movieR.title = values.getAsString(COLUMN_TITLE)
                movieR.overview = values.getAsString(COLUMN_OVERVIEW)
                movieR.poster = values.getAsString(COLUMN_POSTER_PATH)
                movieR.release = values.getAsString(COLUMN_RELEASE_DATE)
            }
            return movieR
        }
    }
}