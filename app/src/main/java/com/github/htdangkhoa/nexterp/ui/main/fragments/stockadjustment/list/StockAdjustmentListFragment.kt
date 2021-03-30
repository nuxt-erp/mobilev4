package com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.list

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.StockAdjustmentListAdapter
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.StockAdjustmentViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stock_adjustment_list.*

class StockAdjustmentListFragment() : BaseFragment<StockAdjustmentViewModel>(
    StockAdjustmentViewModel::class) {

    override val layoutResID: Int
        get() = R.layout.fragment_stock_adjustment_list

    private val alphabeticalComparator: Comparator<StockAdjustmentResponse.StockAdjustment> =
        Comparator { a, b -> a.id.compareTo(b.id) }

    override fun render(view: View, savedInstanceState: Bundle?) {
        val mAdapter = StockAdjustmentListAdapter(
            this.context,
            viewModel,
            alphabeticalComparator
        )
        val linearLayoutManager = LinearLayoutManager(this.context)

        stockAdjustmentList.layoutManager = linearLayoutManager
        stockAdjustmentList.adapter = mAdapter

        viewModel.resourceStockAdjustment.observe(viewLifecycleOwner, object : ObserverResource<Array<StockAdjustmentResponse.StockAdjustment>>() {
            override fun onSuccess(data: Array<StockAdjustmentResponse.StockAdjustment>) {
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
        viewModel.getStockAdjustment()

        newButton.setOnClickListener {
            val action = StockAdjustmentListFragmentDirections.actionNavStockAdjustmentToStockAdjustmentNewFragment()
            view.findNavController().navigate(action)
        }
    }
}