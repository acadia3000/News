package com.example.news

import com.example.news.data.network.ApiRemoteDataSource
import com.example.news.data.network.AuthorizationInterceptor
import com.example.news.data.repository.NewsRepository
import com.example.news.domain.model.mapper.ArticleMapper
import com.example.news.domain.usecase.ArticleUseCase
import com.example.news.presentation.model.mapper.ArticleItemModelMapper
import com.example.news.presentation.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

interface AppModules {

    companion object {
        operator fun invoke() = module {
            single { AuthorizationInterceptor() }
            single { ApiRemoteDataSource(authorizationInterceptor = get()) }
            single { NewsRepository(remoteDataSource = get()) }

            single { ArticleMapper() }
            single { ArticleItemModelMapper() }

            factory { ArticleUseCase(repository = get(), mapper = get()) }

            viewModel { MainViewModel(useCase = get(), itemModelMapper = get()) }
        }
    }
}