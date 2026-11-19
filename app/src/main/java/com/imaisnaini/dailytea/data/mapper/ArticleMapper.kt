package com.imaisnaini.dailytea.data.mapper

import com.imaisnaini.dailytea.data.dto.ArticleDto
import com.imaisnaini.dailytea.domain.model.Article

fun ArticleDto.toDomain(): Article {
    return Article(
        sourceId = source.id,
        sourceName = source.name,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}