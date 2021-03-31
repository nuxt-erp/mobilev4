package com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.StockAdjustmentDetailResponse
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.StockAdjustmentViewModel
import kotlinx.android.synthetic.main.fragment_stock_adjustment_details.*
import kotlinx.android.synthetic.main.fragment_stock_adjustment_details.binDetailBarcode
import kotlinx.android.synthetic.main.fragment_stock_adjustment_details.binDetailName
import kotlinx.android.synthetic.main.fragment_stock_adjustment_details.productDetailName
import kotlinx.android.synthetic.main.fragment_stock_adjustment_details.txtClose


class StockAdjustmentDetailsFragment() : BaseFragment<StockAdjustmentViewModel>(
    StockAdjustmentViewModel::class
) {
    override val layoutResID: Int
        get() = R.layout.fragment_stock_adjustment_details

    override fun render(view: View, savedInstanceState: Bundle?) {
        val args = this.requireArguments().getParcelable<StockAdjustmentDetailResponse.StockAdjustmentDetail>("stockAdjustmentDetail")
        Log.e("ARGS->>>" ,args.toString())
        productDetailName.text = args!!.product_full_name
        productDetailSku.text = args.product_sku
        productDetailBarcode.text = args.product_barcode
        productDetailCartonBarcode.text = args.product_carton_barcode
        productDetailCartonQty.text = args.product_carton_qty.toString()
        productDetailOnHand.text = args.on_hand.toString()

        if(args.bin_id != null) {
            binDetailName.text = args.bin_name
            binDetailBarcode.text = args.bin_searchable
        } else {
            binDetailName.text = "No bin found."
            binDetailBarcode.text = "No bin found."
        }

        txtClose.setOnClickListener {
            val fm: FragmentManager = parentFragmentManager
            fm.beginTransaction().remove(this).commit()
        }
    }
}