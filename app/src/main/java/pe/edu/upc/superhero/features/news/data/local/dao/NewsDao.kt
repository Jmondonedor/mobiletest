package pe.edu.upc.superhero.features.news.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import pe.edu.upc.superhero.features.news.data.local.models.NewsEntity
import retrofit2.http.Query

@Dao
interface NewsDao {
    @Insert
    suspend fun insert(vararg entity: NewsEntity)

    @Delete
    suspend fun delete(vararg entity: NewsEntity)

    @Query("SELECT * FROM news")
    suspend fun fetchAll(): List<NewsEntity>

    @Query("SELECT * FROM news WHERE url=:url")
    suspend fun fetchByUrl(url: String): List<NewsEntity>
}