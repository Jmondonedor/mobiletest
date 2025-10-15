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
class FavoritesNewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news

    private fun getAllFavorites() {
        viewModelScope.launch {
            _news.value = repository.getAllFavorites()
        }
    }

    fun removeFavorite(news: News) {
        viewModelScope.launch {
            repository.delete(news)
            _news.value = _news.value.filterNot { it.id == news.id }
        }
    }

    init {
        getAllFavorites()
    }
}