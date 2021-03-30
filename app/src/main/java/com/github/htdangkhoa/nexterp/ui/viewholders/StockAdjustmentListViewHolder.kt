package com.github.htdangkhoa.nexterp.ui.viewholders
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.databinding.StockadjustmentItemViewHolderBinding
import com.github.htdangkhoa.nexterp.databinding.StockcountItemViewHolderBinding
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.StockAdjustmentViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel


class StockAdjustmentListViewHolder(binding: StockadjustmentItemViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    private val mBinding: StockadjustmentItemViewHolderBinding = binding
    fun bind(item: StockAdjustmentResponse.StockAdjustment?, viewModel: StockAdjustmentViewModel) {
        mBinding.stockadjustment = item
        mBinding.viewModel = viewModel
    }
}