package com.imaisnaini.dailytea.data.dto

data class SourceResponseDto(
    val status: String,
    val sources: List<SourceDto> = emptyList(),
    val code: String? = null,
    val message: String? = null
)
