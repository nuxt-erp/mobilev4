package com.github.htdangkhoa.nexterp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.databinding.ReceivingTransferItemViewHolderBinding
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_transfers.ReceivingTransferViewModel
import com.github.htdangkhoa.nexterp.ui.viewholders.ReceivingListViewHolder
import com.github.htdangkhoa.nexterp.ui.viewholders.ReceivingTransferListViewHolder


class ReceivingTransferListAdapter(context: Context?, viewModel: ReceivingTransferViewModel, comparator: Comparator<ReceivingResponse.Receiving>) :
    RecyclerView.Adapter<ReceivingTransferListViewHolder>() {
    private val mSortedList: SortedList<ReceivingResponse.Receiving> =
        SortedList(ReceivingResponse.Receiving::class.java, object : SortedList.Callback<ReceivingResponse.Receiving>() {
            override fun compare(a: ReceivingResponse.Receiving, b: ReceivingResponse.Receiving): Int {
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

            override fun areContentsTheSame(oldItem: ReceivingResponse.Receiving, newItem: ReceivingResponse.Receiving): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: ReceivingResponse.Receiving, item2: ReceivingResponse.Receiving): Boolean {
                return item1.id == item2.id
            }
        })

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mViewModel: ReceivingTransferViewModel = viewModel
    private val mComparator: Comparator<ReceivingResponse.Receiving> = comparator

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceivingTransferListViewHolder {
        val binding: ReceivingTransferItemViewHolderBinding = ReceivingTransferItemViewHolderBinding.inflate(
            mInflater,
            parent,
            false
        )
        return ReceivingTransferListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceivingTransferListViewHolder, position: Int) {
        val model: ReceivingResponse.Receiving = mSortedList[position]
        holder.bind(model, mViewModel)
    }

    override fun getItemCount(): Int {
        return mSortedList.size()
    }
    fun add(model: ReceivingResponse.Receiving?) {
        mSortedList.add(model)
    }

    fun remove(model: ReceivingResponse.Receiving?) {
        mSortedList.remove(model)
    }

    fun add(models: Array<ReceivingResponse.Receiving>) {
        models.forEach {
            mSortedList.add(it)
        }
    }

    fun remove(models: List<ReceivingResponse.Receiving?>) {
        mSortedList.beginBatchedUpdates()
        for (model in models) {
            mSortedList.remove(model)
        }
        mSortedList.endBatchedUpdates()
    }
    fun replaceAll(models: List<ReceivingResponse.Receiving?>) {
        mSortedList.beginBatchedUpdates()
        for (i in mSortedList.size() - 1 downTo 0) {
            val model: ReceivingResponse.Receiving = mSortedList[i]
            if (!models.contains(model)) {
                mSortedList.remove(model)
            }
        }
        mSortedList.addAll(models)
        mSortedList.endBatchedUpdates()
    }

}
