
package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.new

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.remote.locationbin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.CheckableSpinnerAdapter
import com.github.htdangkhoa.nexterp.ui.adapters.CheckableSpinnerAdapter.SpinnerItem
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stockcount_list.progressCircular
import kotlinx.android.synthetic.main.fragment_stockcount_new.*
import timber.log.Timber
import timber.log.Timber.log
import kotlin.properties.Delegates


class StockCountNewFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class
) {
    private val categorySpinnerItems:  MutableList<KeyPairBoolData> = ArrayList()
    private val binSpinnerItems:  MutableList<KeyPairBoolData> = ArrayList()
    private val brandSpinnerItems:  MutableList<KeyPairBoolData> = ArrayList()
    private val stockLocatorSpinnerItems:  MutableList<KeyPairBoolData> = ArrayList()
    private val tagSpinnerItems:  MutableList<KeyPairBoolData> = ArrayList()
    private lateinit var sharedPreferences: SharedPreferences
    private var locationId by Delegates.notNull<Int>()

    override val layoutResID: Int
        get() = R.layout.fragment_stockcount_new

    override fun render(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        locationId = sharedPreferences.getString("location", null)?.toInt()!!

        viewModel.resourceTags.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<TagResponse.Tag>>() {
                override fun onSuccess(data: Array<TagResponse.Tag>) {
                    tagSpinner.isSearchEnabled = true
                    tagSpinner.setSearchHint("Select bins")
                    tagSpinner.setEmptyTitle("Not Data Found!")
                    tagSpinner.isShowSelectAllButton = true
                    tagSpinner.setClearText("Cancel")
                    data.forEach {
                        val h = KeyPairBoolData()
                        h.id = it.id.toLong()
                        h.name = it.name
                        h.isSelected = false;
                        tagSpinnerItems.add(h)
                    }
                    tagSpinner.setItems(
                        tagSpinnerItems.toList()
                    ) { items ->
                        for (i in items.indices) {
                            if (items[i].isSelected) {
                                Log.i(
                                    "Category",
                                    i.toString() + " : " + items[i].name + " : " + items[i].isSelected
                                )
                            }
                        }
                    }
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

        viewModel.resourceBrands.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<BrandResponse.Brand>>() {
                override fun onSuccess(data: Array<BrandResponse.Brand>) {
                    brandSpinner.isSearchEnabled = true
                    brandSpinner.setSearchHint("Select bins")
                    brandSpinner.setEmptyTitle("Not Data Found!")
                    brandSpinner.isShowSelectAllButton = true
                    brandSpinner.setClearText("Cancel")
                    data.forEach {
                        val h = KeyPairBoolData()
                        h.id = it.id.toLong()
                        h.name = it.name
                        h.isSelected = false;
                        brandSpinnerItems.add(h)
                    }
                    brandSpinner.setItems(
                        brandSpinnerItems.toList()
                    ) { items ->
                        for (i in items.indices) {
                            if (items[i].isSelected) {
                                Log.i(
                                    "Category",
                                    i.toString() + " : " + items[i].name + " : " + items[i].isSelected
                                )
                            }
                        }
                    }
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

        viewModel.resourceCategories.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<CategoryResponse.Category>>() {
                override fun onSuccess(data: Array<CategoryResponse.Category>) {
                    categorySpinner.isSearchEnabled = true
                    categorySpinner.setSearchHint("Select bins")
                    categorySpinner.setEmptyTitle("Not Data Found!")
                    categorySpinner.isShowSelectAllButton = true
                    categorySpinner.setClearText("Cancel")
                    data.forEach {
                        val h = KeyPairBoolData()
                        h.id = it.id.toLong()
                        h.name = it.name
                        h.isSelected = false;
                        categorySpinnerItems.add(h)
                    }
                    categorySpinner.setItems(
                        categorySpinnerItems.toList()
                    ) { items ->
                        for (i in items.indices) {
                            if (items[i].isSelected) {
                                Log.i(
                                    "Category",
                                    i.toString() + " : " + items[i].name + " : " + items[i].isSelected
                                )
                            }
                        }
                    }
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

        viewModel.resourceBins.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<BinResponse.Bin>>() {
                override fun onSuccess(data: Array<BinResponse.Bin>) {
                    binSpinner.isSearchEnabled = true
                    binSpinner.setSearchHint("Select bins")
                    binSpinner.setEmptyTitle("Not Data Found!")
                    binSpinner.isShowSelectAllButton = true
                    binSpinner.setClearText("Cancel")
                    data.forEach {
                        val h = KeyPairBoolData()
                        h.id = it.id.toLong()
                        h.name = it.name
                        h.isSelected = false;
                        binSpinnerItems.add(h)
                    }
                    binSpinner.setItems(
                        binSpinnerItems.toList()
                    ) { items ->
                        for (i in items.indices) {
                            if (items[i].isSelected) {
                                Log.i(
                                    "BIN",
                                    i.toString() + " : " + items[i].name + " : " + items[i].isSelected
                                )
                            }
                        }
                    }
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

        viewModel.resourceStockLocators.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<StockLocatorResponse.StockLocator>>() {
                override fun onSuccess(data: Array<StockLocatorResponse.StockLocator>) {
                    data.forEach {
                        stockLocatorSpinner.isSearchEnabled = true
                        stockLocatorSpinner.setSearchHint("Select bins")
                        stockLocatorSpinner.setEmptyTitle("Not Data Found!")
                        stockLocatorSpinner.isShowSelectAllButton = true
                        stockLocatorSpinner.setClearText("Cancel")
                        data.forEach {
                            val h = KeyPairBoolData()
                            h.id = it.id.toLong()
                            h.name = it.name
                            h.isSelected = false;
                            stockLocatorSpinnerItems.add(h)
                        }
                        stockLocatorSpinner.setItems(
                            stockLocatorSpinnerItems.toList()
                        ) { items ->
                            for (i in items.indices) {
                                if (items[i].isSelected) {
                                    Log.i(
                                        "Stock Locator",
                                        i.toString() + " : " + items[i].name + " : " + items[i].isSelected
                                    )
                                }
                            }
                        }
                    }
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

        viewModel.resourceStockCountObject.observe(
            viewLifecycleOwner,
            object : ObserverResource<StockCountResponse.StockCount>() {
                override fun onSuccess(data: StockCountResponse.StockCount) {
                    val action =
                        StockCountNewFragmentDirections.actionStockCountNewFragmentToStockCountFormFragment(
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

        viewModel.getTag(1)
        viewModel.getBrand(1, 1)
        viewModel.getCategory(1, 1)
        viewModel.getStockLocator(1, 1)
        viewModel.getBin(null, null, 1, 1)

        btnSaveFilters.setOnClickListener {
            val myName = nameInput.text.toString()
            if(myName.isNotEmpty()) {
                createMap()
                viewModel.newStockCount(createMap(), myName, location_id = locationId)
            } else {
                showDialog("Error", "You must enter a name for the Stock Count.")
            }
        }
    }

    private fun createMap() : HashMap<String, List<Long>> {
        val map =  HashMap<String, List<Long>>()
        map["bin_ids"] = binSpinner.selectedIds
        map["brand_ids"] = brandSpinner.selectedIds
        map["category_ids"] = categorySpinner.selectedIds
        map["stock_locator_ids"] = stockLocatorSpinner.selectedIds
        map["tag_ids"] = tagSpinner.selectedIds
        return map
    }

}