package dev.abrorjon755.movieapp.data

import dev.abrorjon755.movieapp.model.MoviesModel
import dev.abrorjon755.movieapp.network.MovieApiService

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int = 1): MoviesModel
    suspend fun getPopularMovies(page: Int = 1): MoviesModel
    suspend fun getTopRatedMovies(page: Int = 1): MoviesModel
    suspend fun getUpcomingMovies(page: Int = 1): MoviesModel
    suspend fun getSearchMovies(query: String, page: Int = 1): MoviesModel
}

class MovieRepositoryImpl(private val movieApiService: MovieApiService) : MovieRepository {
    override suspend fun getNowPlayingMovies(page: Int): MoviesModel =
        movieApiService.getNowPlayingMovies(page = page)

    override suspend fun getPopularMovies(page: Int): MoviesModel =
        movieApiService.getPopularMovies(page = page)

    override suspend fun getTopRatedMovies(page: Int): MoviesModel =
        movieApiService.getTopRatedMovies(page = page)

    override suspend fun getUpcomingMovies(page: Int): MoviesModel =
        movieApiService.getUpcomingMovies(page = page)

    override suspend fun getSearchMovies(query: String, page: Int): MoviesModel =
        movieApiService.getSearchMovies(query = query, page = page)
}