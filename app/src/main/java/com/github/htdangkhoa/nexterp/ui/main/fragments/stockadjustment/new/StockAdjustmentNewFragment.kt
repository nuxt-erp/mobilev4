
package com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.new

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.StockAdjustmentViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stock_adjustment_new.*
import kotlinx.android.synthetic.main.fragment_stockcount_new.btnSaveFilters
import kotlinx.android.synthetic.main.fragment_stockcount_new.nameInput
import kotlinx.android.synthetic.main.fragment_stockcount_new.progressCircular
import kotlin.properties.Delegates


class StockAdjustmentNewFragment() : BaseFragment<StockAdjustmentViewModel>(
    StockAdjustmentViewModel::class
) {
    private var adjustmentType = "add to stock"
    private lateinit var sharedPreferences: SharedPreferences
    private var locationId by Delegates.notNull<Int>()

    override val layoutResID: Int
        get() = R.layout.fragment_stock_adjustment_new

    override fun render(view: View, savedInstanceState: Bundle?) {
        initialize(view)
        viewModel.resourceStockAdjustmentObject.observe(
            viewLifecycleOwner,
            object : ObserverResource<StockAdjustmentResponse.StockAdjustment>() {
                override fun onSuccess(data: StockAdjustmentResponse.StockAdjustment) {
                    val action =
                        StockAdjustmentNewFragmentDirections.actionStockAdjustmentNewFragmentToStockAdjustmentFormFragment(
                            data
                        )
                    view.findNavController().navigate(action)
                }

                override fun onError(throwable: Throwable?) {

                    handleError(throwable) {
                        throw it!!

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
            val myNote = noteInput.text.toString()
//            val adjustment_type = adjustmentInput.text.toString()
            val checkedRadioButtonId = radioAdjustmentType.checkedRadioButtonId // Returns View.NO_ID if nothing is checked.
            Log.e("FIELDS->>>", adjustmentType)
            Log.e("FIELDS->>>", myName)
            Log.e("FIELDS->>>", myNote)
            if(myName.isNotEmpty()) {
                viewModel.newStockAdjustment(myName, myNote, adjustmentType, locationId)
            } else {
                showDialog("Error", "You must enter a name for the Stock Adjustment.")
            }
        }
    }
    private fun initialize(view: View) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!

        radioAdjustmentType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_button_add_stock -> {
                    adjustmentType = "Add to stock"
                }
                R.id.radio_button_new_stock -> {
                    adjustmentType = "Replace current stock"
                }
            }
            Log.e("FIELDS->>>", group.toString())
            Log.e("FIELDS->>>", checkedId.toString())
        }
    }
}