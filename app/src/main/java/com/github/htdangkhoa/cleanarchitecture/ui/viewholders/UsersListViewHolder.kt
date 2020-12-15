package com.github.htdangkhoa.cleanarchitecture.ui.viewholders
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.UsersResponse
import com.github.htdangkhoa.cleanarchitecture.databinding.UserItemViewHolderBinding
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainViewModel


class UsersListViewHolder(binding: UserItemViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    private val mBinding: UserItemViewHolderBinding = binding
    fun bind(item: UsersResponse.User?, viewModel: MainViewModel) {
        mBinding.user = item
        mBinding.viewModel = viewModel
    }
}