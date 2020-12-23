package com.github.htdangkhoa.nexterp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.databinding.StockcountItemViewHolderBinding
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.github.htdangkhoa.nexterp.ui.viewholders.StockListViewHolder


class StockListAdapter(context: Context?, viewModel: StockCountViewModel, comparator: Comparator<StockCountResponse.StockCount>) :
    RecyclerView.Adapter<StockListViewHolder>() {
    private val mSortedList: SortedList<StockCountResponse.StockCount> =
        SortedList(StockCountResponse.StockCount::class.java, object : SortedList.Callback<StockCountResponse.StockCount>() {
            override fun compare(a: StockCountResponse.StockCount, b: StockCountResponse.StockCount): Int {
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

            override fun areContentsTheSame(oldItem: StockCountResponse.StockCount, newItem: StockCountResponse.StockCount): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: StockCountResponse.StockCount, item2: StockCountResponse.StockCount): Boolean {
                return item1.id == item2.id
            }
        })

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mViewModel: StockCountViewModel = viewModel
    private val mComparator: Comparator<StockCountResponse.StockCount> = comparator

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockListViewHolder {
        val binding: StockcountItemViewHolderBinding = StockcountItemViewHolderBinding.inflate(
            mInflater,
            parent,
            false
        )
        return StockListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockListViewHolder, position: Int) {
        val model: StockCountResponse.StockCount
                = mSortedList[position]
        holder.bind(model, mViewModel)
    }

    override fun getItemCount(): Int {
        return mSortedList.size()
    }
    fun add(model: StockCountResponse.StockCount?) {
        mSortedList.add(model)
    }

    fun remove(model: StockCountResponse.StockCount?) {
        mSortedList.remove(model)
    }

    fun add(models: Array<StockCountResponse.StockCount>) {
        models.forEach {
            mSortedList.add(it)
        }
    }

    fun remove(models: List<StockCountResponse.StockCount?>) {
        mSortedList.beginBatchedUpdates()
        for (model in models) {
            mSortedList.remove(model)
        }
        mSortedList.endBatchedUpdates()
    }
    fun replaceAll(models: List<StockCountResponse.StockCount?>) {
        mSortedList.beginBatchedUpdates()
        for (i in mSortedList.size() - 1 downTo 0) {
            val model: StockCountResponse.StockCount = mSortedList[i]
            if (!models.contains(model)) {
                mSortedList.remove(model)
            }
        }
        mSortedList.addAll(models)
        mSortedList.endBatchedUpdates()
    }

}
