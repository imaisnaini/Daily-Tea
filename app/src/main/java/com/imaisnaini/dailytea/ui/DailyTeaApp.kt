package com.imaisnaini.dailytea.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.imaisnaini.dailytea.domain.model.Category

@Composable
fun DailyTeaApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "categories"
    ) {
        composable("categories") {
            CategoryScreen(
                onCategoryClick = { category ->
                    navController.navigate("sources/${category.apiValue}")
                },
                onSearchSubmit = {},
                modifier = Modifier
            )
        }

        composable(
            route = "sources/{category}",
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments
                ?.getString("category")
            val category = Category.entries.firstOrNull {
                it.apiValue == categoryName
            } ?: Category.GENERAL

            SourceScreen(
                category = category,
                onSourceClick = { source ->
                    // TODO : Navigate to articles later
                }
            )
        }
    }
}