package com.imaisnaini.dailytea.data.entity

data class ArticleResponse(
    val status: String,
    val totalResult: Int?,
    val articles: List<Article>?,
    val code: String?,
    val message: String?
)
