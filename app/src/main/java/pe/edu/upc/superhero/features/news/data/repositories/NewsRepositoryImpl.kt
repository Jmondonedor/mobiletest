package pe.edu.upc.superhero.features.news.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.superhero.features.news.data.local.dao.NewsDao
import pe.edu.upc.superhero.features.news.data.local.models.NewsEntity
import pe.edu.upc.superhero.features.news.data.remote.service.NewsService
import pe.edu.upc.superhero.features.news.domain.News
import pe.edu.upc.superhero.features.news.domain.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val service: NewsService,
    private val dao: NewsDao
) : NewsRepository {

    override suspend fun searchNews(query: String): List<News> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.searchNews(query)
                if (response.isSuccessful && response.body() != null) {
                    // Ahora response.body() es List<NewsDto> directamente
                    val articles = response.body()!!
                    return@withContext articles.map { dto ->
                        News(
                            id = dto.url,
                            author = dto.author,
                            title = dto.title,
                            description = dto.description,
                            url = dto.url,
                            urlToImage = dto.urlToImage,
                            publishedAt = dto.publishedAt,
                            content = dto.content,
                            sourceName = dto.source.name,
                            isFavorite = dao.fetchByUrl(dto.url).isNotEmpty()
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace() // Para debugging
                return@withContext emptyList()
            }
            return@withContext emptyList()
        }

    override suspend fun insert(news: News) = withContext(Dispatchers.IO) {
        dao.insert(news.toEntity())
    }

    override suspend fun delete(news: News) = withContext(Dispatchers.IO) {
        dao.delete(news.toEntity())
    }

    override suspend fun getAllFavorites(): List<News> =
        withContext(Dispatchers.IO) {
            dao.fetchAll().map { it.toDomain() }
        }
}

// Extension functions para mapear
private fun News.toEntity() = NewsEntity(
    url = url,
    author = author,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    sourceName = sourceName
)

private fun NewsEntity.toDomain() = News(
    id = url,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    sourceName = sourceName,
    isFavorite = true
)