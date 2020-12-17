
package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.form

import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_receiving_list.*

class ReceivingFormFragment() : BaseFragment<ReceivingViewModel>(
    ReceivingViewModel::class) {

    override val layoutResID: Int
        get() = R.layout.fragment_receiving_form

    override fun render(view: View, savedInstanceState: Bundle?) {

        viewModel.resourceReceivingDetails.observe(viewLifecycleOwner, object : ObserverResource<Array<ReceivingDetailsResponse.ReceivingDetails>>() {
            override fun onSuccess(data: Array<ReceivingDetailsResponse.ReceivingDetails>) {
                data.forEach {
                    Log.e("DETAILS->>>", it.toString())
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
//        viewModel.getReceivingDetails(data[0].id)


    }
}