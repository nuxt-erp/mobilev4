package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.form

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
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
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.locationbin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.StockCountRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.components.SwipeToDeleteCallback
import com.github.htdangkhoa.nexterp.ui.components.addRxTextWatcher
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.details.StockCountDetailsFragment
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_stockcount_form.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class StockCountFormFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class
) {
    private lateinit var stockCountDetailsAdapter: StockCountRecyclerAdapter
    private val args: StockCountFormFragmentArgs by navArgs()
    private lateinit var sharedPreferences: SharedPreferences
    private var productId: Int? = null
    private var binId: Int? = null
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
        get() = R.layout.fragment_stockcount_form

    override fun render(view: View, savedInstanceState: Bundle?) {
        initialize(view)
        viewModel.resourceStockCountDetails.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<StockCountDetailResponse.StockCountDetail>>() {
                override fun onSuccess(data: Array<StockCountDetailResponse.StockCountDetail>) {
                    stockCountDetailsAdapter = StockCountRecyclerAdapter(data.toMutableList()) {
                        productId = it.product_id
                        binId = it.bin_id
                        itemField.clearFocus()
                        itemName.text = it.product_name
                        binName.text = it.bin_name
                        binField.setText(it.bin_searchable)
                        itemField.setText(it.searchable)
                        qtyField.setText(it.qty.toString())
                        stockCountDetailsAdapter.notifyDataSetChanged()
                    }

                    val swipeHandler = object : SwipeToDeleteCallback(context!!) {
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val id = stockCountDetailsAdapter.removeAt(viewHolder.adapterPosition)
                            if (id != null) {
                                viewModel.deleteStockCountDetail(id)
                            }
                        }
                    }

                    val itemTouchHelper = ItemTouchHelper(swipeHandler)
                    itemTouchHelper.attachToRecyclerView(stockCountDetails)

                    stockCountDetails.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = stockCountDetailsAdapter
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
        viewModel.resourceBins.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<BinResponse.Bin>>() {
                override fun onSuccess(data: Array<BinResponse.Bin>) {
                    if (data.isNotEmpty()) {
                        binName.text = data[0].name
                        binId = data[0].id
                        itemField.requestFocus()
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
        viewModel.resourceFinish.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<StockCountResponse.StockCount?>>() {
                override fun onSuccess(data: Array<StockCountResponse.StockCount?>) {

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

        viewModel.resourceVoid.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<StockCountResponse.StockCount?>>() {
                override fun onSuccess(data: Array<StockCountResponse.StockCount?>) {

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

        viewModel.resourceStockCountObject.observe(
            viewLifecycleOwner,
            object : ObserverResource<StockCountResponse.StockCount>() {
                override fun onSuccess(data: StockCountResponse.StockCount) {
                    viewModel.getStockCountDetails(data.id)
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
                        if (data.size == 1) {
                            itemName.text = data[0].product_name
                            productId = data[0].product_id
                            binId = data[0].bin_id
                            if (binId != null) binName.text = data[0].bin_name else binName.text =
                                "No bin selected"
                            qtyField.setText(data[0].qty.toString())
                        } else {
                            val previousProductId: Int? = productId

                            data.forEach {
                                Log.e("HERE->>>", "No bin")
                                if (it.searchable == itemField.text.toString() || it.product_sku == itemField.text.toString()) {
                                    Log.e("HERE->>>", it.toString())
                                    itemName.text = it.product_name
                                    productId = it.product_id

                                    if (previousProductId != productId && previousProductId != null) {
                                        binId = null
                                        binName.text = "Select a bin"
                                    }
                                }

                                if (binField.text.toString().isEmpty() && it.bin_id == null) {
                                    binId = null
                                    qtyField.setText(it.qty.toString())
                                    itemName.text = it.product_name
                                } else if (binField.text.toString()
                                        .isNotEmpty() && it.bin_searchable == binField.text.toString()
                                ) {
                                    binId = it.bin_id
                                    qtyField.setText(it.qty.toString())
                                    itemName.text = it.product_name
                                }
                            }
                        }

                        itemField.selectAll()
                        stockCountDetailsAdapter.updateList(data, binId)
                        stockCountDetailsAdapter.notifyDataSetChanged()
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
        viewModel.getStockCountDetails(args.stockCount.id)
    }

    private fun initialize(view: View) {
        //variables
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!

        //xml
        stockCountNumber.text = args.stockCount.id.toString()
        stockStatus.text = args.stockCount.status_name
        itemField.setSelectAllOnFocus(true)
        binField.setSelectAllOnFocus(true)

        //functions
        binHandle()
        itemHandle()
        qtyHandle()

        val finishDialog: AlertDialog? = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.are_you_sure_finish_title)
                setMessage(R.string.are_you_sure_finish)
                setPositiveButton(R.string.proceed,
                    DialogInterface.OnClickListener { dialog, _ ->
                        viewModel.finishStockCount(args.stockCount.id)
                        dialog.dismiss()

                    })
                setNegativeButton(R.string.abort,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }

            builder.create()
        }
        val voidDialog: AlertDialog? = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.are_you_sure_void_title)
                setMessage(R.string.are_you_sure_void)
                setPositiveButton(R.string.proceed,
                    DialogInterface.OnClickListener { dialog, _ ->
                        viewModel.voidStockCount(args.stockCount.id)
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
            viewModel.updateStockCount(
                args.stockCount.id,
                locationId,
                stockCountDetailsAdapter.getUpdateList()
            )
        }

        finishButton.setOnClickListener {

            // handle are you sure popup?
            finishDialog!!.show()
        }

        voidButton.setOnClickListener {
            // handle are you sure popup?
            voidDialog!!.show()
        }
        btnLinkToStockDetails.setOnClickListener {
            if(productId != null) {
                val stockCountDetail : StockCountDetailResponse.StockCountDetail? = stockCountDetailsAdapter.findById(
                    productId!!, binId
                )

                if(stockCountDetail != null) {
                    val fm: FragmentManager = childFragmentManager
                    val bundle = Bundle()
                    val detailsFragment = StockCountDetailsFragment()

                    bundle.putParcelable("stockCountDetail", stockCountDetail)
                    detailsFragment.arguments = bundle

                    fm.beginTransaction()
                        .replace(R.id.outer_frame_sc, detailsFragment)
                        .commit()
                }
            }
        }
    }

    private fun binHandle(){
        binField.addRxTextWatcher()
        .debounce(300, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe {
            if (binField.hasFocus()) {
                if (it != null && !TextUtils.isEmpty(it) && it.length >= 3) {
                    viewModel.getBin(it.toString(), locationId, 0, 1)
                } else if (TextUtils.isEmpty(it)) {
                    binId = null
                    binName.text = "No bin selected"
                }
            }
        }

    }

    // qty listener
    private fun qtyHandle() {
        qtyField.doAfterTextChanged { text ->
            if(itemField.text.toString().isEmpty().not()) {
                stockCountDetailsAdapter.updateQty(productId!!, binId, text.toString())
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
                    if (it != null && !TextUtils.isEmpty(it) && it.length >= 3) {
                        val stockCountDetails: StockCountDetailResponse.StockCountDetail? =
                            stockCountDetailsAdapter.checkProductAndUpdate(it, binId)

                        if (stockCountDetails == null) {
                            viewModel.getProductAvailability(
                                it,
                                locationId,
                                args.stockCount.brand_ids,
                                args.stockCount.tag_ids,
                                args.stockCount.bin_ids,
                                args.stockCount.category_ids,
                                args.stockCount.stock_locator_ids
                            )
                        } else {
                            productId = stockCountDetails.product_id
                            itemName.text = stockCountDetails.product_name
                            Log.e("DETAIL->>>", stockCountDetails.qty.toString())
                            qtyField.setText(stockCountDetails.qty.toString())
                            itemField.selectAll()
                        }
                    } else {
                        productId = null
                        itemName.text = "Product not on count"
                    }
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