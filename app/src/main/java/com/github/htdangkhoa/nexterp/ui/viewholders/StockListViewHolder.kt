package com.github.htdangkhoa.nexterp.ui.viewholders
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.databinding.StockcountItemViewHolderBinding
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel


class StockListViewHolder(binding: StockcountItemViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    private val mBinding: StockcountItemViewHolderBinding = binding
    fun bind(item: StockCountResponse.StockCount?, viewModel: StockCountViewModel) {
        mBinding.stockitem = item
        mBinding.viewModel = viewModel
    }
}