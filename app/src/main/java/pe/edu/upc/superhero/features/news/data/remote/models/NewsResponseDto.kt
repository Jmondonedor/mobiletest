package pe.edu.upc.superhero.features.news.data.remote.models

data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsDto>  // IMPORTANTE: El API devuelve "articles"
)