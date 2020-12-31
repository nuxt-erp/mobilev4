
package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.new

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.bin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.CheckableSpinnerAdapter
import com.github.htdangkhoa.nexterp.ui.adapters.CheckableSpinnerAdapter.SpinnerItem
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list.StockCountListFragmentDirections
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.new.StockCountNewFragmentDirections
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stockcount_list.progressCircular
import kotlinx.android.synthetic.main.fragment_stockcount_new.*
import kotlin.properties.Delegates


class ReceivingNewFragment() : BaseFragment<ReceivingViewModel>(
    ReceivingViewModel::class
) {
    private lateinit var sharedPreferences: SharedPreferences
    private var locationId by Delegates.notNull<Int>()

    override val layoutResID: Int
        get() = R.layout.fragment_receiving_new

    override fun render(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!

        viewModel.resourceReceivingObject.observe(viewLifecycleOwner, object : ObserverResource<ReceivingResponse.Receiving>() {
            override fun onSuccess(data: ReceivingResponse.Receiving) {
                val action = ReceivingNewFragmentDirections.actionReceivingNewFragmentToNavReceiving()
                view.findNavController().navigate(action)

            }
            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let { toast(it) }
                    handleHttpError(it)
                }
            }

            override fun onLoading(isShow: Boolean) {
                progressCircular.apply {
                    if (isShow) show() else hide(true)
                }
            }
        })
        btnSaveFilters.setOnClickListener {
            val myName = nameInput.text.toString()
            if(myName.isNotEmpty()) {
                viewModel.newReceiving(locationId, myName)
            } else {
                showDialog("Error", "You must enter a name for the Receiving.")
            }
        }
    }
}