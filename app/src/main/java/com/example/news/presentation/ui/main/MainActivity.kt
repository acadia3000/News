package com.example.news.presentation.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding
import com.example.news.presentation.binding.SimpleDataBindingPresenter
import com.example.news.presentation.model.ArticleListItemModel
import com.example.news.presentation.model.ArticleListItemModel.ContentItemModel
import com.example.news.presentation.model.state.StateResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainListAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
        }.also {
            binding = it
        }

        initViews()

        mainViewModel.apply {
            articles.observe(this@MainActivity, Observer { data ->
                when (data) {
                    is StateResult.Success<List<ArticleListItemModel>> -> {
                        mainListAdapter.submitList(data.item)
                    }
                    is StateResult.Failure -> {
                        Toast.makeText(this@MainActivity, data.cause.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }

                binding.refresher.isRefreshing = false
            })
        }.run {
            fetch()
        }
    }

    private fun initViews() {
        mainListAdapter = MainListAdapter(object : SimpleDataBindingPresenter() {
            override fun onClick(view: View, item: Any) {
                when (item) {
                    is ContentItemModel -> launchUrl(item.article.url)
                }
            }
        })

        binding.rvArticles.apply {
            adapter = mainListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.refresher.apply {
            setColorSchemeResources(R.color.colorAccent)
            setOnRefreshListener { mainViewModel.fetch(true) }
        }
    }

    private fun launchUrl(url: String) {
        CustomTabsIntent.Builder().apply {
            setToolbarColor(getColor(R.color.colorPrimary))
        }.build().launchUrl(this, Uri.parse(url))
    }
}