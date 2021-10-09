package com.floriv.moviereferences.features.tvshow

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "tv_show")
class TvShowR {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "web_id")
    var webId: String? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "overview")
    var overview: String? = null

    @ColumnInfo(name = "poster_path")
    var poster: String? = null

    @ColumnInfo(name = "release_date")
    var release: String? = null
}