package dev.abrorjon755.movieapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.abrorjon755.movieapp.MovieApplication
import dev.abrorjon755.movieapp.data.MovieDao
import dev.abrorjon755.movieapp.data.MovieRepository
import dev.abrorjon755.movieapp.model.Movie
import dev.abrorjon755.movieapp.model.StoredMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val movieDao: MovieDao,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    var uiState: StateFlow<HomeUiState> = _uiState
        private set

    init {
        getNowPlayingMovies(page = uiState.value.nowPlayingPage)
        getUpcomingMovies(page = uiState.value.upcomingPage)
        getTopRatedMovies(page = uiState.value.topRatedPage)
        getPopularMovies(page = uiState.value.popularPage)
    }

    fun changeScreen(newScreen: AppScreens) {
        _uiState.update { it.copy(currentPage = newScreen) }
    }

    fun changeTab(newTabIndex: Int) {
        _uiState.update { it.copy(currentTab = newTabIndex) }
    }

    fun selectMovie(newMovie: Movie) {
        _uiState.update { it.copy(selectedMovie = newMovie, isAtHome = false) }
    }

    fun atHome() {
        _uiState.update { it.copy(isAtHome = true) }
    }

    private fun getNowPlayingMovies(page: Int) {
        _uiState.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val response = movieRepository.getNowPlayingMovies(page = page)
                _uiState.update {
                    it.copy(
                        nowPlayingMovies = response.results,
                        status = Status.Success,
                    )
                }
            } catch (_: IOException) {
                _uiState.update { it.copy(status = Status.Error) }
            }
        }
    }

    private fun getUpcomingMovies(page: Int) {
        _uiState.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val response = movieRepository.getUpcomingMovies(page = page)
                _uiState.update {
                    it.copy(
                        upcomingMovies = it.upcomingMovies + response.results,
                        status = Status.Success,
                    )
                }
            } catch (_: IOException) {
                _uiState.update { it.copy(status = Status.Error) }
            }
        }
    }

    private fun getTopRatedMovies(page: Int) {
        _uiState.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val response = movieRepository.getTopRatedMovies(page = page)
                _uiState.update {
                    it.copy(
                        topRatedMovies = it.topRatedMovies + response.results,
                        status = Status.Success,
                    )
                }
            } catch (_: IOException) {
                _uiState.update { it.copy(status = Status.Error) }
            }
        }
    }

    private fun getPopularMovies(page: Int) {
        _uiState.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val response = movieRepository.getPopularMovies(page = page)
                _uiState.update {
                    it.copy(
                        popularMovies = it.popularMovies + response.results,
                        status = Status.Success,
                    )
                }
            } catch (_: IOException) {
                _uiState.update { it.copy(status = Status.Error) }
            }
        }
    }

    fun getSearchMovies(query: String) {
        _uiState.update { it.copy(status = Status.Loading, searchPage = 1) }
        viewModelScope.launch {
            try {
                val response =
                    movieRepository.getSearchMovies(query = query, page = uiState.value.searchPage)
                _uiState.update {
                    it.copy(
                        searchMovies = response.results,
                        status = Status.Success,
                    )
                }
            } catch (_: IOException) {
                _uiState.update { it.copy(status = Status.Error) }
            }
        }
    }

    fun getUpComingMoviesNext() {
        _uiState.update { it.copy(status = Status.Loading, upcomingPage = it.upcomingPage + 1) }
        try {
            getUpcomingMovies(page = uiState.value.upcomingPage + 1)
        } catch (e: IOException) {
            _uiState.update { it.copy(status = Status.Error) }
        }
    }

    fun getTopRatedMoviesNext() {
        _uiState.update { it.copy(status = Status.Loading, topRatedPage = it.topRatedPage + 1) }
        try {
            getTopRatedMovies(page = uiState.value.topRatedPage + 1)
        } catch (e: IOException) {
            _uiState.update { it.copy(status = Status.Error) }
        }
    }

    fun getPopularMoviesNext() {
        _uiState.update { it.copy(status = Status.Loading, popularPage = it.popularPage + 1) }
        try {
            getPopularMovies(page = uiState.value.popularPage + 1)
        } catch (e: IOException) {
            _uiState.update { it.copy(status = Status.Error) }
        }
    }

    fun getSearchMoviesNext(query: String) {
        _uiState.update { it.copy(status = Status.Loading, searchPage = it.searchPage + 1) }
        try {
            viewModelScope.launch {
                val response =
                    movieRepository.getSearchMovies(query = query, page = uiState.value.searchPage)
                _uiState.update {
                    it.copy(
                        searchMovies = it.searchMovies + response.results,
                        status = Status.Success,
                    )
                }
            }
        } catch (e: IOException) {
            _uiState.update { it.copy(status = Status.Error) }
        }
    }

    fun getAllMovies(): Flow<List<StoredMovie>> = movieDao.getAllMovies()

    fun getAllMoviesToUiState(list: List<Movie>) {
        _uiState.update {
            it.copy(
                storedMovies = list,
            )
        }
    }

    fun isSaved(movie: Movie): Boolean {
        for (i in uiState.value.storedMovies) {
            if (i.id == movie.id) return true
        }
        return false
    }

    fun saveMovie(movie: Movie) {
        val storedMovie = Movie.toStore(movie)
        viewModelScope.launch {
            movieDao.insertAll(storedMovie)
        }
    }

    fun deleteMovie(movie: Int) {
        viewModelScope.launch {
            movieDao.deleteMovie(movie)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieRepository
                HomeViewModel(
                    movieRepository = movieRepository,
                    movieDao = application.database.movieDao(),
                )
            }
        }
    }
}