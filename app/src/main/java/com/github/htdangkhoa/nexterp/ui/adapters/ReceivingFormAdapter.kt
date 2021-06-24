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

    fun updateQty(productId : Int, qty: Int) {
        updateList.forEach {
            if(it!!.product_id == productId) {
                it.qty_received = qty
            }
        }
        notifyDataSetChanged()
    }

    fun checkProductAndUpdate(searchable : String, multiplier: Int): ReceivingDetailsResponse.ReceivingDetails? {
        if(searchable.isEmpty().not()) {
            updateList.forEach {
                when {
                    it!!.product_sku.trim().equals(searchable.trim(), ignoreCase = true) -> {
                        it.qty_received = it.qty_received + multiplier
                        notifyDataSetChanged()
                        return it
                    }
                    it.product_barcode?.trim().equals(searchable.trim(), ignoreCase = true) -> {
                        it.qty_received = it.qty_received + multiplier
                        notifyDataSetChanged()
                        return it
                    }
                    it.product_carton_barcode?.trim().equals(searchable.trim(), ignoreCase = true) -> {
                        if(it.product_carton_qty != null) {
                            it.qty_received = it.qty_received + it.product_carton_qty!!
                            notifyDataSetChanged()
                            return it
                        }
                    }
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

    fun updateList(result: Array<ProductResponse.Product>, searchable: String) {
        for (item in result) {
            var found = false
            for (detail in updateList) {
                if (detail != null) {
                    when {
                        item.barcode == detail.product_barcode && item.barcode == searchable -> {
                            found = true
                            detail.qty_received += 1
                        }
                        item.sku == detail.product_sku && item.sku == searchable -> {
                            found = true
                            detail.qty_received += 1
                        }
                        item.carton_barcode == detail.product_carton_barcode && item.carton_barcode == searchable -> {
                            found = true
                            if(item.carton_qty !== null) {
                                detail.qty_received += item.carton_qty!!
                            }
                        }
                    }
                }
            }
            if (!found) {
                val receivingDetail = ReceivingDetailsResponse.ReceivingDetails(
                    id = null,
                    product_id = item.id,
                    product_name = item.name,
                    product_full_name = item.product_full_name,
                    product_carton_barcode = item.carton_barcode,
                    product_carton_qty = item.carton_qty,
                    product_sku = item.sku,
                    product_barcode = item.barcode,
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


