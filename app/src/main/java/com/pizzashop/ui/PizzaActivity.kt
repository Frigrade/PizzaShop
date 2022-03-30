package com.pizzashop.ui

import android.os.Bundle
import android.view.View
import android.widget.ListAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.pizzashop.R
import com.pizzashop.appComponent
import com.pizzashop.presentation.adapter.BannerAdapter
import com.pizzashop.presentation.adapter.PizzaAdapter
import com.pizzashop.presentation.PizzaViewModel
import com.pizzashop.presentation.PizzaViewModelProviderFactory
import com.pizzashop.presentation.adapter.CategoryAdapter
import com.pizzashop.presentation.adapter.CategoryItem
import com.pizzashop.presentation.state.PizzaState
import com.pizzashop.util.Constants
import com.pizzashop.util.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_pizzashop.*
import javax.inject.Inject

class PizzaActivity: AppCompatActivity() {

    lateinit var viewModel: PizzaViewModel
    lateinit var pizzaAdapter: PizzaAdapter
    lateinit var paginationScrollListener: PaginationScrollListener

    @Inject
    lateinit var viewModelProviderFactory: PizzaViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pizzashop)
        scrollView.setOnScrollChangeListener(stickyToolbar.scrollListener)

        appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(PizzaViewModel::class.java)

        paginationScrollListener = PaginationScrollListener(viewModel::getNewPizza)
        setupPizzaRecyclerView()
        setupBannerRecyclerView()
        setupCategoryRecyclerView()

        viewModel.newPizzaLiveData.observe(this) { state ->
            renderContent(state)
        }
        viewModel.getNewPizza("us")
    }

    private fun renderContent(state: PizzaState) {
        when (state) {
            is PizzaState.Content -> {
                hideProgressBar(paginationProgressBar)
                pizzaAdapter.submitList(state.data.articles)
                updatePaginationListener(state)
            }

            is PizzaState.Error -> {
                hideProgressBar(paginationProgressBar)
                Toast.makeText(applicationContext, "Не удалось связаться с сервером", Toast.LENGTH_SHORT)
                    .show()
            }

            is PizzaState.Loading -> {
                showProgressBar(paginationProgressBar)
            }
        }
    }

    private fun updatePaginationListener(state: PizzaState.Content) {
        val totalPages = state.data.totalResults / Constants.QUERY_PAGE_SIZE + 2
        paginationScrollListener.isLastPage = viewModel.newPizzaPage == totalPages
        if (paginationScrollListener.isLastPage) {
            rvBreakingNews.setPadding(0, 0, 0, 0)
        }
    }

    private fun setupBannerRecyclerView() {
        val bannerAdapter = BannerAdapter(applicationContext)
        bannerAdapter.submitList(getFakeBanners())
        bannerView.adapter = bannerAdapter
    }

    private fun setupCategoryRecyclerView() {
        val categoryAdapter = CategoryAdapter(applicationContext)
        categoryView.isNestedScrollingEnabled = false
        (categoryView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        categoryAdapter.submitList(getFakeCategory())
        categoryView.adapter = categoryAdapter
    }

    private fun setupPizzaRecyclerView() {
        pizzaAdapter = PizzaAdapter()
        rvBreakingNews.isNestedScrollingEnabled = false
        rvBreakingNews.adapter = pizzaAdapter
        rvBreakingNews.addOnScrollListener(PaginationScrollListener(viewModel::getNewPizza))
    }

    private fun hideProgressBar(paginationProgressBar: ProgressBar) {
        paginationProgressBar.visibility = View.INVISIBLE
        paginationScrollListener.isLoading = false
    }

    private fun showProgressBar(paginationProgressBar: ProgressBar) {
        paginationProgressBar.visibility = View.VISIBLE
        paginationScrollListener.isLoading = true
    }

  private fun getFakeBanners() =
        listOf(
            R.color.purple_200,
            R.color.gray,
            R.color.purple_700,
            R.color.teal_700,
            R.color.teal_200,
            R.color.active_tab_color,
        )

    private fun getFakeCategory() =
        mutableListOf(
            CategoryItem("Пицца", true),
            CategoryItem("Комбо", false),
            CategoryItem("Закуски", false),
            CategoryItem("Десерты",false),
            CategoryItem("Напитки",false),
            CategoryItem("Соусы",false),
            CategoryItem("Другие товары",false)
        )
}