package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_transfers.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_transfers.ReceivingTransferViewModel
import kotlinx.android.synthetic.main.fragment_receiving_purchase_details.popupBackground
import kotlinx.android.synthetic.main.fragment_receiving_purchase_details.productDetailBarcode
import kotlinx.android.synthetic.main.fragment_receiving_purchase_details.productDetailCartonBarcode
import kotlinx.android.synthetic.main.fragment_receiving_purchase_details.productDetailCartonQty
import kotlinx.android.synthetic.main.fragment_receiving_purchase_details.productDetailName
import kotlinx.android.synthetic.main.fragment_receiving_purchase_details.productDetailSku
import kotlinx.android.synthetic.main.fragment_receiving_purchase_details.txtClose


class ReceivingTransferDetailsFragment() : BaseFragment<ReceivingTransferViewModel>(
    ReceivingTransferViewModel::class
) {
    override val layoutResID: Int
        get() = R.layout.fragment_receiving_transfers_details

    override fun render(view: View, savedInstanceState: Bundle?) {
        val args = this.requireArguments().getParcelable<ReceivingDetailsResponse.ReceivingDetails>("receivingDetails")
        productDetailName.text = args!!.product_full_name
        productDetailSku.text = args.product_sku
        productDetailBarcode.text = args.product_barcode
        productDetailCartonBarcode.text = args.product_carton_barcode
        productDetailCartonQty.text = args.product_carton_qty.toString()

        txtClose.setOnClickListener {
            val fm: FragmentManager = parentFragmentManager
            fm.beginTransaction().remove(this).commit()
        }

        popupBackground.setOnClickListener {
            val fm: FragmentManager = parentFragmentManager
            fm.beginTransaction().remove(this).commit()
        }

    }
}