package pe.edu.upc.superhero.features.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.superhero.features.news.data.remote.service.NewsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    @Named("newsUrl")
    fun provideNewsApiBaseUrl(): String {
        return "https://dev.formandocodigo.com/"
    }

    @Provides
    @Singleton
    @Named("newsRetrofit")
    fun provideNewsRetrofit(@Named("newsUrl") url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsService(@Named("newsRetrofit") retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}