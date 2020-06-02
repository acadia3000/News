package com.example.news.data.repository

import com.example.news.data.network.ApiRemoteDataSource

class NewsRepository(private val remoteDataSource: ApiRemoteDataSource) {

    suspend fun headlines(sources: String) = remoteDataSource.headlines(sources)
}
