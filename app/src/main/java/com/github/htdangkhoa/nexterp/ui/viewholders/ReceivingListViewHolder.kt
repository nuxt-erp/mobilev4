package com.github.htdangkhoa.nexterp.ui.viewholders
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.data.remote.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.databinding.ReceivingItemViewHolderBinding
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel


class ReceivingListViewHolder(binding: ReceivingItemViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    private val mBinding: ReceivingItemViewHolderBinding = binding
    fun bind(item: ReceivingResponse.Receiving?, viewModel: ReceivingViewModel) {
        mBinding.receiving = item
        mBinding.viewModel = viewModel
    }
}