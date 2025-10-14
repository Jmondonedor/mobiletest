package pe.edu.upc.superhero.features.news.domain

data class News(
    val id: String,                    // Puedes usar URL como ID único
    val author: String?,               // NUEVO
    val title: String,                 // Equivale a "name"
    val description: String?,          // NUEVO
    val url: String,                   // NUEVO - para abrir en navegador
    val urlToImage: String?,           // Equivale a "image"
    val publishedAt: String,           // NUEVO
    val content: String?,              // NUEVO
    val sourceName: String?,           // NUEVO - source.name
    val isFavorite: Boolean = false
)