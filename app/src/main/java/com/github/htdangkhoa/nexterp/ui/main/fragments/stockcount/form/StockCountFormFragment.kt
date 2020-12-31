package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.form

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.bin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.StockCountRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.components.addRxTextWatcher
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.form.ReceivingFormFragmentDirections
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_receiving_form.*
import kotlinx.android.synthetic.main.fragment_stockcount_form.*
import kotlinx.android.synthetic.main.fragment_stockcount_form.expandButton
import kotlinx.android.synthetic.main.fragment_stockcount_form.finishButton
import kotlinx.android.synthetic.main.fragment_stockcount_form.itemField
import kotlinx.android.synthetic.main.fragment_stockcount_form.itemName
import kotlinx.android.synthetic.main.fragment_stockcount_form.progressCircular
import kotlinx.android.synthetic.main.fragment_stockcount_form.qtyField
import kotlinx.android.synthetic.main.fragment_stockcount_form.saveButton
import kotlinx.android.synthetic.main.fragment_stockcount_form.voidButton
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class StockCountFormFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class) {
    private lateinit var stockCountDetailsAdapter: StockCountRecyclerAdapter
    private val args: StockCountFormFragmentArgs by navArgs()
    private lateinit var sharedPreferences: SharedPreferences
    private var productId by Delegates.notNull<Int>()
    private var binId: Int? = null
    private var selectedQty: Int? = null
    private var locationId by Delegates.notNull<Int>()

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.rotate_open_anim )}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.rotate_close_anim )}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.from_bottom_anim )}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.to_bottom_anim )}

    private var clicked : Boolean = false

    override val layoutResID: Int
        get() = R.layout.fragment_stockcount_form

    override fun render(view: View, savedInstanceState: Bundle?) {
        initialize(view)
        viewModel.resourceStockCountDetails.observe(viewLifecycleOwner, object : ObserverResource<Array<StockCountDetailResponse.StockCountDetail>>() {
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
        viewModel.resourceBins.observe(viewLifecycleOwner, object : ObserverResource<Array<BinResponse.Bin>>() {
            override fun onSuccess(data: Array<BinResponse.Bin>) {
                if(data.isNotEmpty()) {
                    binName.text = data[0].name
                    binId = data[0].id
                    itemField.requestFocus()
                    Log.e("BIN->>>", data.toString())
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
        viewModel.resourceFinish.observe(viewLifecycleOwner, object : ObserverResource<StockCountResponse.StockCount>() {
            override fun onSuccess(data: StockCountResponse.StockCount) {
                val action = StockCountFormFragmentDirections.actionStockCountFormFragmentToNavStockCount()
                view.findNavController().navigate(action)
                Log.e("FINISH->>>", data.toString())

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

        viewModel.resourceVoid.observe(viewLifecycleOwner, object : ObserverResource<Array<StockCountResponse.StockCount?>>() {
            override fun onSuccess(data: Array<StockCountResponse.StockCount?>) {
                val action = StockCountFormFragmentDirections.actionStockCountFormFragmentToNavStockCount()
                view.findNavController().navigate(action)
                Log.e("VOID->>>", data.toString())

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

        viewModel.resourceStockCountObject.observe(viewLifecycleOwner, object : ObserverResource<StockCountResponse.StockCount>() {
            override fun onSuccess(data: StockCountResponse.StockCount) {
                viewModel.getStockCountDetails(data.id)

                Log.e("UPDATE->>>", data.toString())

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

        viewModel.resourceProductAvailability.observe(viewLifecycleOwner, object : ObserverResource<Array<ProductAvailabilityResponse.ProductAvailability>>() {
            override fun onSuccess(data: Array<ProductAvailabilityResponse.ProductAvailability>) {
                if(data.isNotEmpty()) {

                    stockCountDetailsAdapter.updateList(data, binId)
                    productId = data[0].product_id
                    itemName.text = data[0].product_name
                    itemField.selectAll()
                    stockCountDetailsAdapter.notifyDataSetChanged()
                    Log.e("AVAIL->>>", data.toString())
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

        //functions
        binHandle()
        itemHandle()
        qtyHandle()

        //listeners
        expandButton.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }

        saveButton.setOnClickListener {
            viewModel.updateStockCount(args.stockCount.id, locationId,  stockCountDetailsAdapter.getUpdateList())
        }

        finishButton.setOnClickListener {
            viewModel.finishStockCount(args.stockCount.id)
        }

        voidButton.setOnClickListener {
            viewModel.voidStockCount(args.stockCount.id)
        }
    }

    private fun binHandle(){
        binField.addRxTextWatcher()
        .debounce(500, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe {
            if (binField.hasFocus()) {
                if (it != null && !TextUtils.isEmpty(it) && it.length >= 3) {
                    viewModel.getBin(it.toString(), locationId, 0, 1)
                } else if (TextUtils.isEmpty(it)) {
                    binId = null
                    binName.text = ""
                }
            }
        }

    }

    // qty listener
    private fun qtyHandle() {
        qtyField.doAfterTextChanged { text ->
            Log.e("TEXT->>>", text.toString())
            stockCountDetailsAdapter.updateQty(productId, binId, text.toString())
        }
    }

    //item text watcher
    private fun itemHandle() {
        itemField.addRxTextWatcher()
            .debounce(500, TimeUnit.MILLISECONDS)
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
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                        } else {
                            productId = stockCountDetails.product_id
                            itemName.text = stockCountDetails.product_name
                            qtyField.setText(stockCountDetails.qty.toString())
                            itemField.selectAll()
                        }
                    }
                }
            }
    }

    //return fancy colours depending on status
    private fun checkStatus(status : String): Int {
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