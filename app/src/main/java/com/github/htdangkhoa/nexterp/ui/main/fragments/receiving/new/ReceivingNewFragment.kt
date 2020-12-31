
package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.new

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.bin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.CheckableSpinnerAdapter
import com.github.htdangkhoa.nexterp.ui.adapters.CheckableSpinnerAdapter.SpinnerItem
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list.StockCountListFragmentDirections
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stockcount_list.progressCircular
import kotlinx.android.synthetic.main.fragment_stockcount_new.*


class ReceivingNewFragment() : BaseFragment<ReceivingViewModel>(
    ReceivingViewModel::class
) {

    override val layoutResID: Int
        get() = R.layout.fragment_receiving_new

    override fun render(view: View, savedInstanceState: Bundle?) {

        btnSaveFilters.setOnClickListener {
            val myName = nameInput.text.toString()
            if(myName.isNotEmpty()) {
//                viewModel.newStockCount(createMap(), myName)
            } else {
                showDialog("Error", "You must enter a name for the Stock Count.")
            }
        }
    }
}