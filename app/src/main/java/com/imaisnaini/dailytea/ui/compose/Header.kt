package com.imaisnaini.dailytea.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalCafe
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.imaisnaini.dailytea.ui.theme.*

@Composable
fun Header(
    title: String,
    subtitle: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    searchPlaceholder: String = "Find your tea.."
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
            text = title,
            style = Typography.h4.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            ),
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(padding_normal))

        Text(
            text = subtitle,
            style = Typography.body1,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(spacer_normal))

        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearchClick = onSearchClick,
            placeholder = searchPlaceholder
        )
    }
}