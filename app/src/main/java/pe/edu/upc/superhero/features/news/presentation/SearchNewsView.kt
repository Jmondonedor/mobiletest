package pe.edu.upc.superhero.features.news.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import pe.edu.upc.superhero.core.ui.components.NewsRow

@Composable
fun SearchNewsView(
    modifier: Modifier = Modifier,
    onNewsClick: (String) -> Unit = {},
    viewModel: SearchNewsViewModel = hiltViewModel()
) {
    val query = viewModel.query.collectAsState()
    val newsList = viewModel.news.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = query.value,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Search news...")
            },
            trailingIcon = {
                IconButton(
                    onClick = viewModel::searchNews
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        LazyColumn {
            items(newsList.value) { news ->
                NewsRow(
                    news = news,
                    onToggleFavorite = {
                        viewModel.toggleFavorite(news)
                    },
                    onDetailsClick = {
                        onNewsClick(news.url)
                    }
                )
            }
        }
    }
}