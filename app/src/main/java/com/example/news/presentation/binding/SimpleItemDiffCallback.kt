package com.example.news.presentation.binding

import androidx.recyclerview.widget.DiffUtil
import com.example.news.presentation.binding.SimpleItemDiffCallback.DiffCallback

class SimpleItemDiffCallback<T : DiffCallback> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }

    interface DiffCallback {
        fun areItemsTheSame(other: DiffCallback) = (this::class == other::class)
        fun areContentsTheSame(other: DiffCallback) = (this == other)
    }
}
