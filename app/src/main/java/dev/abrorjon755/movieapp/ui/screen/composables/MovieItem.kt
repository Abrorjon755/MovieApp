package dev.abrorjon755.movieapp.ui.screen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.abrorjon755.movieapp.R
import dev.abrorjon755.movieapp.model.Movie


@Composable
fun MovieItem(
    model: Movie,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(150.dp)
    ) {
        MyAsyncImage(
            url = model.poster_path,
            modifier = imageModifier
                .fillMaxHeight()
                .width(100.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(2f)
        ) {
            Text(
                text = model.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1f))
            MovieItemText(
                icon = painterResource(R.drawable.star),
                text = model.vote_average.toString(),
                isYellow = true,
            )
            Spacer(modifier = Modifier.height(6.dp))
            MovieItemText(
                icon = painterResource(R.drawable.ticket),
                text = "Action"
            )
            Spacer(modifier = Modifier.height(6.dp))
            MovieItemText(
                icon = painterResource(R.drawable.calendarblank),
                text = model.release_date,
            )
            Spacer(modifier = Modifier.height(6.dp))
            MovieItemText(
                icon = painterResource(R.drawable.clock),
                text = if (model.adult) "18+" else "16+",
            )
        }
    }
}

@Composable
fun MovieItemText(
    icon: Painter,
    text: String,
    isYellow: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = if (isYellow) {
                Color(0xffff8700)
            } else {
                Color.Unspecified
            },
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isYellow) {
                Color(0xffff8700)
            } else {
                Color.Unspecified
            },
        )
    }
}