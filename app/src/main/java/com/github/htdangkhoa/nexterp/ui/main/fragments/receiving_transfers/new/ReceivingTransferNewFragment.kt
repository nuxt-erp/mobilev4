
package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_transfers.new

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.github.htdangkhoa.nexterp.Constant.TYPE_TRANSFER
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_transfers.ReceivingTransferViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_receiving_transfers_new.*
import timber.log.Timber
import kotlin.properties.Delegates


class ReceivingTransferNewFragment() : BaseFragment<ReceivingTransferViewModel>(
    ReceivingTransferViewModel::class
) {
    private lateinit var sharedPreferences: SharedPreferences
    private var locationId by Delegates.notNull<Int>()

    override val layoutResID: Int
        get() = R.layout.fragment_receiving_transfers_new

    override fun render(view: View, savedInstanceState: Bundle?) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!

        viewModel.resourceReceivingObject.observe(viewLifecycleOwner, object : ObserverResource<ReceivingResponse.Receiving>() {
            override fun onSuccess(data: ReceivingResponse.Receiving) {
                Timber.d(data.toString())
                val action = ReceivingTransferNewFragmentDirections.actionReceivingTransferNewFragmentToReceivingTransferFormFragment(data)
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
            val myTransfer = transferNumberInput.text.toString()
            val myInvoice = invoiceInput.text.toString()
            val myTracking = trackingInput.text.toString()
            if(myName.isNotEmpty()) {
                viewModel.newReceiving(locationId, myName, myTransfer, myInvoice, myTracking, TYPE_TRANSFER)
            } else {
                showDialog("Error", "You must enter a name for the Receiving.")
            }
        }
    }
}