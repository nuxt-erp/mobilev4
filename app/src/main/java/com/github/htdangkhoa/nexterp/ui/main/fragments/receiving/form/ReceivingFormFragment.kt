
package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.form

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.product.ProductResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.ReceivingRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_receiving_form.*
import kotlin.properties.Delegates

class ReceivingFormFragment() : BaseFragment<ReceivingViewModel>(
    ReceivingViewModel::class) {

    private val args: ReceivingFormFragmentArgs by navArgs()
    private lateinit var receivingDetailsAdapter: ReceivingRecyclerAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var productId by Delegates.notNull<Int>()
    private var locationId by Delegates.notNull<Int>()

    override val layoutResID: Int
        get() = R.layout.fragment_receiving_form

    override fun render(view: View, savedInstanceState: Bundle?) {
        initialize(view)

        viewModel.resourceReceivingDetails.observe(viewLifecycleOwner, object : ObserverResource<Array<ReceivingDetailsResponse.ReceivingDetails>>() {
            override fun onSuccess(data: Array<ReceivingDetailsResponse.ReceivingDetails>) {
                receivingDetailsAdapter = ReceivingRecyclerAdapter(data.toMutableList()) {
                    productId = it.product_id
                    itemField.setText("")
                    itemField.clearFocus()
                    itemName.text = it.product_name
                    qtyField.setText(it.qty_received.toString())
                }
                receivingDetails.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = receivingDetailsAdapter
                }
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

        viewModel.resourceProduct.observe(viewLifecycleOwner, object : ObserverResource<Array<ProductResponse.Product>>() {
            override fun onSuccess(data: Array<ProductResponse.Product>) {
//                Log.e("TEST->>>", data.toString())
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
//        resourceReceivingDetails.value?.data?.forEach {
//            Log.e("resource->>>", it.toString())
//
//        }
        viewModel.getReceivingDetails(args.receiving.id)
    }
    private fun itemHandle(){
        itemField.doAfterTextChanged { text ->
            Log.e("TEXT->>>", text.toString())
            Log.e("LOCATION->>>", locationId.toString())
            viewModel.getProduct(locationId, text.toString())
        }
    }
    private fun initialize(view: View) {
        //variables
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!

        //xml
        receivingNumber.text = args.receiving.id.toString()
        receivingStatus.text = args.receiving.status
        itemField.setSelectAllOnFocus(true)
        receivingStatus.setTextColor(ContextCompat.getColor(view.context, checkStatus(args.receiving.status)))

        //functions
        itemHandle()
    }

    private fun checkStatus(status : String): Int {
        var color by Delegates.notNull<Int>()

        when (status) {
            "new" -> {
                color = R.color.statusNew
            }
            "partially received" -> {
                color =R.color.statusPartiallyReceived
            }
            "received" -> {
                color = R.color.statusReceived
            }
        }
        return color
    }
}