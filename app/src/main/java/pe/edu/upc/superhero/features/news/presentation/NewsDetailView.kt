package pe.edu.upc.superhero.features.news.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

private val NewsDetailViewModel.news: Any

@Composable
fun NewsDetailView(
    newsUrl: String?,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val news = viewModel.news.collectAsState()

    LaunchedEffect(newsUrl) {
        newsUrl?.let { viewModel.loadNews(it) }
    }

    news.value?.let { newsItem ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Source name
            Text(
                newsItem.sourceName ?: "",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(16.dp)
            )

            // Imagen (25-30% de pantalla)
            AsyncImage(
                model = newsItem.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),  // Ajusta según 25-30%
                contentScale = ContentScale.Crop
            )

            // Content
            Text(
                newsItem.content ?: "",
                modifier = Modifier.padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botón para abrir URL
                IconButton(onClick = {
                    // Abrir en navegador
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url))
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Default.OpenInBrowser, contentDescription = "Open")
                }

                // Botón favorito
                IconButton(onClick = { viewModel.toggleFavorite() }) {
                    Icon(
                        if (newsItem.isFavorite) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
        }
    }
}