package com.imaisnaini.dailytea.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaisnaini.dailytea.domain.model.Category
import com.imaisnaini.dailytea.domain.model.Source
import com.imaisnaini.dailytea.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 10
    }
    private var allSources: List<Source> = emptyList()
    private var filteredSources: List<Source> = emptyList()
    private var currentVisibleCount = PAGE_SIZE
    private var currentQuery = ""
    private var selectedCategory: Category? = null
    private var hasLoaded = false
    private val _sources =
        MutableLiveData<List<Source>>(emptyList())
    val sources: LiveData<List<Source>> = _sources
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error
    private val _hasMoreSources = MutableLiveData(false)
    val hasMoreSources: LiveData<Boolean> = _hasMoreSources

    fun loadSources(
        category: Category,
        forceRefresh: Boolean = false
    ) {
        if (_isLoading.value == true) return
        if (
            !forceRefresh &&
            selectedCategory == category &&
            hasLoaded
        ) return

        selectedCategory = category

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.getSources(category)
                .onSuccess { result ->
                    allSources = result
                    hasLoaded = true
                    applySearchFilter()
                }
                .onFailure { exception ->
                    _error.value = exception.message
                        ?: "Unable to load news sources"
                }
            _isLoading.value = false
        }
    }

    fun searchSources(query: String) {
        currentQuery = query.trim()
        applySearchFilter()
    }

    private fun applySearchFilter() {
        filteredSources = if (currentQuery.isBlank()) {
            allSources
        } else {
            allSources.filter { source ->
                source.name.contains(
                    currentQuery,
                    ignoreCase = true
                ) || source.description.orEmpty().contains(
                    currentQuery,
                    ignoreCase = true
                )
            }
        }
        currentVisibleCount = PAGE_SIZE
        updateVisibleSources()
    }

    fun loadMoreSources() {
        if (_isLoading.value == true) return
        if (currentVisibleCount >= filteredSources.size) return
        currentVisibleCount = minOf(
            currentVisibleCount + PAGE_SIZE,
            filteredSources.size
        )
        updateVisibleSources()
    }

    private fun updateVisibleSources() {
        _sources.value = filteredSources.take(currentVisibleCount)
        _hasMoreSources.value =
            currentVisibleCount < filteredSources.size
    }

    fun retry() {
        selectedCategory?.let { category ->
            loadSources(
                category = category,
                forceRefresh = true
            )
        }
    }
}