package pe.edu.upc.superhero.features.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.superhero.features.news.domain.News
import pe.edu.upc.superhero.features.news.domain.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableStateFlow<News?>(null)
    val news: StateFlow<News?> = _news

    fun loadNews(newsUrl: String) {
        viewModelScope.launch {
            // 1. Intentar obtener del caché primero
            val cachedNews = SearchNewsViewModel.getNewsFromCache(newsUrl)

            if (cachedNews != null) {
                // Verificar si está en favoritos
                val favorites = repository.getAllFavorites()
                val isFavorite = favorites.any { it.url == newsUrl }
                _news.value = cachedNews.copy(isFavorite = isFavorite)
            } else {
                // 2. Si no está en caché, buscar en favoritos
                val favorites = repository.getAllFavorites()
                _news.value = favorites.find { it.url == newsUrl }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _news.value?.let { currentNews ->
                try {
                    if (currentNews.isFavorite) {
                        repository.delete(currentNews)
                        _news.value = currentNews.copy(isFavorite = false)
                    } else {
                        repository.insert(currentNews)
                        _news.value = currentNews.copy(isFavorite = true)
                    }

                    // Actualizar caché
                    _news.value?.let { SearchNewsViewModel.cacheNews(it) }
                } catch (e: Exception) {
                    // Manejar error si es necesario
                }
            }
        }
    }
}