package com.imaisnaini.dailytea.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaisnaini.dailytea.domain.model.Article
import com.imaisnaini.dailytea.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    private val _articles =
        MutableLiveData<List<Article>>(emptyList())

    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData(false)

    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)

    val error: LiveData<String?> = _error

    private var currentSourceId: String? = null
    private var currentQuery: String = ""

    fun loadArticles(
        sourceId: String,
        query: String = ""
    ) {
        if (_isLoading.value == true) return

        currentSourceId = sourceId
        currentQuery = query.trim()

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = if (currentQuery.isBlank()) {
                repository.getTopHeadlines(
                    sourceId = sourceId
                )
            } else {
                repository.searchArticles(
                    query = currentQuery,
                    sourceId = sourceId
                )
            }

            result
                .onSuccess { result ->
                    _articles.value = result
                }
                .onFailure { exception ->
                    _error.value = exception.message
                        ?: "Unable to load articles"
                }

            _isLoading.value = false
        }
    }

    fun retry() {
        currentSourceId?.let { sourceId ->
            loadArticles(
                sourceId = sourceId,
                query = currentQuery
            )
        }
    }
}
