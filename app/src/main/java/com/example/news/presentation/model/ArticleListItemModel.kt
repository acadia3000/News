package com.example.news.presentation.model

import androidx.annotation.LayoutRes
import com.example.news.R
import com.example.news.domain.model.Article
import com.example.news.presentation.binding.SimpleItemDiffCallback.DiffCallback

sealed class ArticleListItemModel(@LayoutRes val layoutResId: Int) :
    DiffCallback {

    data class ContentItemModel(val article: Article) :
        ArticleListItemModel(R.layout.article_content_list_item)

    data class FooterLoadMoreItemModel(val article: Article) :
        ArticleListItemModel(R.layout.loading_list_item)

    override fun areItemsTheSame(other: DiffCallback): Boolean {
        return if (this is ContentItemModel && other is ContentItemModel) {
            this.article == other.article
        } else {
            super.areItemsTheSame(other)
        }
    }
}
