package pe.edu.upc.superhero.features.favorites.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import pe.edu.upc.superhero.core.ui.components.FavoriteRow

@Composable
fun FavoritesView(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val heroes = viewModel.heroes.collectAsState()

    LazyColumn(modifier) {
        items(heroes.value) { hero ->
            FavoriteRow(hero) {
                viewModel.removeFavorite(hero)
            }
        }
    }
}