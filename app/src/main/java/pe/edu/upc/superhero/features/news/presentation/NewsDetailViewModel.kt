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
            // Buscar en favoritos primero
            val favorites = repository.getAllFavorites()
            _news.value = favorites.find { it.url == newsUrl }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _news.value?.let { currentNews ->
                if (currentNews.isFavorite) {
                    repository.delete(currentNews)
                    _news.value = currentNews.copy(isFavorite = false)
                } else {
                    repository.insert(currentNews)
                    _news.value = currentNews.copy(isFavorite = true)
                }
            }
        }
    }
}