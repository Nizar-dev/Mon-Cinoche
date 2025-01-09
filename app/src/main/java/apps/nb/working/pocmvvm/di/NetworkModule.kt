package apps.nb.working.pocmvvm.di

import android.content.Context
import apps.nb.working.pocmvvm.core.createService
import apps.nb.working.pocmvvm.data.remote.MovieRepositoryImpl
import apps.nb.working.pocmvvm.data.remote.SearchRepositoryImpl
import apps.nb.working.pocmvvm.domain.MovieRepository
import apps.nb.working.pocmvvm.domain.SearchRepository
import apps.nb.working.pocmvvm.model.BuildConfig
import apps.nb.working.pocmvvm.network.CinocheConnectivityObserver
import apps.nb.working.pocmvvm.network.ConnectivityObserver
import apps.nb.working.pocmvvm.network.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Provides retrofit network calls
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val apiKeyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_KEY)
                .build()

            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApiService(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): MovieApi =
        okHttpClient.createService(
            baseUrl = baseUrl,
            converter = gsonConverterFactory,
            service = MovieApi::class.java
        )

    @Singleton
    @Provides
    fun provideMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository =
        movieRepository

    @Singleton
    @Provides
    fun provideSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository =
        searchRepository

    @Provides
    @Singleton
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return CinocheConnectivityObserver(context)
    }
}
