package com.imaisnaini.dailytea.domain.model

enum class Category(
    val displayName: String,
    val apiValue: String
) {
    TECHNOLOGY("Technology", "technology"),
    EDUCATION("Education", "education"),
    POLITICS("Politics", "politics"),
    FINANCE("Finance", "finance"),
    HEALTH("Health", "health"),
    NATURE("Nature", "nature"),
    SCIENCE("Science", "science"),
    SPORTS("Sports", "sports"),
    ENTERTAINMENT("Entertainment", "entertainment"),
    CULTURE("Culture", "culture"),
}