package dev.abrorjon755.movieapp.ui.screen.search

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.abrorjon755.movieapp.R
import dev.abrorjon755.movieapp.model.Movie
import dev.abrorjon755.movieapp.ui.screen.composables.MovieItem
import dev.abrorjon755.movieapp.ui.screen.home.HomeUiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SearchResult(
    uiState: State<HomeUiState>,
    searchNextMovies: (String) -> Unit,
    searchText: String,
    goToDetail: (Movie) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(uiState.value.searchMovies) {
            Column {
                MovieItem(
                    model = it,
                    modifier = Modifier.clickable { goToDetail(it) },
                    imageModifier = Modifier.sharedElement(
                        state = rememberSharedContentState(it.poster_path ?: ""),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable { searchNextMovies(searchText) },
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(R.string.load_more),
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}