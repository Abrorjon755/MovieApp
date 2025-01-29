package dev.abrorjon755.movieapp.ui.screen.search

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.abrorjon755.movieapp.R
import dev.abrorjon755.movieapp.model.Movie
import dev.abrorjon755.movieapp.ui.screen.home.HomeUiState
import dev.abrorjon755.movieapp.util.Debouncer

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SearchScreen(
    goToDetail: (Movie) -> Unit,
    searchMovies: (String) -> Unit,
    searchNextMovies: (String) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: State<HomeUiState>,
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val debouncer = Debouncer(300)
    Scaffold(
        topBar = { SearchScreenTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    debouncer.debounce {
                        searchMovies(it)
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                        color = MaterialTheme.colorScheme.outline,
                    )
                },
                colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search2),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (searchText != "" && uiState.value.searchMovies.isNotEmpty()) {
                SearchResult(
                    uiState = uiState,
                    searchNextMovies = searchNextMovies,
                    searchText = searchText,
                    goToDetail = goToDetail,
                    animatedVisibilityScope = animatedVisibilityScope,
                    modifier = Modifier.weight(1f)
                )
            } else if (searchText != "" && uiState.value.searchMovies.isEmpty()) {
                SearchStart()
            } else {
                NoResult()
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    )
}
