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
        Category.EDUCATION -> CategoryCardStyle(
            containerColor = RoyalIndigo,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.School
        )
        Category.POLITICS -> CategoryCardStyle(
            containerColor = BrandBlue,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.Public
        )
        Category.FINANCE -> CategoryCardStyle(
            containerColor = BrandYellow,
            contentColor = TextOnYellow,
            icon = Icons.Rounded.TrendingUp
        )
        Category.SPORTS -> CategoryCardStyle(
            containerColor = ErrorRed,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.MonitorHeart
        )
        Category.NATURE -> CategoryCardStyle(
            containerColor = SuccessGreen,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.Eco
        )
        Category.HEALTH -> CategoryCardStyle(
            containerColor = BrightBlue,
            contentColor = BrandBlue,
            icon = Icons.Rounded.Science
        )
        Category.SCIENCE -> CategoryCardStyle(
            containerColor = DeepTeal,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.SportsSoccer
        )
        Category.ENTERTAINMENT -> CategoryCardStyle(
            containerColor = VividPurple,
            contentColor = TextOnPrimary,
            icon = Icons.Rounded.Movie
        )
        Category.CULTURE -> CategoryCardStyle(
            containerColor = WarningOrange,
            contentColor = TextPrimary,
            icon = Icons.Rounded.StarBorder
        )
    }