package dev.abrorjon755.movieapp.ui.screen.home

import dev.abrorjon755.movieapp.model.Movie
import dev.abrorjon755.movieapp.model.emptyMovie

enum class Status {
    Initial,
    Loading,
    Success,
    Error,
}

data class HomeUiState(
    val status: Status = Status.Initial,
    val currentPage: AppScreens = AppScreens.Recommend,
    val currentTab: Int = 0,
    val nowPlayingMovies: List<Movie> = emptyList(),
    val nowPlayingPage: Int = 1,
    val upcomingMovies: List<Movie> = emptyList(),
    val upcomingPage: Int = 1,
    val topRatedMovies: List<Movie> = emptyList(),
    val topRatedPage: Int = 1,
    val popularMovies: List<Movie> = emptyList(),
    val popularPage: Int = 1,
    val selectedMovie: Movie = emptyMovie,
    val isAtHome: Boolean = true,
    val searchMovies: List<Movie> = emptyList(),
    val searchPage: Int = 1,
    val storedMovies: List<Movie> = emptyList(),
)
