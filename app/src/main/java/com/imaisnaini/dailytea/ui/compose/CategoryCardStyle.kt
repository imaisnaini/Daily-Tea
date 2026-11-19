package com.imaisnaini.dailytea.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.imaisnaini.dailytea.domain.model.Category
import com.imaisnaini.dailytea.ui.theme.*

data class CategoryCardStyle(
    val containerColor: Color,
    val contentColor: Color,
    val icon: ImageVector
)

val Category.cardStyle: CategoryCardStyle
    get() = when (this) {
        Category.TECHNOLOGY -> CategoryCardStyle(
            containerColor = PrimaryBlue,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.Memory
        )
        Category.GENERAL -> CategoryCardStyle(
            containerColor = BrandBlue,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.Public
        )
        Category.BUSINESS -> CategoryCardStyle(
            containerColor = BrandYellow,
            contentColor = TextOnYellow,
            icon = Icons.Rounded.TrendingUp
        )
        Category.SPORTS -> CategoryCardStyle(
            containerColor = ErrorRed,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.MonitorHeart
        )
        Category.SCIENCE -> CategoryCardStyle(
            containerColor = SuccessGreen,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.Eco
        )
        Category.HEALTH -> CategoryCardStyle(
            containerColor = BrightBlue,
            contentColor = BrandBlue,
            icon = Icons.Rounded.Science
        )
        Category.ENTERTAINMENT -> CategoryCardStyle(
            containerColor = WarningOrange,
            contentColor = TextPrimary,
            icon = Icons.Rounded.StarBorder
        )
    }