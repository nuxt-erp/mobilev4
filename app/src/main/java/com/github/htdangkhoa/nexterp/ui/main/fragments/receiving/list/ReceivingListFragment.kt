
package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.list

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.Constant.TYPE_PURCHASE
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.ReceivingListAdapter
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_receiving_list.*

class ReceivingListFragment() : BaseFragment<ReceivingViewModel>(
    ReceivingViewModel::class) {

    override val layoutResID: Int
        get() = R.layout.fragment_receiving_list

    private val alphabeticalComparator: Comparator<ReceivingResponse.Receiving> =
        Comparator { a, b -> a.id.compareTo(b.id) }

    override fun render(view: View, savedInstanceState: Bundle?) {
        val mAdapter = ReceivingListAdapter(
            this.context,
            viewModel,
            alphabeticalComparator
        )
        val linearLayoutManager = LinearLayoutManager(this.context)

        receivingList.layoutManager = linearLayoutManager
        receivingList.adapter = mAdapter

        viewModel.resourceReceiving.observe(viewLifecycleOwner, object : ObserverResource<Array<ReceivingResponse.Receiving>>() {
            override fun onSuccess(data: Array<ReceivingResponse.Receiving>) {
                mAdapter.add(data)
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
        viewModel.getReceiving(TYPE_PURCHASE)

        newButton.setOnClickListener {
            val action = ReceivingListFragmentDirections.actionNavReceivingToReceivingNewFragment()
            view.findNavController().navigate(action)
        }
    }
}