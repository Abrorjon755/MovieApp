package dev.abrorjon755.movieapp.ui.screen.watchlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.abrorjon755.movieapp.R
import dev.abrorjon755.movieapp.model.Movie
import dev.abrorjon755.movieapp.ui.screen.composables.MovieItem

@Composable
fun WatchlistScreen(
    goToDetail: (Movie) -> Unit,
    movies: List<Movie>,
) {
    Scaffold(
        topBar = { WatchlistScreenTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(movies) {
                    Column {
                        MovieItem(
                            model = it,
                            modifier = Modifier.clickable { goToDetail(it) },
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.watch_list),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    )
}