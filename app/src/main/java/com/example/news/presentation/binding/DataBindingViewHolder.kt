package com.example.news.presentation.binding

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.news.BR
import kotlinx.android.extensions.LayoutContainer

abstract class DataBindingViewHolder<T>(internal val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root), LayoutContainer {

    override val containerView: View?
        get() = binding.root

    fun bind(item: T, presenter: DataBindingPresenter?) {
        presenter?.let {
            binding.setVariable(BR.presenter, it)
        }
        bindItem(item)
        binding.executePendingBindings()
    }

    abstract fun bindItem(item: T)
}
