package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.form

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.product.ProductResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.ReceivingRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.components.addRxTextWatcher
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_receiving_form.*
import kotlinx.android.synthetic.main.fragment_receiving_form.progressCircular
import kotlinx.android.synthetic.main.fragment_receiving_list.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class ReceivingFormFragment() : BaseFragment<ReceivingViewModel>(
    ReceivingViewModel::class) {

    private val args: ReceivingFormFragmentArgs by navArgs()
    private lateinit var receivingDetailsAdapter: ReceivingRecyclerAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var productId by Delegates.notNull<Int>()
    private var locationId by Delegates.notNull<Int>()

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.rotate_open_anim )}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.rotate_close_anim )}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.from_bottom_anim )}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.to_bottom_anim )}

    private var clicked : Boolean = false

    override val layoutResID: Int
        get() = R.layout.fragment_receiving_form

    override fun render(view: View, savedInstanceState: Bundle?) {
        initialize(view)
        //resources (data) from view model
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
        viewModel.resourceFinish.observe(viewLifecycleOwner, object : ObserverResource<ReceivingResponse.Receiving>() {
            override fun onSuccess(data: ReceivingResponse.Receiving) {
                Log.e("FINISH->>>", data.toString())
                val action = ReceivingFormFragmentDirections.actionReceivingFormFragmentToNavReceiving()
                view.findNavController().navigate(action)            }

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


        viewModel.resourceVoid.observe(this, object : ObserverResource<Array<ReceivingResponse.Receiving?>>() {
            override fun onSuccess(data: Array<ReceivingResponse.Receiving?>) {
                Log.e("VOID->>>", data.toString())
                val action = ReceivingFormFragmentDirections.actionReceivingFormFragmentToNavReceiving()
                view.findNavController().navigate(action)
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let {
                        showDialog("Error", it)
                    }
                    throw it!!

                }
            }
        })

        viewModel.resourceReceivingObject.observe(viewLifecycleOwner, object : ObserverResource<ReceivingResponse.Receiving>() {
            override fun onSuccess(data: ReceivingResponse.Receiving) {
                viewModel.getReceivingDetails(data.id)
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
                Log.e("TEST->>>", data.toString())
                if(data.isNullOrEmpty().not()) {
                    receivingDetailsAdapter.updateList(data)
                    receivingDetailsAdapter.notifyDataSetChanged()

                    productId = data[0].id
                    itemName.text = data[0].name
                    qtyField.setText("0")
                    itemField.selectAll()
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
        viewModel.resourceLogout.observe(this, object : ObserverResource<String>() {
            override fun onSuccess(data: String) {
                logout(401)
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let {
                        showDialog("Error", it)
                    }
                }
            }
        })
        viewModel.getReceivingDetails(args.receiving.id)
    }

    //item text watcher
    private fun itemHandle() {
        itemField.addRxTextWatcher()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
            if (it != null && !TextUtils.isEmpty(it) && it.length >= 3) {
                val receivingDetails : ReceivingDetailsResponse.ReceivingDetails? = receivingDetailsAdapter.checkProductAndUpdate(it)
                if(receivingDetails == null) {
                    viewModel.getProduct(locationId, it.toString())
                } else {
                    productId = receivingDetails.product_id
                    itemName.text = receivingDetails.product_name
                    qtyField.setText(receivingDetails.qty_received.toString())
                    itemField.selectAll()
                }
            }
        }
    }

    // qty listener
    private fun qtyHandle() {
        qtyField.doAfterTextChanged { text ->
            receivingDetailsAdapter.updateQty(productId, text.toString())
            receivingDetailsAdapter.notifyDataSetChanged()
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
        qtyHandle()

        //listeners
        expandButton.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }

        saveButton.setOnClickListener {
            viewModel.updateReceiving(args.receiving.id, locationId,  receivingDetailsAdapter.getUpdateList())
        }

        finishButton.setOnClickListener {
            viewModel.finishReceiving(args.receiving.id)
        }

        voidButton.setOnClickListener {
            viewModel.voidReceiving(args.receiving.id)
        }
    }

    //return fancy colours depending on status
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

    //animation and visibility for button expand
    private fun setVisibility(clicked : Boolean) {
        if(!clicked) {
            saveButton.visibility = View.VISIBLE
            voidButton.visibility = View.VISIBLE
            finishButton.visibility = View.VISIBLE
        } else {
            saveButton.visibility = View.INVISIBLE
            voidButton.visibility = View.INVISIBLE
            finishButton.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked : Boolean) {
        if(!clicked) {
            saveButton.startAnimation(fromBottom)
            voidButton.startAnimation(fromBottom)
            finishButton.startAnimation(fromBottom)
            expandButton.startAnimation(rotateOpen)
        } else {
            saveButton.startAnimation(toBottom)
            voidButton.startAnimation(toBottom)
            finishButton.startAnimation(toBottom)
            expandButton.startAnimation(rotateClose)
        }
    }
}