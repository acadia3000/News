package com.example.news.domain.usecase

import com.example.news.data.repository.NewsRepository
import com.example.news.domain.model.mapper.ArticleMapper

class ArticleUseCase(private val repository: NewsRepository, private val mapper: ArticleMapper) {

    suspend fun headlines(sources: String = "techcrunch") = runCatching {
        repository.headlines(sources)
    }.mapCatching { response ->
        mapper.mapToModel(response)
    }.onFailure {
        // handleError(throwable)
    }.getOrThrow()
}