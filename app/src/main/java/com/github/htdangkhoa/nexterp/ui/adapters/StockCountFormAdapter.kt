package com.github.htdangkhoa.nexterp.ui.adapters

import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.StringUtils.getString
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.data.remote.product.ProductResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.ui.utils.inflate
import kotlinx.android.synthetic.main.receiving_details_item.view.*
import kotlinx.android.synthetic.main.receiving_details_item.view.detailId
import kotlinx.android.synthetic.main.receiving_details_item.view.productName
import kotlinx.android.synthetic.main.stockcount_details_item.view.*
import java.util.*


class StockCountRecyclerAdapter(private val list: List<StockCountDetailResponse.StockCountDetail>, val callback: (StockCountDetailResponse.StockCountDetail) -> Unit) : RecyclerView.Adapter<StockCountRecyclerAdapter.ListHolder>() {
    private var updateList : MutableList<StockCountDetailResponse.StockCountDetail> = list as MutableList<StockCountDetailResponse.StockCountDetail>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflatedView = parent.inflate(R.layout.stockcount_details_item, false)
        return ListHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: StockCountRecyclerAdapter.ListHolder, position: Int) {

        if (position == 0) {
            holder.bindHeader()
        } else {
            val item = list[position - 1]
            holder.bindItem(item)
            holder.itemView.setOnClickListener { callback(item) }
        }

    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    fun getUpdateList(): List<StockCountDetailResponse.StockCountDetail> {
        return updateList.toList()
    }

    fun updateQty(productId : Int, text: String) {
        updateList.forEach {
            if(it.product_id == productId) {
                val previousQty = it.qty
                it.qty = try {
                    text.toInt()
                } catch (e: NumberFormatException) {
                    previousQty
                }
            }
        }
    }

    fun checkProductAndUpdate(searchable : String): StockCountDetailResponse.StockCountDetail? {
        if(searchable.isEmpty().not()) {
            updateList.forEach {
                if(it.searchable.trim().toLowerCase(Locale.ROOT) == searchable.trim().toLowerCase(Locale.ROOT)) {
                    it.qty = it.qty + 1
                    return it
                }
            }
        }
        return null
    }

    fun updateList(result: Array<ProductResponse.Product>) {
        for (item in result) {
            var found = false
            for (detail in updateList) {
                if (item.id == detail.product_id) {
                    found = true
                    detail.qty += 1
                }
            }
            if (!found) {
//                val stockCountDetail = StockCountDetailResponse.StockCountDetail(
//                    id = null,
//                    product_id = item.id,
//                    product_name = item.name,
//                    searchable = item.searchable
//                )
//                updateList.add(stockCountDetail)
            }
        }

    }
    class ListHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var item: StockCountDetailResponse.StockCountDetail? = null

        init {
            v.setOnClickListener(this)
        }

        private fun setHeaderBg(view: View) {
            view.setBackgroundResource(R.drawable.table_header_bg)
        }

        private fun setContentBg(view: View) {
            view.setBackgroundResource(R.drawable.table_content_bg)
        }

        private fun setDisable(view: View) {
            view.isActivated = false
            view.isFocusable = false
            view.isClickable = false
        }

        fun bindHeader() {
            setHeaderBg(view.detailId)
            setHeaderBg(view.productName)
            setHeaderBg(view.binName)
            setHeaderBg(view.stockQty)

            view.detailId.text = getString(R.string.receiving_header_id)
            view.productName.text =  getString(R.string.receiving_header_product)
            view.binName.text =  getString(R.string.stockcount_header_bin)
            view.stockQty.text =  getString(R.string.receiving_header_qty)

            view.detailId.setTypeface(null, Typeface.BOLD)
            view.productName.setTypeface(null, Typeface.BOLD)
            view.binName.setTypeface(null, Typeface.BOLD)
            view.stockQty.setTypeface(null, Typeface.BOLD)

        }

        fun bindItem(item: StockCountDetailResponse.StockCountDetail) {
            this.item = item

            setContentBg(view.detailId)
            setContentBg(view.productName)
            setContentBg(view.binName)
            setContentBg(view.stockQty)

            view.detailId.text = item.id.toString()
            view.productName.text = item.product_name
            view.stockQty.text = item.qty.toString()
            view.binName.text = item.bin_name

        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }

    }

}


