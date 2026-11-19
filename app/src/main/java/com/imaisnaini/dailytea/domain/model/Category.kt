package com.imaisnaini.dailytea.domain.model

enum class Category(
    val displayName: String,
    val apiValue: String
) {
    TECHNOLOGY("Technology", "technology"),
    GENERAL("General", "general"),
    BUSINESS("Business", "business"),
    HEALTH("Health", "health"),
    SCIENCE("Science", "science"),
    SPORTS("Sports", "sports"),
    ENTERTAINMENT("Entertainment", "entertainment"),
}