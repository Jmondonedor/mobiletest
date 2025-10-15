package pe.edu.upc.superhero.features.news.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import pe.edu.upc.superhero.core.ui.components.FavoriteNewsRow

@Composable
fun FavoritesNewsView(
    modifier: Modifier = Modifier,
    viewModel: FavoritesNewsViewModel = hiltViewModel()
) {
    val newsList = viewModel.news.collectAsState()

    LazyColumn(modifier) {
        items(newsList.value) { news ->
            FavoriteNewsRow(news) {
                viewModel.removeFavorite(news)
            }
        }
    }
}