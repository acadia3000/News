package com.example.news.presentation.binding

import androidx.databinding.ViewDataBinding
import com.example.news.BR

class SimpleDataBindingViewHolder<T>(binding: ViewDataBinding) :
    DataBindingViewHolder<T>(binding) {

    override fun bindItem(item: T) {
        binding.setVariable(BR.item, item)
    }
}
