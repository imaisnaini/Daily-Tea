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
                    navController.navigate(
                        "articles/${source.id}?sourceName=${source.name}"
                    )
                }
            )
        }
        composable(
            route = "articles/{sourceId}?sourceName={sourceName}",
            arguments = listOf(
                navArgument("sourceId") {
                    type = NavType.StringType
                },
                navArgument("sourceName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val sourceId = backStackEntry.arguments
                ?.getString("sourceId").orEmpty()

            val sourceName = backStackEntry.arguments
                ?.getString("sourceName").orEmpty()

            ArticleScreen(
                sourceId = sourceId,
                sourceName = sourceName,
                onArticleClick = { article ->
                    article.url?.let { url ->
                        navController.navigate(
                            "article-webview?url=${url}"
                        )
                    }
                }
            )
        }
        composable(
            route = "article-webview?url={url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val articleUrl = backStackEntry.arguments
                ?.getString("url")
                .orEmpty()

            ArticleWebViewScreen(
                articleUrl = articleUrl
            )
        }
    }
}