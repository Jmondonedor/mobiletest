package pe.edu.upc.superhero.features.news.data.remote.service

import pe.edu.upc.superhero.features.news.data.remote.models.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("articles.php")
    suspend fun searchNews(
        @Query("description") query: String
    ): Response<NewsResponseDto>
}