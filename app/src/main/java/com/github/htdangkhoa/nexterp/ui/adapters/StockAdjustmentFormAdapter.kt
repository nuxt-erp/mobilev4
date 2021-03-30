package com.github.htdangkhoa.nexterp.ui.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.StockAdjustmentDetailResponse
import com.github.htdangkhoa.nexterp.ui.utils.inflate
import kotlinx.android.synthetic.main.receiving_details_item.view.productName
import kotlinx.android.synthetic.main.stockadjustment_details_item.view.*


class StockAdjustmentRecyclerAdapter(
    private val list: List<StockAdjustmentDetailResponse.StockAdjustmentDetail>,
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
            view.onHandQtyLabel.text = " ITEM(S) ON HAND"
            view.binName.text = item.bin_name
        }

        override fun onClick(p0: View?) {
            Log.e("Test->>>", "TEST")
        }

    }

}


