package com.imaisnaini.dailytea.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaisnaini.dailytea.domain.model.Article
import com.imaisnaini.dailytea.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    companion object {
        private const val SEARCH_DELAY = 500L
        private const val MIN_SEARCH_LENGTH = 3
    }

    private val _articles = MutableLiveData<List<Article>>(emptyList())
    val articles: LiveData<List<Article>> = _articles
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error
    private var currentSourceId: String? = null
    private var currentQuery = ""
    private var hasActiveSearch = false
    private var requestJob: Job? = null

    fun loadArticles(
        sourceId: String,
        query: String = ""
    ) {
        currentSourceId = sourceId
        currentQuery = query.trim()
        requestJob?.cancel()

        val searchQuery = currentQuery.takeIf {
            it.length >= MIN_SEARCH_LENGTH
        }.orEmpty()

        hasActiveSearch = searchQuery.isNotEmpty()
        requestJob = viewModelScope.launch {
            requestArticles(sourceId, searchQuery)
        }
    }

    fun searchArticles(query: String) {
        currentQuery = query.trim()
        val sourceId = currentSourceId ?: return

        if (currentQuery.length < MIN_SEARCH_LENGTH && !hasActiveSearch) return
        requestJob?.cancel()

        _isLoading.value = false
        _error.value = null

        requestJob = viewModelScope.launch {
            delay(SEARCH_DELAY)

            if (currentQuery.length >= MIN_SEARCH_LENGTH) {
                hasActiveSearch = true
                requestArticles(sourceId, currentQuery)
            } else {
                hasActiveSearch = false
                requestArticles(sourceId, "")
            }
        }
    }

    private suspend fun requestArticles(
        sourceId: String,
        query: String
    ) {
        _isLoading.value = true
        _error.value = null

        val result = if (query.isBlank()) {
            repository.getTopHeadlines(sourceId = sourceId)
        } else {
            repository.searchArticles(
                query = query,
                sourceId = sourceId
            )
        }

        result
            .onSuccess { articles -> _articles.value = articles }
            .onFailure { exception ->
                _error.value = exception.message
                    ?: "Unable to load articles"
            }
        _isLoading.value = false
    }

    fun retry() {
        currentSourceId?.let { sourceId -> requestJob?.cancel()
            val searchQuery = currentQuery.takeIf {
                it.length >= MIN_SEARCH_LENGTH
            }.orEmpty()

            hasActiveSearch = searchQuery.isNotEmpty()
            requestJob = viewModelScope.launch {
                requestArticles(sourceId, searchQuery)
            }
        }
    }
}
