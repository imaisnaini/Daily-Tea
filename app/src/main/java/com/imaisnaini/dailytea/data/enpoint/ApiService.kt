package com.imaisnaini.dailytea.data.enpoint

import com.imaisnaini.dailytea.data.dto.ArticleResponseDto
import com.imaisnaini.dailytea.data.dto.SourceResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String? = null,
    ): SourceResponseDto

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") sources: String? = null,
        @Query("category") category: String? = null,
        @Query("q") query: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ArticleResponseDto

    @GET("everything")
    suspend fun searchEverything(
        @Query("q") query: String,
        @Query("sources") sources: String? = null,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ArticleResponseDto
}