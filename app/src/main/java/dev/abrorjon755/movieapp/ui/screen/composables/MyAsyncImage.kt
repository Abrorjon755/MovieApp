package dev.abrorjon755.movieapp.ui.screen.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

@Composable
fun MyAsyncImage(
    url: String?,
    modifier: Modifier = Modifier,
) {
    val imageUrl = "https://image.tmdb.org/t/p/w780$url"
    val shimmer = remember { Animatable(0f) }

    LaunchedEffect(true) {
        launch {
            shimmer.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 5000, delayMillis = 0),
            )
        }
    }

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color.Gray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.8f),
            Color.Gray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.8f),
            Color.Gray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.8f),
            Color.Gray.copy(alpha = 0.3f),
        ),
        start = Offset(shimmer.value * 1f, 0f),
        end = Offset(shimmer.value * 1000f, 1000f)
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(shimmerBrush)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .memoryCacheKey(imageUrl)
                .addHeader("accept", "application/json")
                .addHeader(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMGI2NTI1ODk2Zjg5MjEzZjQyZTlhYTI3OWQwNTcwZSIsIm5iZiI6MTcyNjIxODg0Ny4wMiwic3ViIjoiNjZlNDAyNWZjODFiMjRiM2ZlMjNmYWE4Iiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.QUcJ5a2BhpHFnhe7mJTR_GIc-QX-EQD8ni5lOuh-hSA"
                )
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }

}