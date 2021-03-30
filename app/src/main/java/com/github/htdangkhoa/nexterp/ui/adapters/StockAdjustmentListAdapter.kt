package com.github.htdangkhoa.nexterp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse
import com.github.htdangkhoa.nexterp.databinding.StockadjustmentItemViewHolderBinding
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.StockAdjustmentViewModel
import com.github.htdangkhoa.nexterp.ui.viewholders.StockAdjustmentListViewHolder


class StockAdjustmentListAdapter(context: Context?, viewModel: StockAdjustmentViewModel, comparator: Comparator<StockAdjustmentResponse.StockAdjustment>) :
    RecyclerView.Adapter<StockAdjustmentListViewHolder>() {
    private val mSortedList: SortedList<StockAdjustmentResponse.StockAdjustment> =
        SortedList(StockAdjustmentResponse.StockAdjustment::class.java, object : SortedList.Callback<StockAdjustmentResponse.StockAdjustment>() {
            override fun compare(a: StockAdjustmentResponse.StockAdjustment, b: StockAdjustmentResponse.StockAdjustment): Int {
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

            override fun areContentsTheSame(oldItem: StockAdjustmentResponse.StockAdjustment, newItem: StockAdjustmentResponse.StockAdjustment): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: StockAdjustmentResponse.StockAdjustment, item2: StockAdjustmentResponse.StockAdjustment): Boolean {
                return item1.id == item2.id
            }
        })

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mViewModel: StockAdjustmentViewModel = viewModel
    private val mComparator: Comparator<StockAdjustmentResponse.StockAdjustment> = comparator

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockAdjustmentListViewHolder {
        val binding: StockadjustmentItemViewHolderBinding = StockadjustmentItemViewHolderBinding.inflate(
            mInflater,
            parent,
            false
        )
        return StockAdjustmentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockAdjustmentListViewHolder, position: Int) {
        val model: StockAdjustmentResponse.StockAdjustment
                = mSortedList[position]
        holder.bind(model, mViewModel)
    }

    override fun getItemCount(): Int {
        return mSortedList.size()
    }
    fun add(model: StockAdjustmentResponse.StockAdjustment?) {
        mSortedList.add(model)
    }

    fun remove(model: StockAdjustmentResponse.StockAdjustment?) {
        mSortedList.remove(model)
    }

    fun add(models: Array<StockAdjustmentResponse.StockAdjustment>) {
        models.forEach {
            mSortedList.add(it)
        }
    }

    fun remove(models: List<StockAdjustmentResponse.StockAdjustment?>) {
        mSortedList.beginBatchedUpdates()
        for (model in models) {
            mSortedList.remove(model)
        }
        mSortedList.endBatchedUpdates()
    }
    fun replaceAll(models: List<StockAdjustmentResponse.StockAdjustment?>) {
        mSortedList.beginBatchedUpdates()
        for (i in mSortedList.size() - 1 downTo 0) {
            val model: StockAdjustmentResponse.StockAdjustment = mSortedList[i]
            if (!models.contains(model)) {
                mSortedList.remove(model)
            }
        }
        mSortedList.addAll(models)
        mSortedList.endBatchedUpdates()
    }

}
