
package com.imaisnaini.dailytea.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.imaisnaini.dailytea.ui.theme.SurfaceDark
import com.imaisnaini.dailytea.ui.theme.SurfaceLight
import com.imaisnaini.dailytea.ui.theme.icon_large

@Composable
fun ArticleImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    if (imageUrl.isNullOrBlank()) {
        ImagePlaceholder(modifier)
    } else {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "Article thumbnail",
            modifier = modifier,
            contentScale = ContentScale.Crop
        ) {
            when (painter.state) {
                is coil.compose.AsyncImagePainter.State.Loading,
                is coil.compose.AsyncImagePainter.State.Error -> {
                    ImagePlaceholder(Modifier.fillMaxSize())
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}

@Composable
private fun ImagePlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(SurfaceLight),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.BrokenImage,
            contentDescription = "Image unavailable",
            tint = SurfaceDark,
            modifier = Modifier.size(icon_large)
        )
    }
}
