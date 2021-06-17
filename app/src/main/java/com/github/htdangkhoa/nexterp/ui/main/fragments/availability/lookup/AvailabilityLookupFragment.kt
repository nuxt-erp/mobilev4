package com.github.htdangkhoa.nexterp.ui.main.fragments.availability.lookup
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.availability.AvailabilityResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.ProductLookupAdapter
import com.github.htdangkhoa.nexterp.ui.components.addRxTextWatcher
import com.github.htdangkhoa.nexterp.ui.main.fragments.availability.AvailabilityViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_product_lookup.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class AvailabilityLookupFragment() : BaseFragment<AvailabilityViewModel>(
    AvailabilityViewModel::class
) {
    private var locationId by Delegates.notNull<Int>()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var productLookupAdapter: ProductLookupAdapter
    private val availabilityList = mutableListOf<AvailabilityResponse.Availability>()
    override val layoutResID: Int
        get() = R.layout.fragment_product_lookup

    override fun render(view: View, savedInstanceState: Bundle?) {
        initialize(view)
        viewModel.resourceAvailability.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<AvailabilityResponse.Availability>>() {
                override fun onSuccess(data: Array<AvailabilityResponse.Availability>) {
                    if(data.isNullOrEmpty().not()) {
                        productLookupAdapter.updateList(data)
                        productLookupAdapter.notifyDataSetChanged()
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

    private fun initialize(view: View) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!
        productLookupAdapter = ProductLookupAdapter(availabilityList)

        productLookupDetails.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productLookupAdapter
        }
        itemHandle()
    }

    //item text watcher
    private fun itemHandle() {
        itemField.addRxTextWatcher()
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (itemField.hasFocus()) {
                    if (it != null && !TextUtils.isEmpty(it) && it.trim().length >= 3) {
                        viewModel.getAvailability(it, locationId)
                    }
                }
            }
    }
}