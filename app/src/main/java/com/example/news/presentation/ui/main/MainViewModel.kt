package com.example.news.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.news.domain.usecase.ArticleUseCase
import com.example.news.presentation.lifecycle.ActionStateLiveData
import com.example.news.presentation.model.ArticleListItemModel
import com.example.news.presentation.model.mapper.ArticleItemModelMapper
import com.example.news.presentation.model.state.StateResult

class MainViewModel(
    private val useCase: ArticleUseCase,
    itemModelMapper: ArticleItemModelMapper
) : ViewModel() {

    private val mutableArticles = ActionStateLiveData<List<ArticleListItemModel>> {
        runCatching {
            useCase.headlines()
        }.mapCatching {
            itemModelMapper.mapToModel(it)
        }.getOrThrow()
    }

    val articles: LiveData<StateResult<List<ArticleListItemModel>>> = mutableArticles

    val isLoading = Transformations.map(articles) { state ->
        state is StateResult.Loading
    }

    fun fetch(pullToRefresh: Boolean = false) {
        mutableArticles.load(pullToRefresh.not())
    }
}