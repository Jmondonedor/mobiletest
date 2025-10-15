package pe.edu.upc.superhero.features.news.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pe.edu.upc.superhero.features.news.data.repositories.NewsRepositoryImpl
import pe.edu.upc.superhero.features.news.domain.NewsRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}