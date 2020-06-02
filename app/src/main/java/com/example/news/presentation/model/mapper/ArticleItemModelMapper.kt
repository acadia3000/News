package com.example.news.presentation.model.mapper

import com.example.news.domain.model.Article
import com.example.news.domain.model.mapper.Mapper
import com.example.news.presentation.model.ArticleListItemModel

class ArticleItemModelMapper : Mapper<List<Article>, List<ArticleListItemModel>> {

    override fun mapToModel(from: List<Article>): List<ArticleListItemModel> {
        return from.map { ArticleListItemModel.ContentItemModel(it) }
    }
}
