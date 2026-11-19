package com.imaisnaini.dailytea.data.repository

import com.imaisnaini.dailytea.data.enpoint.ApiService
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiService: ApiService
) {
}