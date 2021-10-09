package com.floriv.moviereferences.core.di

import com.floriv.moviereferences.BuildConfig
import com.floriv.moviereferences.core.utils.LoggingInterceptor
import com.floriv.moviereferences.features.movie.MovieRepository
import com.floriv.moviereferences.features.trending.TrendingRepository
import com.floriv.moviereferences.features.tvshow.TvShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val address = BuildConfig.BASE_URL
        return Retrofit.Builder()
            .baseUrl(address)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = LoggingInterceptor()
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(dataSource: MovieRepository.Network): MovieRepository =
        dataSource

    @Provides
    @Singleton
    fun provideTvShowRepository(dataSource: TvShowRepository.Network): TvShowRepository =
        dataSource

    @Provides
    @Singleton
    fun provideTrendingRepository(dataSource: TrendingRepository.Network): TrendingRepository =
        dataSource
}
