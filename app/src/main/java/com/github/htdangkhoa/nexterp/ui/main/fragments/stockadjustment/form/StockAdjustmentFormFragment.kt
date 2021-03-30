package com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.form

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.availability.AvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.locationbin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.StockAdjustmentDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.StockAdjustmentRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.components.addRxTextWatcher
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.StockAdjustmentViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_stock_adjustment_form.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class StockAdjustmentFormFragment() : BaseFragment<StockAdjustmentViewModel>(
    StockAdjustmentViewModel::class
) {
    private lateinit var stockAdjustmentDetailsAdapter: StockAdjustmentRecyclerAdapter
    private val args: StockAdjustmentFormFragmentArgs by navArgs()
    private lateinit var sharedPreferences: SharedPreferences
    private var productId: Int? = null
    private var binId: Int? = null
    private var locationId by Delegates.notNull<Int>()
    private var binsMandatory by Delegates.notNull<Boolean>()
    private val currentList :  MutableList<ProductAvailabilityResponse.ProductAvailability> = ArrayList()

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
        get() = R.layout.fragment_stock_adjustment_form

    override fun render(view: View, savedInstanceState: Bundle?) {
        initialize(view)

        val binNoMatchDialog: AlertDialog? = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.are_you_sure_no_bin_match_title)
                setMessage(R.string.are_you_sure_no_bin_match)
                setPositiveButton(R.string.update,
                    DialogInterface.OnClickListener { dialog, _ ->
                        val noMatch = currentList.find { availability ->
                            availability.bin_matches == false
                        }
//                      send api request
                        if(noMatch !== null) {
                            viewModel.newAvailability(binField.text.toString(), noMatch.product_id, noMatch.location_id)
                        }
                        dialog.dismiss()

                    })
                setNegativeButton(R.string.abort,
                    DialogInterface.OnClickListener { dialog, _ ->
                        productId = null
                        binId = null
                        binName.text = "Select a bin"
                        itemName.text = "Select a bin"
                        itemField.setText("")
                        binField.setText("")
                        qtyField.setText("")
                        binField.requestFocus()
                        dialog.cancel()
                    })
            }

            builder.create()
        }

        viewModel.resourceStockAdjustmentDetails.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail>>() {
                override fun onSuccess(data: Array<StockAdjustmentDetailResponse.StockAdjustmentDetail>) {
//                    stockAdjustmentDetailsAdapter = StockAdjustmentRecyclerAdapter(
//                        data.toMutableList(),
//                        callback = {
//                            productId = it.product_id
//                            binId = it.bin_id
//                            itemField.clearFocus()
//                            itemName.text = it.product_name
//                            binName.text = it.bin_name
//                            binField.setText(it.bin_searchable)
//                            itemField.setText(it.searchable)
//                            qtyField.setText(it.qty.toString())
//                            stockAdjustmentDetailsAdapter.notifyDataSetChanged()
//                        },
//                        callback2 = {
//                            val id = stockAdjustmentDetailsAdapter.removeAt(it)
//                            if (id != null) {
//                                viewModel.deleteStockAdjustmentDetail(id)
//                            }
//                        },
//                    )
//
//
//                    stockAdjustmentDetails.apply {
//                        layoutManager = LinearLayoutManager(activity)
//                        adapter = stockAdjustmentDetailsAdapter
//                    }
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

        viewModel.resourceBins.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<BinResponse.Bin>>() {
                override fun onSuccess(data: Array<BinResponse.Bin>) {
                    if (data.isNotEmpty()) {
                        binName.text = data[0].name
                        binId = data[0].id
                        itemField.requestFocus()
                    } else {
                        binName.text = binField.text.toString()
                        binId = null

                    }
                    enableItems()
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

        viewModel.resourceVoid.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<StockAdjustmentResponse.StockAdjustment?>>() {
                override fun onSuccess(data: Array<StockAdjustmentResponse.StockAdjustment?>) {
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
        viewModel.resourceNewAvailabilityObject.observe(
            viewLifecycleOwner,
            object : ObserverResource<ProductAvailabilityResponse.ProductAvailability>() {
                override fun onSuccess(data: ProductAvailabilityResponse.ProductAvailability) {
//                    itemName.text = data.product_name
//                    binName.text = data.bin_name
//                    qtyField.setText("0")
//                    productId = data.product_id
//                    binId = data.bin_id
//
//                    stockAdjustmentDetailsAdapter.updateListWithObj(data, itemField.text.toString(), binId)
//                    itemField.selectAll()
//                    stockAdjustmentDetailsAdapter.notifyDataSetChanged()
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
        viewModel.resourceStockAdjustmentObject.observe(
            viewLifecycleOwner,
            object : ObserverResource<StockAdjustmentResponse.StockAdjustment>() {
                override fun onSuccess(data: StockAdjustmentResponse.StockAdjustment) {
                    viewModel.getStockAdjustmentDetails(data.id)
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

        viewModel.resourceProductAvailability.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<ProductAvailabilityResponse.ProductAvailability>>() {
                override fun onSuccess(data: Array<ProductAvailabilityResponse.ProductAvailability>) {
                    if (data.isNotEmpty()) {
//                        if (data.size == 1) {
//                            itemName.text = data[0].product_name
//                            productId = data[0].product_id
//                            binId = data[0].bin_id
//                            if (binId != null) binName.text = data[0].bin_name else binName.text =
//                                "No bin selected"
//                            qtyField.setText(data[0].qty.toString())
//                        } else {
//                            val previousProductId: Int? = productId
//                            data.forEach {
//                                if (it.product_barcode == itemField.text.toString() || it.product_sku == itemField.text.toString() || it.product_carton_barcode == itemField.text.toString()) {
//                                    itemName.text = it.product_name
//                                    productId = it.product_id
//                                    if (previousProductId != productId && previousProductId != null) {
//                                        binId = null
//                                        binName.text = "Select a bin"
//                                    }
//                                }
//
//                                if (binField.text.toString().isEmpty() && it.bin_id == null) {
//                                    binId = null
//                                    qtyField.setText(it.qty.toString())
//                                    itemName.text = it.product_name
//                                } else if (binField.text.toString()
//                                        .isNotEmpty() && it.bin_searchable == binField.text.toString()
//                                ) {
//                                    binId = it.bin_id
//                                    qtyField.setText(it.qty.toString())
//                                    itemName.text = it.product_name
//                                }
//                            }
//                        }
//                        val binNoMatches = data.any{ obj -> obj.bin_matches == false }
//
//                        if(binNoMatches) {
//                            currentList.clear()
//                            currentList.addAll(data)
//                            binNoMatchDialog!!.show()
//                        } else {
//                            stockAdjustmentDetailsAdapter.updateList(data, itemField.text.toString(), binId)
//                            itemField.selectAll()
//                            stockAdjustmentDetailsAdapter.notifyDataSetChanged()
//                        }
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
        viewModel.getStockAdjustmentDetails(args.stockAdjustment.id)
    }

    private fun initialize(view: View) {
        //variables
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!
        binsMandatory = sharedPreferences.getString("binsMandatory", null)?.toBoolean()!!
        disableItems()


        //xml
        stockAdjustmentNumber.text = args.stockAdjustment.id.toString()
        itemField.setSelectAllOnFocus(true)
        binField.setSelectAllOnFocus(true)

        //functions
        binHandle()
        itemHandle()
        qtyHandle()

        val voidDialog: AlertDialog? = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.are_you_sure_void_title)
                setMessage(R.string.are_you_sure_void)
                setPositiveButton(R.string.proceed,
                    DialogInterface.OnClickListener { dialog, _ ->
                        viewModel.voidStockAdjustment(args.stockAdjustment.id)
                        dialog.dismiss()
                    })
                setNegativeButton(R.string.abort,
                    DialogInterface.OnClickListener { dialog, _ ->

                        dialog.cancel()
                    })
            }

            builder.create()
        }


        //listeners
        expandButton.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }

        saveButton.setOnClickListener {
            viewModel.updateStockAdjustment(
                args.stockAdjustment.id,
                locationId,
                stockAdjustmentDetailsAdapter.getUpdateList()
            )
        }
        voidButton.setOnClickListener {
            // handle are you sure popup?
            voidDialog!!.show()
        }

        btnLinkToStockAdjustmentDetails.setOnClickListener {
//            if(productId != null) {
//                val stockAdjustmentDetail : StockAdjustmentDetailResponse.StockAdjustmentDetail? = stockAdjustmentDetailsAdapter.findById(
//                    productId!!, binId
//                )
//
//                if(stockAdjustmentDetail != null) {
//                    val fm: FragmentManager = childFragmentManager
//                    val bundle = Bundle()
//                    val detailsFragment = StockAdjustmentDetailsFragment()
//
//                    bundle.putParcelable("stockAdjustmentDetail", stockAdjustmentDetail)
//                    detailsFragment.arguments = bundle
//
//                    fm.beginTransaction()
//                        .replace(R.id.outer_frame_sc, detailsFragment)
//                        .commit()
//                }
//            }
        }
    }

    private fun binHandle(){
        binField.addRxTextWatcher()
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (binField.hasFocus()) {
//                    if (it != null && !TextUtils.isEmpty(it) && it.length >= 3) {
//                        viewModel.getBin(it.toString(), locationId, 0, 1)
//                    } else if (TextUtils.isEmpty(it)) {
//                        binId = null
//                        binName.text = "No bin selected"
//                        disableItems()
//                    }
                }
            }

    }

    // qty listener
    private fun qtyHandle() {
        qtyField.doAfterTextChanged { text ->
            if(itemField.text.toString().isEmpty().not()) {
                stockAdjustmentDetailsAdapter.updateQty(productId!!, binId, text.toString())
            }
        }
    }

    //item text watcher
    private fun itemHandle() {
        itemField.addRxTextWatcher()
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (itemField.hasFocus()) {
//                    if (it != null && !TextUtils.isEmpty(it) && it.length >= 3) {
//
//                        val stockAdjustmentDetails: StockAdjustmentDetailResponse.StockAdjustmentDetail? =
//                            stockAdjustmentDetailsAdapter.checkProductAndUpdate(it, binId)
//
//                        if (stockAdjustmentDetails == null) {
//                            viewModel.getProductAvailability(
//                                it,
//                                binField.text.toString(),
//                                locationId,
//                                args.stockAdjustment.brand_ids,
//                                args.stockAdjustment.tag_ids,
//                                args.stockAdjustment.bin_ids,
//                                args.stockAdjustment.category_ids,
//                                args.stockAdjustment.stock_locator_ids
//                            )
//                        } else {
//                            productId = stockAdjustmentDetails.product_id
//                            itemName.text = stockAdjustmentDetails.product_name
//                            qtyField.setText(stockAdjustmentDetails.qty.toString())
//                            itemField.selectAll()
//                        }
//                    } else {
//                        productId = null
//                        itemName.text = "Product not on count"
//                    }
                }
            }
    }

    //return fancy colours depending on status
    private fun checkStatus(status: String): Int {
        var color by Delegates.notNull<Int>()

        when (status) {
            "IN PROGRESS" -> {
                color = R.color.statusPartiallyReceived
            }
            "DONE" -> {
                color = R.color.statusReceived
            }
        }
        return color
    }
    private fun disableItems() {
        itemField.isEnabled = false;
        itemField.inputType = InputType.TYPE_NULL;
        itemField.isFocusable = false;
        itemField.isFocusableInTouchMode = false;
    }
    private fun enableItems() {
        itemField.isEnabled = true;
        itemField.inputType = InputType.TYPE_CLASS_TEXT;
        itemField.isFocusable = true;
        itemField.isFocusableInTouchMode = true;
    }
    //animation and visibility for button expand
    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
            saveButton.visibility = View.VISIBLE
            voidButton.visibility = View.VISIBLE
        } else {
            saveButton.visibility = View.INVISIBLE
            voidButton.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked) {
            saveButton.startAnimation(fromBottom)
            voidButton.startAnimation(fromBottom)
            expandButton.startAnimation(rotateOpen)
        } else {
            saveButton.startAnimation(toBottom)
            voidButton.startAnimation(toBottom)
            expandButton.startAnimation(rotateClose)
        }
    }
}