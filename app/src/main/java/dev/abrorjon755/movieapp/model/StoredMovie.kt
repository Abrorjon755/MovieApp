package dev.abrorjon755.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class StoredMovie(
    val adult: Boolean,
    val backdrop_path: String?,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
) {
    companion object {
        fun toMovie(model: StoredMovie) = Movie(
            adult = model.adult,
            backdrop_path = model.backdrop_path,
            genre_ids = emptyList(),
            id = model.id,
            original_language = model.original_language,
            original_title = model.original_title,
            overview = model.overview,
            popularity = model.popularity,
            poster_path = model.poster_path,
            release_date = model.release_date,
            title = model.title,
            video = model.video,
            vote_average = model.vote_average,
            vote_count = model.vote_count,
        )
    }
}
