package com.github.htdangkhoa.nexterp.ui.components

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener
/**
 * Supporting only LinearLayoutManager for now.
 */(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        super.onScrolled(recyclerView, dx, dy)
//        val visibleItemCount = layoutManager.childCount
//        val totalItemCount = layoutManager.itemCount
//        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//        val currentItemCount = visibleItemCount + firstVisibleItemPosition
//        Log.e("dx", dx.toString())
//        Log.e("dy", dy.toString())
//
//        if (!isLastPage && (currentItemCount >= totalItemCount) && (firstVisibleItemPosition >= 0) && (totalItemCount >= PAGE_SIZE)) {
//                Log.e("visibleItemCount", (visibleItemCount + firstVisibleItemPosition).toString())
//                Log.e("totalItemCount", totalItemCount.toString())
//                Log.e("1stVisibleItemPosition", totalItemCount.toString())
//                Log.e("PAGE_SIZE", PAGE_SIZE.toString())
//        }
//    }
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager.itemCount
        val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
        Log.e("dx", dx.toString())
        Log.e("dy", dy.toString())
        if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                // do your stuff here
                Log.e("loadMore", true.toString())
                loadMoreItems()
        }

    }
    protected abstract fun loadMoreItems()
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    companion object {
        const val PAGE_START = 1

        /**
         * Set scrolling threshold here (for now i'm assuming 10 item in one page)
         */
        private const val PAGE_SIZE = 20
    }
}