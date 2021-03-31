package com.github.htdangkhoa.nexterp.ui.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.StockAdjustmentDetailResponse
import com.github.htdangkhoa.nexterp.ui.utils.inflate
import kotlinx.android.synthetic.main.receiving_details_item.view.productName
import kotlinx.android.synthetic.main.stockadjustment_details_item.view.*
import java.util.*


class StockAdjustmentRecyclerAdapter(
    private val list: List<StockAdjustmentDetailResponse.StockAdjustmentDetail>,
    private val adjustment_type: String,
    val callback: (StockAdjustmentDetailResponse.StockAdjustmentDetail) -> Unit,
    val callback2: (Int) -> Unit
) : RecyclerView.Adapter<StockAdjustmentRecyclerAdapter.ListHolder>() {
    private var updateList : MutableList<StockAdjustmentDetailResponse.StockAdjustmentDetail> = list as MutableList<StockAdjustmentDetailResponse.StockAdjustmentDetail>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflatedView = parent.inflate(R.layout.stockadjustment_details_item, false)
        return ListHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: StockAdjustmentRecyclerAdapter.ListHolder, position: Int) {
        if (position > 0) {
            val myPosition = position - 1
            val item = list[myPosition]
            holder.bindItem(item)
            holder.itemView.setOnClickListener { callback(item) }
            holder.itemView.btnDeleteDetail.setOnClickListener { callback2(position) }
        } else {
            holder.bindHeader()
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    fun findById(id: Int, binId: Int?): StockAdjustmentDetailResponse.StockAdjustmentDetail? {
        updateList.forEach {
            if(it!!.product_id == id && it!!.bin_id == binId) {
                return it
            }
        }
        return null
    }

    fun getUpdateList(): List<StockAdjustmentDetailResponse.StockAdjustmentDetail> {
        return updateList.toList()
    }
    fun checkProductAndUpdate(searchable: String, binId: Int?): StockAdjustmentDetailResponse.StockAdjustmentDetail? {
        if(searchable.isEmpty().not()) {
            updateList.forEach {
                if(it.searchable.isNullOrEmpty().not()) {
                    if ((it.product_barcode?.trim()?.toLowerCase(Locale.ROOT) == searchable.trim().toLowerCase(Locale.ROOT)) && it.bin_id == binId) {
                        it.qty = it.qty + 1
                        notifyDataSetChanged()
                        return it
                    } else if ((it.product_sku?.trim()?.toLowerCase(Locale.ROOT) == searchable.trim().toLowerCase(Locale.ROOT)) && it.bin_id == binId) {
                        it.qty = it.qty + 1
                        notifyDataSetChanged()
                        return it
                    } else if((it.product_carton_barcode?.trim()?.toLowerCase(Locale.ROOT) == searchable.trim().toLowerCase(Locale.ROOT))  && it.bin_id == binId){
                        it.qty = it.qty + it.product_carton_qty!!
                        notifyDataSetChanged()
                        return it
                    }
                }
            }
        }
        return null
    }
    fun updateListWithObj(item: ProductAvailabilityResponse.ProductAvailability, searchable: String, binId: Int?) {
        var found = false

        for (detail in updateList) {
            if (item.product_sku != null &&
                item.product_sku == detail.product_sku && item.bin_id == detail.bin_id &&
                item.bin_id == binId && item.product_barcode  == searchable.trim().toLowerCase(Locale.ROOT)) {
                found = true
                detail.qty += 1
            } else if (item.product_barcode != null &&
                item.product_barcode == detail.product_barcode && item.bin_id == detail.bin_id &&
                item.bin_id == binId && item.product_barcode  == searchable.trim().toLowerCase(Locale.ROOT)) {
                found = true
                detail.qty += 1
            } else if (item.product_carton_qty != null &&
                item.product_carton_barcode == detail.product_carton_barcode && item.bin_id == detail.bin_id &&
                item.bin_id == binId && item.product_carton_barcode  == searchable.trim().toLowerCase(Locale.ROOT)) {
                found = true
                detail.qty += item.product_carton_qty!!
            }
        }

        if (!found) {
            val stockAdjustmentDetail = StockAdjustmentDetailResponse.StockAdjustmentDetail(
                id = null,
                product_id = item.product_id,
                product_full_name = item.product_full_name,
                product_sku = item.product_sku,
                product_barcode = item.product_barcode,
                product_carton_barcode = item.product_carton_barcode,
                product_carton_qty = item.product_carton_qty,
                searchable = item.searchable,
                location_id = item.location_id,
                bin_name = item.bin_name,
                bin_id = item.bin_id,
                qty = item.qty,
                on_hand = item.on_hand,
                variance =  item.qty - item.on_hand,
                status = "adjusted",
                adjustment_type = adjustment_type,
                bin_searchable = item.bin_searchable
            )
            updateList.add(stockAdjustmentDetail)
        }
    }

    fun updateList(result: Array<ProductAvailabilityResponse.ProductAvailability>, searchable: String, binId: Int?) {
        for (item in result) {
            var found = false

            for (detail in updateList) {
                if (item.product_sku != null &&
                    item.product_sku == detail.product_sku && item.bin_id == detail.bin_id &&
                    item.bin_id == binId && item.product_barcode  == searchable.trim().toLowerCase(
                        Locale.ROOT)) {
                    found = true
                    detail.qty += 1
                } else if (item.product_barcode != null &&
                    item.product_barcode == detail.product_barcode && item.bin_id == detail.bin_id &&
                    item.bin_id == binId && item.product_barcode  == searchable.trim().toLowerCase(Locale.ROOT)) {
                    found = true
                    detail.qty += 1

                } else if (item.product_carton_qty != null &&
                    item.product_carton_barcode == detail.product_carton_barcode && item.bin_id == detail.bin_id &&
                    item.bin_id == binId && item.product_carton_barcode  == searchable.trim().toLowerCase(Locale.ROOT)) {
                    found = true
                    detail.qty += item.product_carton_qty!!
                }
            }

            if (!found) {
                val stockAdjustmentDetail = StockAdjustmentDetailResponse.StockAdjustmentDetail(
                    id = null,
                    product_id = item.product_id,
                    product_full_name = item.product_full_name,
                    product_sku = item.product_sku,
                    product_barcode = item.product_barcode,
                    product_carton_barcode = item.product_carton_barcode,
                    product_carton_qty = item.product_carton_qty,
                    searchable = item.searchable,
                    location_id = item.location_id,
                    bin_name = item.bin_name,
                    bin_id = item.bin_id,
                    qty = item.qty,
                    on_hand = item.on_hand,
                    variance =  item.qty - item.on_hand,
                    status = "adjusted",
                    adjustment_type = adjustment_type,
                    bin_searchable = item.bin_searchable
                )
                updateList.add(stockAdjustmentDetail)
            }
        }
    }
    fun updateQty(productId: Int, binId: Int?, text: String) {
        updateList.forEach {
            if(it.product_id == productId && it.bin_id == binId) {
                val previousQty = it.qty
                it.qty = try {
                    text.toInt()
                } catch (e: NumberFormatException) {
                    previousQty
                }
            }
        }
        notifyDataSetChanged()
    }

    fun removeAt(position: Int): Int? {
        val id : Int? = updateList.elementAt((position - 1))!!.id
        updateList.removeAt(position - 1)
        notifyItemRemoved(position)
        return id
    }

    class ListHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var item: StockAdjustmentDetailResponse.StockAdjustmentDetail? = null
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
            view.stockQty.visibility = View.GONE
            view.stockQtyLabel.visibility = View.GONE
            view.binName.visibility = View.GONE
            view.btnDeleteDetail.visibility = View.GONE

            view.header.visibility = View.VISIBLE
            view.header.text = "Stock Count Details"
        }

        fun bindItem(item: StockAdjustmentDetailResponse.StockAdjustmentDetail) {
            this.item = item
            view.productName.text = item.product_full_name
            view.stockQty.text = item.qty.toString()
            view.onHandQty.text = item.on_hand.toString()
            view.stockQtyLabel.text = " ITEM(S)"
            view.onHandQtyLabel.text = " ON HAND"
            view.binName.text = item.bin_name
        }

        override fun onClick(p0: View?) {
            Log.e("Test->>>", "TEST")
        }

    }

}


