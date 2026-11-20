package com.imaisnaini.dailytea.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.imaisnaini.dailytea.domain.model.Article
import com.imaisnaini.dailytea.ui.compose.ArticleImage
import com.imaisnaini.dailytea.ui.compose.Header
import com.imaisnaini.dailytea.ui.compose.SearchBar
import com.imaisnaini.dailytea.ui.theme.BackgroundLight
import com.imaisnaini.dailytea.ui.theme.SurfaceDark
import com.imaisnaini.dailytea.ui.theme.TextPrimary
import com.imaisnaini.dailytea.ui.theme.TextSecondary
import com.imaisnaini.dailytea.ui.theme.Typography
import com.imaisnaini.dailytea.ui.theme.img_medium
import com.imaisnaini.dailytea.ui.theme.img_small
import com.imaisnaini.dailytea.ui.theme.padding_large
import com.imaisnaini.dailytea.ui.theme.padding_normal
import com.imaisnaini.dailytea.ui.theme.radius_normal
import com.imaisnaini.dailytea.ui.theme.spacer_normal
import com.imaisnaini.dailytea.ui.theme.spacer_small

@Composable
fun ArticleScreen(
    sourceId: String,
    sourceName: String,
    onArticleClick: (Article) -> Unit,
    viewModel: ArticleViewModel = hiltViewModel()
) {
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val articles by viewModel.articles.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()

    LaunchedEffect(sourceId) {
        viewModel.loadArticles(sourceId)
    }
    LaunchedEffect(searchQuery) {
        viewModel.searchArticles(searchQuery)
    }

    Scaffold(
        backgroundColor = BackgroundLight,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        LazyColumn(
            contentPadding = PaddingValues(
                bottom = padding_large
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
        ) {
            item(key = "header") {
                Header(
                    title = sourceName,
                    subtitle = "Latest teas from $sourceName.",
                )
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    placeholder = "Search teas..",
                    modifier = Modifier
                        .padding(horizontal = spacer_normal, vertical = padding_large)
                )
            }

            when {
                isLoading && articles.isEmpty() -> {
                    item(key = "loading") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding_large),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                error != null && articles.isEmpty() -> {
                    item(key = "error") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding_large),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = error ?: "Oppsie.. Something went wrong",
                                style = Typography.body1
                            )

                            Spacer(
                                modifier = Modifier.height(spacer_small)
                            )

                            Button(
                                onClick = { viewModel.retry() }
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }

                articles.isEmpty() -> {
                    item(key = "empty") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding_large),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.length >= 3) {
                                    "No tea found"
                                } else {
                                    "No tea available"
                                },
                                color = SurfaceDark.copy(alpha = 0.6f)
                            )
                        }
                    }
                }

                else -> {
                    items(
                        items = articles,
                        key = { article ->
                            article.url ?: article.title.orEmpty()
                        }
                    ) { article ->
                        ArticleItem(
                            article = article,
                            onClick = {
                                onArticleClick(article)
                            },
                            modifier = Modifier.padding(
                                horizontal = padding_large
                            )
                        )

                        Divider(
                            color = SurfaceDark.copy(alpha = 0.08f),
                            modifier = Modifier.padding(
                                horizontal = padding_large
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(BackgroundLight)
            .padding(vertical = padding_normal),
        horizontalArrangement = Arrangement.spacedBy(padding_normal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArticleImage(
            imageUrl = article.imageUrl,
            modifier = Modifier
                .size(width = img_medium, height = img_small)
                .clip(RoundedCornerShape(radius_normal))
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(padding_normal)
        ) {
            Text(
                text = article.title ?: "This tea has no title",
                style = Typography.subtitle2,
                color = TextPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = article.description
                    ?: "The writer too lazy to write spoiler",
                style = Typography.caption,
                color = TextSecondary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
