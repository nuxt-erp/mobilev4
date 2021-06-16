package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.pagination.PaginationObject
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.StockListAdapter
import com.github.htdangkhoa.nexterp.ui.components.PaginationListener
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stockcount_form.*
import kotlinx.android.synthetic.main.fragment_stockcount_list.*
import kotlinx.android.synthetic.main.fragment_stockcount_list.progressCircular

class StockCountListFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class) {

    override val layoutResID: Int
        get() = R.layout.fragment_stockcount_list

    private val alphabeticalComparator: Comparator<StockCountResponse.StockCount> =
        Comparator { a, b -> a.id.compareTo(b.id) }

    // Index from which pagination should start (0 is 1st page in our case)
    private val start = 1

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private var totalPages = 1

    // indicates the current page which Pagination is fetching.
    private var currentPage = start

    private var nextPage = start

    private var lastPage = start

    override fun render(view: View, savedInstanceState: Bundle?) {
        val mAdapter = StockListAdapter(
            this.context,
            viewModel,
            alphabeticalComparator
        )
        val linearLayoutManager = LinearLayoutManager(this.context)

        stockList.layoutManager = linearLayoutManager
        stockList.adapter = mAdapter

        stockList.addOnScrollListener(object : PaginationListener(linearLayoutManager) {
            override fun loadMoreItems() {
                Log.e("LOAD MORE->>>", "FRAGMENT")
                //  API call
                if (currentPage != lastPage) {
                    viewModel.getStockCount(nextPage)
                }
            }
            val totalPageCount: Int
                get() = totalPages
            override val isLastPage: Boolean = false
            override val isLoading: Boolean = false
        })


        viewModel.resourceStockCount.observe(viewLifecycleOwner, object : ObserverResource<Pair<Array<StockCountResponse.StockCount>, PaginationObject?>>() {
            override fun onSuccess(data: Pair<Array<StockCountResponse.StockCount>, PaginationObject?>) {
                val (dataArray, pagination) = data
                totalPages = pagination!!.total
                currentPage = pagination.current_page
                lastPage = pagination.last_page
                if(currentPage + 1 <= pagination.last_page) {
                    nextPage = currentPage + 1
                }
                mAdapter.add(dataArray)
                mAdapter.notifyDataSetChanged()
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let { toast(it) }
                    handleHttpError(it)
                }
            }

            override fun onLoading(isShow: Boolean) {
                progressCircular.apply {
                    if (isShow) show() else hide(true)
                }
            }

        })
        viewModel.getStockCount(currentPage)

        newButton.setOnClickListener {
            val action = StockCountListFragmentDirections.actionNavStockCountToStockCountNewFragment()
            view.findNavController().navigate(action)
        }
    }
}