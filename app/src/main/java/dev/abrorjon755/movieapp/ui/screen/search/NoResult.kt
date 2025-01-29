package dev.abrorjon755.movieapp.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.abrorjon755.movieapp.R

@Composable
fun NoResult() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.folder__1__1),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.there_is_no_movie_yet),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.find_your_movie_by_type_title_categories_years_etc),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}