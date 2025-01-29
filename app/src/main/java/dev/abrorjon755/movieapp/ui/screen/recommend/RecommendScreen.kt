package dev.abrorjon755.movieapp.ui.screen.recommend

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.abrorjon755.movieapp.R
import dev.abrorjon755.movieapp.model.Movie
import dev.abrorjon755.movieapp.ui.screen.composables.MyAsyncImage
import dev.abrorjon755.movieapp.ui.screen.home.HomeUiState
import kotlinx.coroutines.launch

enum class AppTabs {
    Upcoming,
    TopRated,
    Popular,
}

data class TabValue(
    val tabs: AppTabs,
    val index: Int,
    val text: String,
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RecommendScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: State<HomeUiState>,
    onTabChange: (Int) -> Unit,
    goToDetail: (Movie) -> Unit,
    goToSearch: () -> Unit,
    nextMovies: (Int) -> Unit,
    changeTheme: () -> Unit,
) {
    val tabValues = listOf(
        TabValue(
            tabs = AppTabs.Upcoming,
            index = 0,
            text = stringResource(R.string.upcoming)
        ),
        TabValue(
            tabs = AppTabs.TopRated,
            index = 1,
            text = stringResource(R.string.top_rated)
        ),
        TabValue(
            tabs = AppTabs.Popular,
            index = 2,
            text = stringResource(R.string.popular)
        )
    )
    val pageState = rememberPagerState(
        pageCount = { tabValues.size },
        initialPage = uiState.value.currentTab,
    )
    val scrollScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            RecommendScreenTopBar(
                changeTheme = { changeTheme() },
            )
        }
    ) {
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(it)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 24.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceBright,
                                    shape = RoundedCornerShape(16),
                                )
                                .clickable { goToSearch() }
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 10.dp,
                                )
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = stringResource(R.string.search),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Icon(
                                    painter = painterResource(R.drawable.search2),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyRow(
                        modifier = Modifier.height(250.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.width(24.dp))
                        }
                        items(uiState.value.nowPlayingMovies.size) {
                            Box {
                                Box(
                                    modifier = Modifier
                                        .height(250.dp)
                                        .padding(end = 24.dp),
                                ) {
                                    MyAsyncImage(
                                        url = uiState.value.nowPlayingMovies[it].poster_path,
                                        modifier = Modifier
                                            .height(210.dp)
                                            .width(145.dp)
                                            .clip(shape = RoundedCornerShape(16))
                                            .clickable { goToDetail(uiState.value.nowPlayingMovies[it]) },
                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.Bottom,
                                    modifier = Modifier.fillMaxHeight(),
                                ) {
                                    Box {
                                        Recommend3dText(
                                            text = "${it + 1}",
                                            offset = Offset(4f, 0f),
                                        )
                                        Recommend3dText(
                                            text = "${it + 1}",
                                            offset = Offset(0f, 4f),
                                        )
                                        Recommend3dText(
                                            text = "${it + 1}",
                                            offset = Offset(-4f, 0f),
                                        )
                                        Recommend3dText(
                                            text = "${it + 1}",
                                            offset = Offset(0f, -4f),
                                        )
                                        Recommend3dText(
                                            text = "${it + 1}",
                                            offset = Offset(4f, 4f),
                                        )
                                        Recommend3dText(
                                            text = "${it + 1}",
                                            offset = Offset(-4f, 4f),
                                        )
                                        Recommend3dText(
                                            text = "${it + 1}",
                                            offset = Offset(-4f, -4f),
                                        )
                                        Recommend3dText(
                                            text = "${it + 1}",
                                            offset = Offset(4f, -4f),
                                        )
                                    }
                                }
                            }
                        }
                    }
                    TabRow(
                        selectedTabIndex = uiState.value.currentTab,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .height(50.dp),
                        indicator = { tabPositions ->
                            Box(
                                Modifier
                                    .tabIndicatorOffset(tabPositions[uiState.value.currentTab])
                                    .height(3.dp)
                                    .background(MaterialTheme.colorScheme.onBackground)
                            )
                        }
                    ) {
                        for (i in tabValues)
                            Tab(
                                selected = i.index == uiState.value.currentTab,
                                onClick = {
                                    onTabChange(i.index)
                                    scrollScope.launch {
                                        pageState.animateScrollToPage(
                                            i.index,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioNoBouncy,
                                                stiffness = Spring.StiffnessLow,
                                            ),
                                        )
                                    }
                                },
                                content = {
                                    Text(
                                        text = i.text,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                },
                                modifier = Modifier.height(50.dp)
                            )
                    }
                    HorizontalPager(
                        state = pageState,
                        modifier = Modifier
                            .padding(18.dp, 12.dp),
                        userScrollEnabled = false,
                    ) { page ->
                        RecommendContent(
                            uiState = uiState,
                            pageIndex = page,
                            nextMovies = { nextMovies(page) },
                            goToDetail = goToDetail,
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    }
                }
            }
            item {
                Spacer(
                    modifier = Modifier.height(70.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendScreenTopBar(
    changeTheme: () -> Unit,
) {
    TopAppBar(
        actions = {
            IconButton(
                onClick = { changeTheme() },
            ) {
                Icon(Icons.Rounded.Build, contentDescription = null)
            }
            IconButton(
                onClick = { },
            ) {
                Icon(Icons.Rounded.Call, contentDescription = null)
            }
        },
        title = {
            Text(
                text = stringResource(R.string.what_do_you_want_to_watch),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }
    )
}

@Composable
fun Recommend3dText(
    text: String,
    offset: Offset,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge.copy(
            shadow = Shadow(
                color = MaterialTheme.colorScheme.primary,
                offset = offset,
            )
        ),
        fontSize = 96.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.background,
    )
}