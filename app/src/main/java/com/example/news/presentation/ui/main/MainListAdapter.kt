package com.example.news.presentation.ui.main

import androidx.databinding.ViewDataBinding
import com.example.news.presentation.binding.DataBindingAdapter
import com.example.news.presentation.binding.DataBindingPresenter
import com.example.news.presentation.binding.SimpleDataBindingViewHolder
import com.example.news.presentation.binding.SimpleItemDiffCallback
import com.example.news.presentation.model.ArticleListItemModel

class MainListAdapter(presenter: DataBindingPresenter? = null) :
    DataBindingAdapter<ArticleListItemModel>(presenter, SimpleItemDiffCallback()) {

    override fun getItemViewType(position: Int) = getItem(position).layoutResId
    override fun createDataBindingViewHolder(binding: ViewDataBinding) =
        SimpleDataBindingViewHolder<ArticleListItemModel>(binding)
}
