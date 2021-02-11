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
import kotlinx.android.synthetic.main.receiving_details_item.view.btnDeleteDetail
import kotlinx.android.synthetic.main.receiving_details_item.view.header
import kotlinx.android.synthetic.main.receiving_details_item.view.productName
import kotlinx.android.synthetic.main.stockcount_details_item.view.*
import java.util.*


class ReceivingRecyclerAdapter(
    private val list: List<ReceivingDetailsResponse.ReceivingDetails?>,
    val callback: (ReceivingDetailsResponse.ReceivingDetails) -> Unit,
    val callback2: (Int) -> Unit) : RecyclerView.Adapter<ReceivingRecyclerAdapter.ListHolder>() {
    private var updateList : MutableList<ReceivingDetailsResponse.ReceivingDetails?> = list as MutableList<ReceivingDetailsResponse.ReceivingDetails?>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflatedView = parent.inflate(R.layout.receiving_details_item, false)
        return ListHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ReceivingRecyclerAdapter.ListHolder, position: Int) {

        if (position == 0) {
            holder.bindHeader()
        } else {
            val item = list[position - 1]
            holder.bindItem(item!!)
            holder.itemView.setOnClickListener { callback(item) }
            holder.itemView.btnDeleteDetail.setOnClickListener { callback2(position) }
        }

    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    fun getUpdateList(): List<ReceivingDetailsResponse.ReceivingDetails?> {
        return updateList.toList()
    }

    fun updateQty(productId : Int, text: String) {
        updateList.forEach {
            if(it!!.product_id == productId) {
                val previousQty = it.qty_received
                it.qty_received = try {
                    text.toInt()
                } catch (e: NumberFormatException) {
                    previousQty
                }
            }
        }
        notifyDataSetChanged()
    }

    fun checkProductAndUpdate(searchable : String): ReceivingDetailsResponse.ReceivingDetails? {
        if(searchable.isEmpty().not()) {
            updateList.forEach {
                if(it!!.searchable.trim().toLowerCase(Locale.ROOT) == searchable.trim().toLowerCase(Locale.ROOT) || it.product_sku.trim().toLowerCase(Locale.ROOT) == searchable.trim().toLowerCase(Locale.ROOT)) {
                    it.qty_received = it.qty_received + 1
                    notifyDataSetChanged()
                    return it
                }
            }
        }
        return null
    }

    fun removeAt(position: Int): Int? {
        val id : Int? = updateList.elementAt((position - 1))!!.id
        updateList.removeAt(position - 1)
        notifyItemRemoved(position)
        return id
    }

    fun findById(id: Int): ReceivingDetailsResponse.ReceivingDetails? {
        updateList.forEach {
            if(it!!.product_id == id) {
                return it
            }
        }
        return null
    }

    fun updateList(result: Array<ProductResponse.Product>) {
        for (item in result) {
            var found = false
            for (detail in updateList) {
                if (item.id == detail!!.product_id) {
                    found = true
                    detail.qty_received += 1
                }
            }
            if (!found) {
                val receivingDetail = ReceivingDetailsResponse.ReceivingDetails(
                    id = null,
                    product_id = item.id,
                    product_name = item.name,
                    product_full_name = item.product_full_name,
                    product_sku = item.sku,
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


        private fun setDisable(view: View) {
            view.isActivated = false
            view.isFocusable = false
            view.isClickable = false
        }
        fun bindHeader() {
            view.productName.visibility = View.GONE
            view.receivingQtyReceived.visibility = View.GONE
            view.receivingQtyReceivedLabel.visibility = View.GONE
            view.btnDeleteDetail.visibility = View.GONE
            view.header.visibility = View.VISIBLE
            view.header.text = "Receiving Details"
        }

        fun bindItem(item: ReceivingDetailsResponse.ReceivingDetails) {
            this.item = item

            view.receivingQtyReceivedLabel.text = " ITEM(S)"

            view.productName.text = item.product_full_name
            view.receivingQtyReceived.text = item.qty_received.toString()

        }

        override fun onClick(p0: View?) {
            Log.e("TEST", "TEST")
        }

    }

}


