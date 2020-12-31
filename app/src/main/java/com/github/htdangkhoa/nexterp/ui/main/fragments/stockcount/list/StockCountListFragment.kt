package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.StockListAdapter
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stockcount_list.*

class StockCountListFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class) {

    override val layoutResID: Int
        get() = R.layout.fragment_stockcount_list

    private val alphabeticalComparator: Comparator<StockCountResponse.StockCount> =
        Comparator { a, b -> a.id.compareTo(b.id) }

    override fun render(view: View, savedInstanceState: Bundle?) {
        val mAdapter = StockListAdapter(
            this.context,
            viewModel,
            alphabeticalComparator
        )
        val linearLayoutManager = LinearLayoutManager(this.context)

        stockList.layoutManager = linearLayoutManager
        stockList.adapter = mAdapter

        viewModel.resourceStockCount.observe(viewLifecycleOwner, object : ObserverResource<Array<StockCountResponse.StockCount>>() {
            override fun onSuccess(data: Array<StockCountResponse.StockCount>) {
                mAdapter.add(data)
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
        viewModel.getStockCount()

        newButton.setOnClickListener {

            val action = StockCountListFragmentDirections.actionNavStockCountToStockCountNewFragment()
            view.findNavController().navigate(action)
        }
    }
}