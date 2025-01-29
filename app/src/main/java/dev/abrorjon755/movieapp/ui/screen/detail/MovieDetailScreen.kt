package dev.abrorjon755.movieapp.ui.screen.detail

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.abrorjon755.movieapp.R
import dev.abrorjon755.movieapp.model.Movie
import dev.abrorjon755.movieapp.ui.screen.composables.MovieItemText
import dev.abrorjon755.movieapp.ui.screen.composables.MyAsyncImage

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieDetailScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    popBack: () -> Unit,
    model: Movie,
    isSaved: (Movie) -> Boolean,
    saveMovie: (Movie) -> Unit,
    deleteMovie: (Int) -> Unit,
) {
    var isSaved by remember { mutableStateOf(isSaved(model)) }
    var currentTab by remember { mutableIntStateOf(0) }
    BackHandler { popBack() }
    Scaffold(
        topBar = {
            MovieDetailScreenTopBar(
                popBack = popBack,
                save = {
                    if (isSaved) {
                        deleteMovie(model.id)
                        isSaved = false
                    } else {
                        saveMovie(model)
                        isSaved = true
                    }
                },
                isSaved = isSaved,
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
            ) {
                MyAsyncImage(
                    url = model.backdrop_path,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 28.dp)
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            Row(
                verticalAlignment = Alignment.Top,
            ) {
                MyAsyncImage(
                    url = model.poster_path,
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .sharedElement(
                            state = rememberSharedContentState(model.poster_path ?: ""),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(8.dp),
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        MovieItemText(
                            icon = painterResource(R.drawable.star),
                            text = String.format("%.1f", model.vote_average),
                        )
                    }
                    Spacer(modifier = Modifier.height(36.dp))
                    Text(
                        text = model.title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                MovieItemText(
                    icon = painterResource(R.drawable.calendarblank),
                    text = model.release_date,
                )
                Text(
                    text = "|",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                MovieItemText(
                    icon = painterResource(R.drawable.clock),
                    text = if (model.adult) "18+" else "16+",
                )
                Text(
                    text = "|",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                MovieItemText(
                    icon = painterResource(R.drawable.ticket),
                    text = "Action",
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            TabRow(
                selectedTabIndex = currentTab,
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.height(40.dp),
                indicator = { tabPositions ->
                    Box(
                        Modifier
                            .tabIndicatorOffset(tabPositions[currentTab])
                            .height(3.dp)
                            .background(MaterialTheme.colorScheme.secondary),
                    )
                }
            ) {
                Tab(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 },
                    modifier = Modifier.height(40.dp),
                ) {
                    Text(
                        stringResource(R.string.about_movie),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                Tab(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 },
                    modifier = Modifier.height(40.dp),
                ) {
                    Text(
                        stringResource(R.string.reviews),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                Tab(
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 },
                    modifier = Modifier.height(40.dp),
                ) {
                    Text(
                        stringResource(R.string.cast),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreenTopBar(
    popBack: () -> Unit,
    save: () -> Unit,
    isSaved: Boolean,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.detail),
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { popBack() },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = null,
                    )
                }
            }
        },
        actions = {
            Row {
                IconButton(
                    onClick = { save() },
                ) {
                    Icon(
                        painter =
                        if (isSaved)
                            painterResource(R.drawable.top_bar_right)
                        else painterResource(
                            R.drawable.save
                        ),
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    )
}