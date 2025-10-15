package pe.edu.upc.superhero.features.news.domain

interface NewsRepository {
    suspend fun searchNews(query: String): List<News>
    suspend fun delete(news: News)
    suspend fun insert(news: News)
    suspend fun getAllFavorites(): List<News>
}