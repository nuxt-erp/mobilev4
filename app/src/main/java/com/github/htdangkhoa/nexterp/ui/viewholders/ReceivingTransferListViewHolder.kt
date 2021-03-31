package com.github.htdangkhoa.nexterp.ui.viewholders
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.databinding.ReceivingTransferItemViewHolderBinding
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_transfers.ReceivingTransferViewModel


class ReceivingTransferListViewHolder(binding: ReceivingTransferItemViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    private val mBinding: ReceivingTransferItemViewHolderBinding = binding
    fun bind(item: ReceivingResponse.Receiving?, viewModel: ReceivingTransferViewModel) {
        mBinding.receiving = item
        mBinding.viewModel = viewModel
    }
}