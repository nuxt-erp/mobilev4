package com.github.htdangkhoa.cleanarchitecture.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.UsersResponse
import com.github.htdangkhoa.cleanarchitecture.databinding.UserItemViewHolderBinding
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainViewModel
import com.github.htdangkhoa.cleanarchitecture.ui.viewholders.UsersListViewHolder


class UsersListAdapter(context: Context?, viewModel: MainViewModel, comparator: Comparator<UsersResponse.User>) :
    RecyclerView.Adapter<UsersListViewHolder>() {
    private val mSortedList: SortedList<UsersResponse.User> =
        SortedList(UsersResponse.User::class.java, object : SortedList.Callback<UsersResponse.User>() {
            override fun compare(a: UsersResponse.User, b: UsersResponse.User): Int {
                return mComparator.compare(a, b)
            }

            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onChanged(position: Int, count: Int) {
                notifyItemRangeChanged(position, count)
            }

            override fun areContentsTheSame(oldItem: UsersResponse.User, newItem: UsersResponse.User): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: UsersResponse.User, item2: UsersResponse.User): Boolean {
                return item1.id === item2.id
            }
        })

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mViewModel: MainViewModel = viewModel
    private val mComparator: Comparator<UsersResponse.User> = comparator

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val binding: UserItemViewHolderBinding = UserItemViewHolderBinding.inflate(
            mInflater,
            parent,
            false
        )
        return UsersListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val model: UsersResponse.User = mSortedList[position]
        holder.bind(model, mViewModel)
    }

    override fun getItemCount(): Int {
        return mSortedList.size()
    }
    fun add(model: UsersResponse.User?) {
        mSortedList.add(model)
    }

    fun remove(model: UsersResponse.User?) {
        mSortedList.remove(model)
    }

    fun add(models: Array<UsersResponse.User>) {
        models.forEach {
            mSortedList.add(it)
        }
    }

    fun remove(models: List<UsersResponse.User?>) {
        mSortedList.beginBatchedUpdates()
        for (model in models) {
            mSortedList.remove(model)
        }
        mSortedList.endBatchedUpdates()
    }
    fun replaceAll(models: List<UsersResponse.User?>) {
        mSortedList.beginBatchedUpdates()
        for (i in mSortedList.size() - 1 downTo 0) {
            val model: UsersResponse.User = mSortedList[i]
            if (!models.contains(model)) {
                mSortedList.remove(model)
            }
        }
        mSortedList.addAll(models)
        mSortedList.endBatchedUpdates()
    }

}
