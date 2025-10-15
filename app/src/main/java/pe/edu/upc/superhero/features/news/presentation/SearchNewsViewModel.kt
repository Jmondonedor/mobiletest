package pe.edu.upc.superhero.features.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.upc.superhero.features.news.domain.News
import pe.edu.upc.superhero.features.news.domain.NewsRepository
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Caché estático para compartir datos con NewsDetailViewModel
    companion object {
        private val newsCache = mutableMapOf<String, News>()

        fun getNewsFromCache(url: String): News? = newsCache[url]

        fun cacheNews(news: News) {
            newsCache[news.url] = news
        }

        fun clearCache() {
            newsCache.clear()
        }
    }

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun searchNews() {
        // Validar que el query no esté vacío
        if (_query.value.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val results = repository.searchNews(_query.value)
                _news.value = results

                // Cachear todas las noticias para NewsDetailView
                results.forEach { news ->
                    cacheNews(news)
                }
            } catch (e: Exception) {
                _news.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(news: News) {
        viewModelScope.launch {
            try {
                if (news.isFavorite) {
                    repository.delete(news)
                } else {
                    repository.insert(news)
                }

                _news.update { list ->
                    list.map { n ->
                        if (n.id == news.id) {
                            val updated = n.copy(isFavorite = !n.isFavorite)
                            cacheNews(updated) // Actualizar en caché también
                            updated
                        } else n
                    }
                }
            } catch (e: Exception) {
                // Manejo de error silencioso
            }
        }
    }
}