package dev.abrorjon755.movieapp.model

data class MoviesModel(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int,
)

data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
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
        fun toStore(model: Movie): StoredMovie = StoredMovie(
            adult = model.adult,
            backdrop_path = model.backdrop_path,
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

val emptyMovie = Movie(
    adult = false,
    backdrop_path = null,
    genre_ids = emptyList(),
    id = 0,
    original_language = "",
    original_title = "",
    overview = "",
    popularity = 0.0,
    poster_path = null,
    release_date = "",
    title = "",
    video = false,
    vote_average = 0.0,
    vote_count = 0,
)
