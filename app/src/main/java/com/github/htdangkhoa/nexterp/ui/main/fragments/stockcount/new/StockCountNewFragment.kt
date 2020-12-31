
package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.new

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseFragment
import com.github.htdangkhoa.nexterp.data.remote.bin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.adapters.CheckableSpinnerAdapter
import com.github.htdangkhoa.nexterp.ui.adapters.CheckableSpinnerAdapter.SpinnerItem
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountViewModel
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list.StockCountListFragmentDirections
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_stockcount_list.progressCircular
import kotlinx.android.synthetic.main.fragment_stockcount_new.*


class StockCountNewFragment() : BaseFragment<StockCountViewModel>(
    StockCountViewModel::class
) {
    private val categorySpinnerItems: MutableList<SpinnerItem<CategoryResponse.Category>> = ArrayList()
    private val categorySelectedItems: MutableSet<CategoryResponse.Category> = HashSet()

    private val binSpinnerItems: MutableList<SpinnerItem<BinResponse.Bin>> = ArrayList()
    private val binSelectedItems: MutableSet<BinResponse.Bin> = HashSet()

    private val brandSpinnerItems: MutableList<SpinnerItem<BrandResponse.Brand>> = ArrayList()
    private val brandSelectedItems: MutableSet<BrandResponse.Brand> = HashSet()

    private val stockLocatorSpinnerItems: MutableList<SpinnerItem<StockLocatorResponse.StockLocator>> = ArrayList()
    private val stockLocatorSelectedItems: MutableSet<StockLocatorResponse.StockLocator> = HashSet()

    private val tagSpinnerItems: MutableList<SpinnerItem<TagResponse.Tag>> = ArrayList()
    private val tagSelectedItems: MutableSet<TagResponse.Tag> = HashSet()

    override val layoutResID: Int
        get() = R.layout.fragment_stockcount_new

    override fun render(view: View, savedInstanceState: Bundle?) {

        viewModel.resourceTags.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<TagResponse.Tag>>() {
                override fun onSuccess(data: Array<TagResponse.Tag>) {
                    data.forEach {
                        tagSpinnerItems.add(SpinnerItem<TagResponse.Tag>(it, it.name))
                    }
                    val adapter: CheckableSpinnerAdapter<*> =
                        CheckableSpinnerAdapter<TagResponse.Tag>(
                            context!!,
                            "Tag",
                            tagSpinnerItems,
                            tagSelectedItems
                        )
                    tagSpinner.setAdapter(adapter)
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

        viewModel.resourceBrands.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<BrandResponse.Brand>>() {
                override fun onSuccess(data: Array<BrandResponse.Brand>) {
                    data.forEach {
                        brandSpinnerItems.add(SpinnerItem<BrandResponse.Brand>(it, it.name))
                    }
                    val adapter: CheckableSpinnerAdapter<*> =
                        CheckableSpinnerAdapter<BrandResponse.Brand>(
                            context!!,
                            "Brand",
                            brandSpinnerItems,
                            brandSelectedItems
                        )
                    brandSpinner.setAdapter(adapter)
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

        viewModel.resourceCategories.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<CategoryResponse.Category>>() {
                override fun onSuccess(data: Array<CategoryResponse.Category>) {
                    data.forEach {
                        categorySpinnerItems.add(SpinnerItem<CategoryResponse.Category>(it, it.name))
                    }
                    val adapter: CheckableSpinnerAdapter<*> =
                        CheckableSpinnerAdapter<CategoryResponse.Category>(
                            context!!,
                            "Category",
                            categorySpinnerItems,
                            categorySelectedItems
                        )
                    categorySpinner.setAdapter(adapter)
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
                    data.forEach {
                        binSpinnerItems.add(SpinnerItem<BinResponse.Bin>(it, it.name))
                    }
                    val adapter: CheckableSpinnerAdapter<*> =
                        CheckableSpinnerAdapter<BinResponse.Bin>(
                            context!!,
                            "Bin",
                            binSpinnerItems,
                            binSelectedItems
                        )
                    binSpinner.setAdapter(adapter)
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

        viewModel.resourceStockLocators.observe(
            viewLifecycleOwner,
            object : ObserverResource<Array<StockLocatorResponse.StockLocator>>() {
                override fun onSuccess(data: Array<StockLocatorResponse.StockLocator>) {
                    data.forEach {
                        stockLocatorSpinnerItems.add(SpinnerItem<StockLocatorResponse.StockLocator>(it, it.name))
                    }
                    val adapter: CheckableSpinnerAdapter<*> =
                        CheckableSpinnerAdapter<StockLocatorResponse.StockLocator>(
                            context!!,
                            "Stock Locator",
                            stockLocatorSpinnerItems,
                            stockLocatorSelectedItems
                        )
                    stockLocatorSpinner.setAdapter(adapter)
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
//                val action = StockCount.actionNavStockCountToStockCountNewFragment()
//                view.findNavController().navigate(action)

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

        viewModel.getTag(1)
        viewModel.getBrand(1, 1)
        viewModel.getCategory(1, 1)
        viewModel.getStockLocator(1, 1)
        viewModel.getBin(null, null, 1, 1)

        btnSaveFilters.setOnClickListener {
            val myName = nameInput.text.toString()
            if(myName.isNotEmpty()) {
                viewModel.newStockCount(createMap(), myName)
            } else {
                showDialog("Error", "You must enter a name for the Stock Count.")
            }
        }
    }

    private fun createMap() : HashMap<String, List<Int>> {
        val map =  HashMap<String, List<Int>>()

        val binIds : MutableList<Int> = ArrayList()
        val brandIds : MutableList<Int> = ArrayList()
        val categoryIds : MutableList<Int> = ArrayList()
        val tagIds : MutableList<Int> = ArrayList()
        val stockLocatorIds : MutableList<Int> = ArrayList()

        stockLocatorSelectedItems.forEach {
            stockLocatorIds += it.id
        }
        binSelectedItems.forEach {
            binIds += it.id
        }
        brandSelectedItems.forEach {
            brandIds += it.id
        }
        categorySelectedItems.forEach {
            categoryIds += it.id
        }
        tagSelectedItems.forEach {
            tagIds += it.id
        }

        map["bin_ids"] = binIds
        map["brand_ids"] = brandIds
        map["category_ids"] = categoryIds
        map["stock_locator_ids"] = stockLocatorIds
        map["tag_ids"] = tagIds

        return map
    }

}