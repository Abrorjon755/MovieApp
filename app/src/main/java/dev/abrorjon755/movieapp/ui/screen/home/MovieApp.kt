package dev.abrorjon755.movieapp.ui.screen.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.abrorjon755.movieapp.R
import dev.abrorjon755.movieapp.model.StoredMovie
import dev.abrorjon755.movieapp.ui.screen.detail.MovieDetailScreen
import dev.abrorjon755.movieapp.ui.screen.recommend.RecommendScreen
import dev.abrorjon755.movieapp.ui.screen.search.SearchScreen
import dev.abrorjon755.movieapp.ui.screen.watchlist.WatchlistScreen
import kotlinx.coroutines.launch

enum class AppScreens {
    Recommend,
    Search,
    Watchlist,
    MovieDetail,
}

data class BottomItems(
    val screen: AppScreens,
    val icon: Painter,
    val text: String
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieApp(
    changeTheme: () -> Unit,
) {
    val bottomItems = listOf(
        BottomItems(
            screen = AppScreens.Recommend,
            icon = painterResource(R.drawable.home),
            text = stringResource(id = R.string.home)
        ),
        BottomItems(
            screen = AppScreens.Search,
            icon = painterResource(R.drawable.search),
            text = stringResource(id = R.string.search)
        ),
        BottomItems(
            screen = AppScreens.Watchlist,
            icon = painterResource(R.drawable.save),
            text = stringResource(id = R.string.watchlist)
        ),
    )
    val viewModel = viewModel<HomeViewModel>(factory = HomeViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val storedMovies by viewModel.getAllMovies().collectAsState(emptyList())
    viewModel.getAllMoviesToUiState(storedMovies.map { StoredMovie.toMovie(it) })
    SharedTransitionLayout(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            NavHost(
                navController = navController,
                startDestination = AppScreens.Recommend.name,
            ) {
                composable(AppScreens.Recommend.name) {
                    RecommendScreen(
                        uiState = uiState,
                        onTabChange = { newIndex -> viewModel.changeTab(newIndex) },
                        goToDetail = { movie ->
                            viewModel.selectMovie(movie)
                            navController.navigate(AppScreens.MovieDetail.name)
                        },
                        goToSearch = {
                            viewModel.changeScreen(AppScreens.Search)
                            navController.navigate(AppScreens.Search.name) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        animatedVisibilityScope = this,
                        nextMovies = { page ->
                            when (page) {
                                0 -> viewModel.getUpComingMoviesNext()
                                1 -> viewModel.getTopRatedMoviesNext()
                                else -> viewModel.getPopularMoviesNext()
                            }
                        },
                        changeTheme = { changeTheme() },
                    )
                }
                composable(AppScreens.Search.name) {
                    SearchScreen(
                        goToDetail = { movie ->
                            viewModel.selectMovie(movie)
                            navController.navigate(AppScreens.MovieDetail.name)
                        },
                        searchMovies = { query ->
                            viewModel.getSearchMovies(query = query)
                        },
                        searchNextMovies = { query ->
                            viewModel.getSearchMoviesNext(query = query)
                        },
                        uiState = uiState,
                        animatedVisibilityScope = this,
                    )
                }
                composable(AppScreens.Watchlist.name) {
                    WatchlistScreen(
                        goToDetail = { movie ->
                            viewModel.selectMovie(movie)
                            navController.navigate(AppScreens.MovieDetail.name)
                        },
                        movies = uiState.value.storedMovies,
                    )
                }
                composable(AppScreens.MovieDetail.name) {
                    MovieDetailScreen(
                        popBack = {
                            coroutineScope.launch {
                                navController.popBackStack()
                            }
                            viewModel.atHome()
                        },
                        model = uiState.value.selectedMovie,
                        animatedVisibilityScope = this,
                        isSaved = { movie -> viewModel.isSaved(movie) },
                        saveMovie = { movie -> viewModel.saveMovie(movie) },
                        deleteMovie = { movie -> viewModel.deleteMovie(movie) },
                    )
                }
            }
        }
    }
    if (uiState.value.isAtHome) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            MovieAppBottomNavigationBar(
                currentScreen = uiState.value.currentPage,
                bottomItems = bottomItems,
                viewModel = viewModel,
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun MovieAppBottomNavigationBar(
    currentScreen: AppScreens,
    bottomItems: List<BottomItems>,
    viewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        )
        NavigationBar(
            modifier = modifier.background(
                color = MaterialTheme.colorScheme.background,
            ),
            tonalElevation = 1.dp,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.inversePrimary,
        ) {
            for (i in bottomItems)
                NavigationBarItem(
                    colors = NavigationBarItemColors(
                        selectedIndicatorColor = Color.Transparent,
                        unselectedIconColor = Color.Transparent,
                        unselectedTextColor = Color.Transparent,
                        disabledIconColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                        selectedIconColor = Color.Transparent,
                        selectedTextColor = Color.Transparent,
                    ),
                    selected = i.screen == currentScreen,
                    onClick = {
                        viewModel.changeScreen(i.screen)
                        navController.navigate(i.screen.name) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            i.icon,
                            contentDescription = null,
                            tint = if (i.screen == currentScreen)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.inversePrimary
                        )
                    },
                    label = {
                        Text(
                            text = i.text,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (i.screen == currentScreen)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.inversePrimary
                        )
                    },
                    alwaysShowLabel = true,
                )
        }
    }
}

@Preview
@Composable
fun MovieAppPreview() {
    MovieApp(
        changeTheme = {},
    )
}