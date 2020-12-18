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
import com.github.htdangkhoa.nexterp.ui.utils.inflate
import kotlinx.android.synthetic.main.receiving_details_item.view.*
import java.util.*


class ReceivingRecyclerAdapter(private val list: List<ReceivingDetailsResponse.ReceivingDetails>, val callback: (ReceivingDetailsResponse.ReceivingDetails) -> Unit) : RecyclerView.Adapter<ReceivingRecyclerAdapter.ListHolder>() {
    private var updateList : MutableList<ReceivingDetailsResponse.ReceivingDetails> = list as MutableList<ReceivingDetailsResponse.ReceivingDetails>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflatedView = parent.inflate(R.layout.receiving_details_item, false)
        return ListHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ReceivingRecyclerAdapter.ListHolder, position: Int) {

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

    fun getUpdateList(): List<ReceivingDetailsResponse.ReceivingDetails> {
        return updateList.toList()
    }

    fun updateQty(productId : Int, text: String) {
        if(productId != null) {
            updateList.forEach {
                if(it.product_id == productId) {
                    val previousQty = it.qty_received
                    it.qty_received = try {
                        text.toInt()
                    } catch (e: NumberFormatException) {
                        previousQty
                    }
                }
            }
        }
    }

    fun checkProductAndUpdate(searchable : String): ReceivingDetailsResponse.ReceivingDetails? {
        if(searchable.isEmpty().not()) {
            updateList.forEach {
                if(it.searchable.trim().toLowerCase(Locale.ROOT) == searchable.trim().toLowerCase(Locale.ROOT)) {
                    it.qty_received = it.qty_received + 1
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
                    detail.qty_received += 1
                }
            }
            if (!found) {
                val receivingDetail = ReceivingDetailsResponse.ReceivingDetails(
                    id = null,
                    product_id = item.id,
                    product_name = item.name,
                    qty_allocated = 0,
                    qty_received = 0,
                    searchable = item.searchable
                )
                updateList.add(receivingDetail)
            }
        }

    }
    class ListHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var item: ReceivingDetailsResponse.ReceivingDetails? = null

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
            setHeaderBg(view.receivingQty)
            setHeaderBg(view.receivingQtyReceived)
            setDisable(view.receivingQtyReceived)

            view.detailId.text = getString(R.string.receiving_header_id)
            view.productName.text =  getString(R.string.receiving_header_product)
            view.receivingQty.text =  getString(R.string.receiving_header_qty)
            view.receivingQtyReceived.text = getString(R.string.receiving_header_qty_received)

            view.detailId.setTypeface(null, Typeface.BOLD)
            view.productName.setTypeface(null, Typeface.BOLD)
            view.receivingQty.setTypeface(null, Typeface.BOLD)
            view.receivingQtyReceived.setTypeface(null, Typeface.BOLD)
        }

        fun bindItem(item: ReceivingDetailsResponse.ReceivingDetails) {
            this.item = item

            setContentBg(view.detailId)
            setContentBg(view.productName)
            setContentBg(view.receivingQty)
            setContentBg(view.receivingQtyReceived)

            view.detailId.text = item.id.toString()
            view.productName.text = item.product_name
            view.receivingQty.text = item.qty_allocated.toString()
            view.receivingQtyReceived.text = item.qty_received.toString()

        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }

    }

}


