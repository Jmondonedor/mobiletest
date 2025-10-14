package pe.edu.upc.superhero.features.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.superhero.features.heroes.domain.Hero
import pe.edu.upc.superhero.features.heroes.domain.HeroRepository
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor
    (private val repository: HeroRepository) : ViewModel() {

    private val _heroes = MutableStateFlow<List<Hero>>(emptyList())
    val heroes: StateFlow<List<Hero>> = _heroes


    private fun getAllFavorites() {
        viewModelScope.launch {
            _heroes.value = repository.getAllFavorites()
        }
    }

    fun removeFavorite(hero: Hero) {
        viewModelScope.launch {
            repository.delete(hero)
        }
        _heroes.value = _heroes.value.filterNot { it.id == hero.id }
    }

    init {
        getAllFavorites()
    }

}
