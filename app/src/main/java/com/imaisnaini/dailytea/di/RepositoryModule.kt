package com.imaisnaini.dailytea.di

import com.imaisnaini.dailytea.data.repository.ArticleRepositoryImpl
import com.imaisnaini.dailytea.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindArticleRepository(
        implementation: ArticleRepositoryImpl
    ): ArticleRepository
}