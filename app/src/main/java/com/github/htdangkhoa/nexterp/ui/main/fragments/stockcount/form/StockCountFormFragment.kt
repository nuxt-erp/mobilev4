package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.form

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
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.ReceivingRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.adapters.StockCountRecyclerAdapter
import com.github.htdangkhoa.nexterp.ui.components.addRxTextWatcher
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingViewModel
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
import kotlin.properties.Delegates

class StockCountFormFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class) {
    private lateinit var stockCountDetailsAdapter: StockCountRecyclerAdapter
    private val args: StockCountFormFragmentArgs by navArgs()
    private lateinit var sharedPreferences: SharedPreferences
    private var productId by Delegates.notNull<Int>()
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
                    itemField.setText("")
                    itemField.clearFocus()
                    itemName.text = it.product_name
                    qtyField.setText(it.qty.toString())
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
//        stockStatus.setTextColor(ContextCompat.getColor(view.context, checkStatus(args.stockCount.status_name)))

        //functions
//        itemHandle()
//        qtyHandle()

        //listeners
        expandButton.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }

        saveButton.setOnClickListener {
        }

        finishButton.setOnClickListener {
        }

        voidButton.setOnClickListener {
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