package pe.edu.upc.superhero.features.news.data.remote.models

data class NewsDto(
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val source: SourceDto
)