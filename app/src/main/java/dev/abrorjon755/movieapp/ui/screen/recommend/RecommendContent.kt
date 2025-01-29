package dev.abrorjon755.movieapp.ui.screen.recommend

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import dev.abrorjon755.movieapp.R
import dev.abrorjon755.movieapp.model.Movie
import dev.abrorjon755.movieapp.ui.screen.composables.MyAsyncImage
import dev.abrorjon755.movieapp.ui.screen.home.HomeUiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RecommendContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: State<HomeUiState>,
    pageIndex: Int,
    goToDetail: (Movie) -> Unit,
    nextMovies: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val movies = when (pageIndex) {
        0 -> uiState.value.upcomingMovies
        1 -> uiState.value.topRatedMovies
        else -> uiState.value.popularMovies
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.height((movies.size / 3).inc() * 195.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        items(movies) {
            MyAsyncImage(
                url = it.poster_path,
                modifier = Modifier
                    .padding(6.dp, 9.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { goToDetail(it) }
                    .sharedElement(
                        state = rememberSharedContentState(it.poster_path ?: ""),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
            )
        }
        item {
            Box(
                modifier = Modifier
                    .padding(6.dp, 9.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surfaceBright,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .clickable { nextMovies() }
            ) {
                Text(
                    text = stringResource(R.string.load_more),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}