package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import kotlinx.android.synthetic.main.fragment_stockcount_details.*


class StockCountDetailsFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class
) {
    override val layoutResID: Int
        get() = R.layout.fragment_stockcount_details

    override fun render(view: View, savedInstanceState: Bundle?) {
        val args = this.requireArguments().getParcelable<StockCountDetailResponse.StockCountDetail>("stockCountDetail")
        productDetailId.text = args!!.product_id.toString()
        productDetailName.text = args.product_name
        productDetailSearchable.text = args.searchable
        productDetailQty.text = args.qty.toString()

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

        popupBackground.setOnClickListener {
            val fm: FragmentManager = parentFragmentManager
            fm.beginTransaction().remove(this).commit()
        }

    }
}