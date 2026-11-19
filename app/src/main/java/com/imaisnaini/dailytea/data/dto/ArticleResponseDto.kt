package com.imaisnaini.dailytea.data.dto

data class ArticleResponseDto(
    val status: String,
    val totalResult: Int?,
    val articles: List<ArticleDto>?,
    val code: String?,
    val message: String?
)
