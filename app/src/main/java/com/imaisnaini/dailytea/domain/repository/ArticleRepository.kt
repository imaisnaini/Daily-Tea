package com.imaisnaini.dailytea.domain.repository

import com.imaisnaini.dailytea.domain.model.Article
import com.imaisnaini.dailytea.domain.model.Category
import com.imaisnaini.dailytea.domain.model.Source

interface ArticleRepository {
    suspend fun getSources(
        category: Category
    ): Result<List<Source>>

    suspend fun getTopHeadlines(
        sourceId: String,
        query: String? = null,
        page: Int = 1
    ): Result<List<Article>>

    suspend fun searchArticles(
        query: String,
        sourceId: String? = null,
        page: Int = 1
    ): Result<List<Article>>
}