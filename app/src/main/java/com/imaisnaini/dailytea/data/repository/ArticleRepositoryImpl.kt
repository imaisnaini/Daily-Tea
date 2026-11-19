package com.imaisnaini.dailytea.data.repository

import com.imaisnaini.dailytea.data.enpoint.ApiService
import com.imaisnaini.dailytea.data.mapper.toDomain
import com.imaisnaini.dailytea.domain.model.Article
import com.imaisnaini.dailytea.domain.model.Category
import com.imaisnaini.dailytea.domain.model.Source
import com.imaisnaini.dailytea.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ArticleRepository {

    override suspend fun getSources(
        category: Category
    ): Result<List<Source>> = runCatching {

        val response = apiService.getSources(
            category = category.name.lowercase()
        )

        if (response.status != "ok") {
            error(response.message ?: "Failed to retrieve sources")
        }

        response.sources.map { source ->
            Source(
                id = source.id,
                name = source.name,
                description = source.description,
                url = source.url,
                category = source.category,
                language = source.language,
                country = source.country
            )
        }
    }


    override suspend fun getTopHeadlines(
        sourceId: String,
        query: String?,
        page: Int
    ): Result<List<Article>> = runCatching {

        val response = apiService.getTopHeadlines(
            sources = sourceId,
            query = query,
            page = page
        )

        if (response.status != "ok") {
            error(response.message ?: "Failed to retrieve articles")
        }

        response.articles?.map {
            it.toDomain()
        } as List<Article>
    }


    override suspend fun searchArticles(
        query: String,
        sourceId: String?,
        page: Int
    ): Result<List<Article>> = runCatching {

        val response = apiService.searchEverything(
            query = query,
            sources = sourceId,
            page = page
        )

        if (response.status != "ok") {
            error(response.message ?: "Failed to search articles")
        }

        response.articles?.map {
            it.toDomain()
        } as List<Article>
    }
}