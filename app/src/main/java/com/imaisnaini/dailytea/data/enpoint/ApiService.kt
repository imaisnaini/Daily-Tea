package com.imaisnaini.dailytea.data.enpoint

import com.imaisnaini.dailytea.BuildConfig
import com.imaisnaini.dailytea.data.entity.ArticleResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.jvm.java

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesByCategory(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ArticleResponse

    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesBySource(
        @Query("sources") source: String,
        @Query("q") query: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ArticleResponse

    @GET("v2/everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("sources") source: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): ArticleResponse

    companion object{
        var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }


}