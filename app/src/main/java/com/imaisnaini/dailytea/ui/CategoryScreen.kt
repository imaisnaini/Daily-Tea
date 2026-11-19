package com.imaisnaini.dailytea.ui

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalCafe
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.imaisnaini.dailytea.domain.model.Category
import com.imaisnaini.dailytea.ui.compose.SearchBar
import com.imaisnaini.dailytea.ui.theme.BackgroundLight
import com.imaisnaini.dailytea.ui.theme.BorderColorDark
import com.imaisnaini.dailytea.ui.theme.BrandBlue
import com.imaisnaini.dailytea.ui.theme.PrimaryBlue
import com.imaisnaini.dailytea.ui.theme.SurfaceLight
import com.imaisnaini.dailytea.ui.theme.TextPrimary
import com.imaisnaini.dailytea.ui.theme.TextPrimaryDark
import com.imaisnaini.dailytea.ui.theme.TextSecondary
import com.imaisnaini.dailytea.ui.theme.TextSecondaryDark
import com.imaisnaini.dailytea.ui.theme.Typography
import com.imaisnaini.dailytea.ui.theme.border_light
import com.imaisnaini.dailytea.ui.theme.icon_small
import com.imaisnaini.dailytea.ui.theme.padding_large
import com.imaisnaini.dailytea.ui.theme.padding_normal
import com.imaisnaini.dailytea.ui.theme.padding_small
import com.imaisnaini.dailytea.ui.theme.spacer_normal
import com.imaisnaini.dailytea.ui.theme.spacer_small

@Composable
fun CategoryScreen(
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Header(
        searchQuery = "",
        onSearchQueryChange = {},
        onSearchClick = {},
        modifier = modifier
    )
}

@Composable
fun Header(
    searchQuery: String?,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundLight)
            .padding(horizontal = spacer_normal, vertical = padding_large)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding_normal)
        ) {
            Icon(
                imageVector = Icons.Rounded.LocalCafe,
                contentDescription = "Daily Tea Logo",
                tint = PrimaryBlue,
                modifier = Modifier.size(icon_small)
            )
            Text(
                text = "Daily Tea",
                style = Typography.subtitle2.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                ),
                color = PrimaryBlue
            )
        }
        Spacer(modifier = Modifier.height(spacer_small))
        Text(
            text = "Explore Topics",
            style = Typography.h4.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            ),
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(padding_normal))
        Text(
            text = "Choose a category to browse verified publishers and top stories.",
            style = Typography.body1,
            color = TextSecondary
        )
        Spacer(modifier = Modifier.height(spacer_normal))
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearchClick = onSearchClick
        )
    }
}