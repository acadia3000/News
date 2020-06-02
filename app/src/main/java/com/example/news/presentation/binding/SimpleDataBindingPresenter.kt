package com.example.news.presentation.binding

import android.view.View
import com.example.news.presentation.binding.DataBindingPresenter

open class SimpleDataBindingPresenter : DataBindingPresenter {
    override fun onClick(view: View, item: Any) {}
    override fun onLongClick(view: View, item: Any): Boolean = false
    override fun onCheckedChanged(view: View, item: Any, checked: Boolean) {}
}
