package com.imaisnaini.dailytea.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.imaisnaini.dailytea.domain.model.Category
import com.imaisnaini.dailytea.ui.compose.Header
import com.imaisnaini.dailytea.ui.compose.cardStyle
import com.imaisnaini.dailytea.ui.theme.BackgroundLight
import com.imaisnaini.dailytea.ui.theme.MyTheme
import com.imaisnaini.dailytea.ui.theme.Typography
import com.imaisnaini.dailytea.ui.theme.icon_large
import com.imaisnaini.dailytea.ui.theme.icon_small
import com.imaisnaini.dailytea.ui.theme.padding_large
import com.imaisnaini.dailytea.ui.theme.padding_small
import com.imaisnaini.dailytea.ui.theme.radius_large
import com.imaisnaini.dailytea.ui.theme.radius_normal
import com.imaisnaini.dailytea.ui.theme.spacer_normal

@Composable
fun CategoryScreen(
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        backgroundColor = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(bottom = spacer_normal),
            verticalArrangement = Arrangement.spacedBy(padding_large),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
        ) {
            item(key = "header") {
                Header(
                    title = "Explore Topics",
                    subtitle = "What hot-topics you want to read today..",
                    modifier = modifier
                )
            }
            items(
                items = Category.entries,
                key = { it.name }
            ) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category) },
                    modifier = Modifier.padding(horizontal = spacer_normal)
                )
            }
        }
    }
}

@Composable
private fun CategoryCard(
    category: Category,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val style = category.cardStyle
    val cardShape = RoundedCornerShape(radius_large)

    Surface(
        shape = cardShape,
        color = style.containerColor,
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding_large),
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawCircle(
                        color = style.contentColor.copy(alpha = 0.12f),
                        radius = size.height * 0.65f,
                        center = Offset(
                            x = size.width * 0.92f,
                            y = size.height * 0.75f
                        )
                    )
                }
                .padding(padding_large)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(icon_large)
                    .clip(RoundedCornerShape(radius_normal))
                    .background(style.contentColor.copy(alpha = 0.14f))
            ) {
                Icon(
                    imageVector = style.icon,
                    contentDescription = category.displayName,
                    tint = style.contentColor,
                    modifier = Modifier.size(icon_small)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(padding_small),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.displayName,
                    style = Typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = style.contentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Tap to browse →",
                    style = Typography.caption,
                    color = style.contentColor.copy(alpha = 0.85f),
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesScreenPreview() {
    MyTheme(darkTheme = false) {
        CategoryScreen(onCategoryClick = {})
    }
}