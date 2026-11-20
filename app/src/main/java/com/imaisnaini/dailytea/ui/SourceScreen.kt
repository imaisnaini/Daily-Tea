package com.imaisnaini.dailytea.ui

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.imaisnaini.dailytea.domain.model.Category
import com.imaisnaini.dailytea.domain.model.Source
import com.imaisnaini.dailytea.ui.compose.Header
import com.imaisnaini.dailytea.ui.compose.SearchBar
import com.imaisnaini.dailytea.ui.compose.cardStyle
import com.imaisnaini.dailytea.ui.theme.BackgroundLight
import com.imaisnaini.dailytea.ui.theme.SurfaceDark
import com.imaisnaini.dailytea.ui.theme.TextPrimary
import com.imaisnaini.dailytea.ui.theme.TextSecondary
import com.imaisnaini.dailytea.ui.theme.Typography
import com.imaisnaini.dailytea.ui.theme.border_light
import com.imaisnaini.dailytea.ui.theme.icon_medium
import com.imaisnaini.dailytea.ui.theme.icon_small
import com.imaisnaini.dailytea.ui.theme.icon_xlarge
import com.imaisnaini.dailytea.ui.theme.padding_large
import com.imaisnaini.dailytea.ui.theme.padding_normal
import com.imaisnaini.dailytea.ui.theme.padding_small
import com.imaisnaini.dailytea.ui.theme.radius_normal
import com.imaisnaini.dailytea.ui.theme.spacer_normal
import com.imaisnaini.dailytea.ui.theme.spacer_small


@Composable
fun SourceScreen(
    category: Category,
    onSourceClick: (Source) -> Unit,
    viewModel: SourceViewModel = hiltViewModel()
) {
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val sources by viewModel.sources.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()
    val hasMoreSources by viewModel.hasMoreSources.observeAsState(false)

    val listState = rememberLazyListState()

    LaunchedEffect(category) {
        viewModel.loadSources(category)
    }

    LaunchedEffect(searchQuery) {
        viewModel.searchSources(searchQuery)
        listState.scrollToItem(0)
    }

    LaunchedEffect(
        listState,
        sources.size,
        hasMoreSources
    ) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
                .lastOrNull()?.index
        }.collect { lastVisibleIndex ->
            if (
                lastVisibleIndex != null &&
                sources.isNotEmpty() &&
                lastVisibleIndex >= sources.size - 3 &&
                hasMoreSources
            ) {
                viewModel.loadMoreSources()
            }
        }
    }

    Scaffold(
        backgroundColor = BackgroundLight,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = padding_normal),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
        ) {
            item(key = "header") {
                Header(
                    title = "Explore Sources",
                    subtitle = "Choose your favorite news sources of tea..."
                )
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    placeholder = "Find your tea's source..",
                    modifier = Modifier
                        .padding(horizontal = spacer_normal, vertical = padding_large)
                )
            }

            if (isLoading && sources.isEmpty()) {
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
            } else if (error != null && sources.isEmpty()) {
                item(key = "error") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(padding_large),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = error ?: "Oppsie.. Something went wrong",
                            style = Typography.body1
                        )
                        Spacer(modifier = Modifier.height(spacer_small))
                        Button(
                            onClick = { viewModel.retry() }
                        ) {
                            Text("Retry")
                        }
                    }
                }
            } else if (sources.isEmpty()) {
                item(key = "empty") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(padding_large),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (searchQuery.length >= 3) {
                                "No tea's sources found"
                            } else {
                                "No tea's sources available"
                            },
                            color = SurfaceDark.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                items(
                    items = sources,
                    key = { it.id }
                ) { source ->
                    SourceItem(
                        source = source,
                        onClick = { onSourceClick(source) },
                        modifier = Modifier.padding(horizontal = padding_large)
                    )

                    Divider(
                        color = SurfaceDark.copy(alpha = 0.08f),
                        modifier = Modifier.padding(
                            horizontal = padding_large
                        )
                    )
                }
                if (hasMoreSources) {
                    item(key = "pagination_loading") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding_large),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(icon_small),
                                strokeWidth = border_light
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SourceItem(
    source: Source,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = padding_normal),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacer_small)
    ) {
        SourceCategoryIcon(category = source.category)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(padding_small)
        ) {
            Text(
                text = source.name,
                style = Typography.subtitle2,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = source.description
                    ?.limitWords(20)
                    ?: "The writer too lazy to write spoiler",
                style = Typography.caption,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = "View source",
            tint = TextSecondary,
            modifier = Modifier.size(icon_small)
        )
    }
}

private fun String.limitWords(
    maxWords: Int
): String {
    val words = trim().split(Regex("\\s+"))
    return if (words.size > maxWords) {
        words.take(maxWords).joinToString(" ") + "..."
    } else {
        this
    }
}

fun String.toCategory(): Category {
    return Category.entries.firstOrNull {
        it.apiValue.equals(this, ignoreCase = true)
    } ?: Category.GENERAL
}

@Composable
fun SourceCategoryIcon(
    category: String,
    modifier: Modifier = Modifier
) {
    val style = category.toCategory().cardStyle
    Surface(
        modifier = modifier.size(icon_xlarge),
        shape = RoundedCornerShape(radius_normal),
        color = style.containerColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = style.icon,
                contentDescription = category,
                tint = style.contentColor,
                modifier = Modifier.size(icon_medium)
            )
        }
    }
}