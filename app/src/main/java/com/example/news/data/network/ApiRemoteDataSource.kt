package com.example.news.data.network

import com.example.news.data.response.NewsResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface ApiRemoteDataSource {

    @GET("top-headlines")
    suspend fun headlines(@Query("sources") sources: String): Response<NewsResponse>

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"

        operator fun invoke(authorizationInterceptor: AuthorizationInterceptor): ApiRemoteDataSource {
            val okHttpBuilder = OkHttpClient.Builder()
                .addInterceptor(authorizationInterceptor)

            return Retrofit.Builder()
                .client(okHttpBuilder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .build()
                .create(ApiRemoteDataSource::class.java)
        }
    }
}
