package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.form

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.locationbin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.pagination.PaginationObject
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.StockCountRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.components.PaginationListener
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
    private var binsMandatory by Delegates.notNull<Boolean>()
    private val currentList :  MutableList<ProductAvailabilityResponse.ProductAvailability> = ArrayList()

    // Index from which pagination should start (0 is 1st page in our case)
    private val start = 1
    private var initialized = false

    // If current page is the last page (Pagination will stop after this page load)
    private val isLastPage = false

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private var totalPages = 1

    // indicates the current page which Pagination is fetching.
    private var currentPage = start

    private var nextPage = start

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
                        if (noMatch !== null) {
                            viewModel.newAvailability(
                                binField.text.toString(),
                                noMatch.product_id,
                                noMatch.location_id
                            )
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

        viewModel.resourceStockCountDetails.observe(
            viewLifecycleOwner,
            object :
                ObserverResource<Pair<Array<StockCountDetailResponse.StockCountDetail>, PaginationObject?>>() {
                override fun onSuccess(data: Pair<Array<StockCountDetailResponse.StockCountDetail>, PaginationObject?>) {
                    val (dataArray, pagination) = data
                    Log.e("PAGINATION ->>>", pagination.toString())
                    totalPages = pagination!!.total
                    currentPage = pagination.current_page
                    if(currentPage + 1 < pagination.last_page) {
                        nextPage = currentPage + 1
                    }
                    stockCountDetailsAdapter.addAll(dataArray)
                    stockCountDetailsAdapter.notifyDataSetChanged()
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
        viewModel.resourceNewAvailabilityObject.observe(
            viewLifecycleOwner,
            object : ObserverResource<ProductAvailabilityResponse.ProductAvailability>() {
                override fun onSuccess(data: ProductAvailabilityResponse.ProductAvailability) {
                    itemName.text = data.product_name
                    binName.text = data.bin_name
                    qtyField.setText("0")
                    productId = data.product_id
                    binId = data.bin_id

                    stockCountDetailsAdapter.updateListWithObj(
                        data,
                        itemField.text.toString(),
                        binId
                    )
                    itemField.selectAll()
                    stockCountDetailsAdapter.notifyDataSetChanged()
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
                    viewModel.getStockCountDetails(data.id, nextPage)
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
                                if (it.product_barcode == itemField.text.toString() || it.product_sku == itemField.text.toString() || it.product_carton_barcode == itemField.text.toString()) {
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
                        val binNoMatches = data.any { obj -> obj.bin_matches == false }

                        if (binNoMatches) {
                            currentList.clear()
                            currentList.addAll(data)
                            binNoMatchDialog!!.show()
                        } else {
                            stockCountDetailsAdapter.updateList(
                                data,
                                itemField.text.toString(),
                                binId
                            )
                            itemField.selectAll()
                            stockCountDetailsAdapter.notifyDataSetChanged()
                        }
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
        viewModel.getStockCountDetails(args.stockCount.id, nextPage)

    }

    private fun initialize(view: View) {
        //variables
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!
        binsMandatory = sharedPreferences.getString("binsMandatory", null)?.toBoolean()!!
        disableItems()


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

            val mutableList : MutableList<StockCountDetailResponse.StockCountDetail> = ArrayList()
            stockCountDetailsAdapter = StockCountRecyclerAdapter(
                mutableList,
                callback = {
                    productId = it.product_id
                    binId = it.bin_id
                    itemField.clearFocus()
                    itemName.text = it.product_name
                    binName.text = it.bin_name
                    binField.setText(it.bin_searchable)
                    itemField.setText(it.searchable)
                    qtyField.setText(it.qty.toString())
                    stockCountDetailsAdapter.notifyDataSetChanged()
                },
                callback2 = {
                    val id = stockCountDetailsAdapter.removeAt(it)
                    if (id != null) {
                        viewModel.deleteStockCountDetail(id)
                    }
                },
            )
            val linearLayoutManager = LinearLayoutManager(activity)

            stockCountDetails.apply {
                layoutManager = linearLayoutManager
                adapter = stockCountDetailsAdapter
            }
            initialized = true


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
                    disableItems()
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
                                binField.text.toString(),
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