package com.example.news.domain.model.mapper

import com.example.news.data.response.NewsResponse
import com.example.news.domain.model.Article
import retrofit2.Response

class ArticleMapper : Mapper<Response<NewsResponse>?, List<Article>> {

    override fun mapToModel(from: Response<NewsResponse>?) = from?.body()?.let { response ->
        response.articles.also {
            if (it.isNullOrEmpty()) {
                throw RuntimeException("No Data!")
            }
        }
    } ?: throw RuntimeException("Server Error.")
}
