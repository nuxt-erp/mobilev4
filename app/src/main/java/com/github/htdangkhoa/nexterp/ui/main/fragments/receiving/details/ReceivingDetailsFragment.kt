package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel
import kotlinx.android.synthetic.main.fragment_receiving_details.*


class ReceivingDetailsFragment() : BaseFragment<ReceivingViewModel>(
    ReceivingViewModel::class
) {
    override val layoutResID: Int
        get() = R.layout.fragment_receiving_details

    override fun render(view: View, savedInstanceState: Bundle?) {
        val args = this.requireArguments().getParcelable<ReceivingDetailsResponse.ReceivingDetails>("receivingDetails")
        productDetailId.text = args!!.product_id.toString()
        productDetailName.text = args.product_name
        productDetailSearchable.text = args.searchable
        productDetailReceivedQty.text = args.qty_received.toString()

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