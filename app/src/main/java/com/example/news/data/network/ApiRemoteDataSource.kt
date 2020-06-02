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
    suspend fun headlines(
        @Query("sources") sources: String = "techcrunch",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    companion object {
        private const val BASE_URL = "http://newsapi.org/v2/"
        private const val API_KEY = "716cf57f17a442a1a2055ab61d18818c"

        operator fun invoke(apiKey: String): ApiRemoteDataSource {
            val okHttpBuilder = OkHttpClient.Builder()

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
