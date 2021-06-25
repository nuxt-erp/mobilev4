package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_purchase.form

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.product.ProductResponse
import com.github.htdangkhoa.nexterp.data.remote.purchase.PurchaseDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.ReceivingRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.components.addRxTextWatcher
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_purchase.ReceivingViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_purchase.details.ReceivingDetailsFragment
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_receiving_purchase_form.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class ReceivingFormFragment() : BaseFragment<ReceivingViewModel>(
    ReceivingViewModel::class
) {

    private val args: ReceivingFormFragmentArgs by navArgs()
    private lateinit var receivingDetailsAdapter: ReceivingRecyclerAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var productId : Int? = null
    private var multiplier: Int = 1
    private var locationId by Delegates.notNull<Int>()

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(
        this.context,
        R.anim.rotate_open_anim
    )}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(
        this.context,
        R.anim.rotate_close_anim
    )}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(
        this.context,
        R.anim.from_bottom_anim
    )}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(
        this.context,
        R.anim.to_bottom_anim
    )}

    private var clicked : Boolean = false

    override val layoutResID: Int
        get() = R.layout.fragment_receiving_purchase_form

    override fun render(view: View, savedInstanceState: Bundle?) {
        initialize(view)

        viewModel.resourceFinish.observe(
            viewLifecycleOwner,
            object : ObserverResource<ReceivingResponse.Receiving>() {
                override fun onSuccess(data: ReceivingResponse.Receiving) {
                    findNavController().popBackStack()
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

        viewModel.resourceDeleteReceivingDetails.observe(
            this,
            object : ObserverResource<Array<ReceivingDetailsResponse.ReceivingDetails?>>() {
                override fun onSuccess(data: Array<ReceivingDetailsResponse.ReceivingDetails?>) {
                    Timber.tag("VOID->>>").e(data.toString())
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

        viewModel.resourceVoid.observe(
            this,
            object : ObserverResource<Int>() {
                override fun onSuccess(data: Int) {
                    val action = ReceivingFormFragmentDirections.actionReceivingFormFragmentToNavReceiving()
                    findNavController().navigate(action)
                }

                override fun onError(throwable: Throwable?) {
                    handleError(throwable) {
                        it?.message?.let {

                            showDialog("Error", it)
                        }
                    }
                }
            })

        viewModel.resourceReceivingObject.observe(
            viewLifecycleOwner,
            object : ObserverResource<ReceivingResponse.Receiving>() {
                override fun onSuccess(data: ReceivingResponse.Receiving) {
                    initDetails(data.receiving_details, data.purchase_details)
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

        viewModel.resourceProduct.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<ProductResponse.Product>>() {
                override fun onSuccess(data: Array<ProductResponse.Product>) {
                    if (data.isNullOrEmpty().not()) {
                        receivingDetailsAdapter.updateList(data, itemField.text.toString())
                        receivingDetailsAdapter.notifyDataSetChanged()

                        productId = data[0].id
                        itemName.text = data[0].product_full_name
                        qtyField.setText("0")
                        itemField.selectAll()
                    }
                    else{
                        itemName.text = "Product not found!"
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
    }

    //item text watcher
    private fun itemHandle() {
        itemField.addRxTextWatcher()
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
            if (it != null && !TextUtils.isEmpty(it) && it.length >= 3) {
                if (itemField.hasFocus()) {

                    if(multiplier < 1){
                        multiplierField.setText("1")
                    }

                    val receivingDetails: ReceivingDetailsResponse.ReceivingDetails? =
                        receivingDetailsAdapter.checkProductAndUpdate(it, multiplier)

                    if (receivingDetails == null) {
                        viewModel.getProduct(null, it.toString())
                    } else {
                        productId = receivingDetails.product_id
                        itemName.text = receivingDetails.product_name
                        qtyField.setText(receivingDetails.qty_received.toString())
                        itemField.selectAll()
                        multiplierField.setText("1")
                    }
                }
            }
            else {
                productId = null
            }
        }
    }

    // qty listener
    private fun qtyHandle() {
        qtyField.doAfterTextChanged { text ->
            if(text.toString().isEmpty().not()) {
                receivingDetailsAdapter.updateQty(productId!!, Integer.parseInt(text.toString()))
                receivingDetailsAdapter.notifyDataSetChanged()
            }
        }
    }

    // multiplier listener
    private fun multiplierHandle() {
        multiplierField.doAfterTextChanged { text ->
            if(text.isNullOrEmpty().not()){
                multiplier = Integer.parseInt(multiplierField.text.toString())
            }
        }
    }

    private fun initDetails(receiving_details: List<ReceivingDetailsResponse.ReceivingDetails?>, purchase_details: List<PurchaseDetailsResponse.PurchaseDetails?>){
        if(receiving_details.isNotEmpty() && purchase_details.isNotEmpty()){
            receiving_details.asSequence().zip(purchase_details.asSequence()).forEach { (a, b) ->
                if(a != null && b != null && a.product_id == b.product_id){
                    a.qty_on_purchase = b.qty
                }
            }
        }
        receivingDetailsAdapter = ReceivingRecyclerAdapter(receiving_details,
            callback = {
                productId = it.product_id
                itemField.setText(it.searchable)
                itemField.clearFocus()
                itemName.text = it.product_name
                qtyField.setText(it.qty_received.toString())
                receivingDetailsAdapter.notifyDataSetChanged()
            },
            callback2 = {
                val id = receivingDetailsAdapter.removeAt(it)
                if (id != null) {
                    viewModel.deleteReceivingDetail(id)
                }
            })

        receivingDetails.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = receivingDetailsAdapter
        }
    }

    private fun initialize(view: View) {

        initDetails(args.receiving.receiving_details, args.receiving.purchase_details)
        //variables
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!

        //xml
        receivingNumber.text = args.receiving.id.toString()
        if(args.receiving.po_number != null){
            poNumber.text = args.receiving.po_number.toString()
        }
        receivingStatus.text = args.receiving.status
        itemField.setSelectAllOnFocus(true)
        receivingStatus.setTextColor(
            ContextCompat.getColor(
                view.context,
                checkStatus(args.receiving.status)
            )
        )
        multiplierField.setText("1")

        //functions
        itemHandle()
        qtyHandle()
        multiplierHandle()

        //listeners
        expandButton.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }

        saveButton.setOnClickListener {
            viewModel.updateReceiving(
                args.receiving.id,
                locationId,
                receivingDetailsAdapter.getUpdateList()
            )
        }

        finishButton.setOnClickListener {
            viewModel.finishReceiving(args.receiving.id)
        }

        voidButton.setOnClickListener {
            viewModel.voidReceiving(args.receiving.id)
        }

        btnLinkToDetails.setOnClickListener {
            if(productId != null) {
                val receivingDetail : ReceivingDetailsResponse.ReceivingDetails? = receivingDetailsAdapter.findById(
                    productId!!
                )

                if(receivingDetail != null) {
                    val fm: FragmentManager = childFragmentManager
                    val bundle = Bundle()
                    val detailsFragment = ReceivingDetailsFragment()

                    bundle.putParcelable("receivingDetails", receivingDetail)
                    detailsFragment.arguments = bundle

                    fm.beginTransaction()
                        .replace(R.id.outer_frame, detailsFragment)
                        .commit()
                }
            }
        }
    }

    //return fancy colours depending on status
    private fun checkStatus(status: String): Int {
        var color by Delegates.notNull<Int>()

        when (status) {
            "new" -> {
                color = R.color.statusNew
            }
            "partially received" -> {
                color = R.color.statusPartiallyReceived
            }
            "received" -> {
                color = R.color.statusReceived
            }
        }
        return color
    }

    //animation and visibility for button expand
    private fun setVisibility(clicked: Boolean) {
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

    private fun setAnimation(clicked: Boolean) {
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