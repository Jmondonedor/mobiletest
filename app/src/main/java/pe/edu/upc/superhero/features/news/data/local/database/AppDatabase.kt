package pe.edu.upc.superhero.features.news.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.superhero.features.news.data.local.dao.NewsDao
import pe.edu.upc.superhero.features.news.data.local.models.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}