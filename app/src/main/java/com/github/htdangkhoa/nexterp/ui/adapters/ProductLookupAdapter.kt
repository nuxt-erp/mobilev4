package com.github.htdangkhoa.nexterp.ui.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.data.remote.availability.AvailabilityResponse
import com.github.htdangkhoa.nexterp.ui.utils.inflate
import kotlinx.android.synthetic.main.product_lookup_details_item.view.*


class ProductLookupAdapter(
    private val list: List<AvailabilityResponse.Availability>,
) : RecyclerView.Adapter<ProductLookupAdapter.ListHolder>() {
    private var updateList : MutableList<AvailabilityResponse.Availability> = list as MutableList<AvailabilityResponse.Availability>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflatedView = parent.inflate(R.layout.product_lookup_details_item, false)
        return ListHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ProductLookupAdapter.ListHolder, position: Int) {

        if (position > 0) {
            val myPosition = position - 1
            val item = list[myPosition]
            holder.bindItem(item)
        } else {
            holder.bindHeader()
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    fun getUpdateList(): List<AvailabilityResponse.Availability> {
        return updateList.toList()
    }

    fun removeAt(position: Int): Int? {
        val id : Int? = updateList.elementAt((position - 1))!!.id
        updateList.removeAt(position - 1)
        notifyItemRemoved(position)
        return id
    }
    fun updateList(availabilityList: Array<AvailabilityResponse.Availability>) {
        updateList.clear()
        updateList.addAll(availabilityList)
    }

    class ListHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var item: AvailabilityResponse.Availability? = null
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
            view.productSku.visibility = View.GONE
            view.productBrand.visibility = View.GONE
            view.barcode.visibility = View.GONE
            view.cartonBarcode.visibility = View.GONE
            view.barcodeLabel.visibility = View.GONE
            view.cartonBarcodeLabel.visibility = View.GONE
            view.locationName.visibility = View.GONE
            view.binName.visibility = View.GONE
            view.binBarcode.visibility = View.GONE

            view.header.visibility = View.VISIBLE
            view.header.text = "Product Bin Details"
        }

        fun bindItem(item: AvailabilityResponse.Availability) {
            this.item = item
            view.productName.text = item.product_name
            view.productSku.text = item.product_sku
            view.productBrand.text = item.product_brand
            if(item.product_barcode != null) {
                view.barcode.text = item.product_barcode
            } else {
                view.barcode.text = "-"
            }
            if(item.product_carton_barcode != null) {
                view.cartonBarcode.text = item.product_carton_barcode
            } else {
                view.cartonBarcode.text = "-"
            }
            view.cartonBarcodeLabel.text = "CARTON BARCODE"
            view.barcodeLabel.text = "BARCODE"

            view.locationName.text = item.location_name
            view.binName.text = item.bin_name
            view.binBarcode.text = item.bin_barcode
        }

        override fun onClick(p0: View?) {
            Log.e("Test->>>", "TEST")
        }

    }

}


