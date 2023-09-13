package net.coblos.rrnet.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.coblos.rrnet.repository.Repository
import net.coblos.rrnet.repository.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(repositoryImpl: RepositoryImpl): Repository = repositoryImpl
}