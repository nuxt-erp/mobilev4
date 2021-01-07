package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.bin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.UpdateStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityParam
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityUseCase
import com.github.htdangkhoa.nexterp.domain.bin.BinParam
import com.github.htdangkhoa.nexterp.domain.bin.BinUseCase
import com.github.htdangkhoa.nexterp.domain.brand.BrandParam
import com.github.htdangkhoa.nexterp.domain.brand.BrandUseCase
import com.github.htdangkhoa.nexterp.domain.category.CategoryParam
import com.github.htdangkhoa.nexterp.domain.category.CategoryUseCase
import com.github.htdangkhoa.nexterp.domain.receiving.ReceivingParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountUseCase
import com.github.htdangkhoa.nexterp.domain.stocklocator.StockLocatorParam
import com.github.htdangkhoa.nexterp.domain.stocklocator.StockLocatorUseCase
import com.github.htdangkhoa.nexterp.domain.tag.TagParam
import com.github.htdangkhoa.nexterp.domain.tag.TagUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list.StockCountListFragmentDirections

class StockCountViewModel(
    private val stockCountUseCase: StockCountUseCase,
    private val binUseCase: BinUseCase,
    private val availabilityUseCase: ProductAvailabilityUseCase,
    private val brandUseCase: BrandUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val tagUseCase: TagUseCase,
    private val stockLocatorUseCase: StockLocatorUseCase

) : ViewModel() {
    val resourceStockCount = liveDataOf<Resource<Array<StockCountResponse.StockCount>>>()
    val resourceBins = liveDataOf<Resource<Array<BinResponse.Bin>>>()
    val resourceStockCountObject = liveDataOf<Resource<StockCountResponse.StockCount>>()
    val resourceFinish = liveDataOf<Resource<Array<StockCountResponse.StockCount?>>>()
    val resourceVoid =  liveDataOf<Resource<Array<StockCountResponse.StockCount?>>>()
    val resourceStockCountDetails = liveDataOf<Resource<Array<StockCountDetailResponse.StockCountDetail>>>()
    val resourceProductAvailability = liveDataOf<Resource<Array<ProductAvailabilityResponse.ProductAvailability>>>()
    val resourceBrands = liveDataOf<Resource<Array<BrandResponse.Brand>>>()
    val resourceCategories = liveDataOf<Resource<Array<CategoryResponse.Category>>>()
    val resourceTags = liveDataOf<Resource<Array<TagResponse.Tag>>>()
    val resourceStockLocators = liveDataOf<Resource<Array<StockLocatorResponse.StockLocator>>>()
    val resourceDeleteStockCountDetails = liveDataOf<Resource<Array<StockCountDetailResponse.StockCountDetail?>>>()

    fun onStockCountClick(view : View, stockCount: StockCountResponse.StockCount) {
        val action = StockCountListFragmentDirections.actionNavStockCountToStockCountFormFragment(stockCount)
        view.findNavController().navigate(action)

    }
    fun getStockCount() {
        resourceStockCount.postValue(Resource.loading())

        stockCountUseCase.execute<Array<StockCountResponse.StockCount>> (StockCountParam(StockCountParam.Type.GET_STOCK_COUNT)) {
            onComplete {
                resourceStockCount.postValue(Resource.success(it))
            }

            onError {
                resourceStockCount.postValue(Resource.error(it))
                throw it

            }

            onCancel {
                resourceStockCount.postValue(Resource.error(it))
            }
        }
    }
    fun getStockCountDetails(id: Int) {
        resourceStockCountDetails.postValue(Resource.loading())
        stockCountUseCase.execute<Array<StockCountDetailResponse.StockCountDetail>> (
            StockCountParam(
                StockCountParam.Type.GET_STOCK_COUNT_DETAILS, id)
        ) {
            onComplete {
                resourceStockCountDetails.postValue(Resource.success(it))
            }

            onError {
                resourceStockCountDetails.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceStockCountDetails.postValue(Resource.error(it))
            }
        }
    }

    fun getBin(barcode: String?,
               location_id: Int?,
               list: Int,
               is_enabled: Int) {
        resourceBins.postValue(Resource.loading())
        binUseCase.execute<Array<BinResponse.Bin>> (
            BinParam(
                BinParam.Type.GET_BINS, location_id, barcode, list, is_enabled)
        ) {
            onComplete {
                resourceBins.postValue(Resource.success(it))
            }

            onError {
                resourceBins.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceBins.postValue(Resource.error(it))
            }
        }
    }

    fun getBrand(list: Int,
                 is_enabled: Int) {
        resourceBrands.postValue(Resource.loading())
        brandUseCase.execute<Array<BrandResponse.Brand>> (
            BrandParam(
                BrandParam.Type.GET_BRANDS, list, is_enabled)
        ) {
            onComplete {
                resourceBrands.postValue(Resource.success(it))
            }

            onError {
                resourceBrands.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceBrands.postValue(Resource.error(it))
            }
        }
    }

    fun getCategory(list: Int,
                 is_enabled: Int) {
        resourceCategories.postValue(Resource.loading())
        categoryUseCase.execute<Array<CategoryResponse.Category>> (
            CategoryParam(
                CategoryParam.Type.GET_CATEGORIES, list, is_enabled)
        ) {
            onComplete {
                resourceCategories.postValue(Resource.success(it))
            }

            onError {
                resourceCategories.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceCategories.postValue(Resource.error(it))
            }
        }
    }

    fun getTag(list: Int) {
        resourceTags.postValue(Resource.loading())
        tagUseCase.execute<Array<TagResponse.Tag>> (
            TagParam(
                TagParam.Type.GET_TAG, list)
        ) {
            onComplete {
                resourceTags.postValue(Resource.success(it))
            }

            onError {
                resourceTags.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceTags.postValue(Resource.error(it))
            }
        }
    }

    fun getStockLocator(list: Int,
                    is_enabled: Int) {
        resourceStockLocators.postValue(Resource.loading())
        stockLocatorUseCase.execute<Array<StockLocatorResponse.StockLocator>> (
            StockLocatorParam(
                StockLocatorParam.Type.GET_STOCK_LOCATOR, list, is_enabled)
        ) {
            onComplete {
                resourceStockLocators.postValue(Resource.success(it))
            }

            onError {
                resourceStockLocators.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceStockLocators.postValue(Resource.error(it))
            }
        }
    }

    fun getProductAvailability(
           searchable: String,
           location_id: Int,
           brand_ids: List<Long>? = null,
           tag_ids: List<Long>? = null,
           bin_ids: List<Long>? = null,
           category_ids: List<Long>? = null,
           stock_locator_ids: List<Long>? = null) {
        resourceProductAvailability.postValue(Resource.loading())
        availabilityUseCase.execute<Array<ProductAvailabilityResponse.ProductAvailability>> (
            ProductAvailabilityParam(
                ProductAvailabilityParam.Type.GET_PRODUCT_AVAILABILITY, searchable, location_id, brand_ids, tag_ids, bin_ids, category_ids, stock_locator_ids)
        )
        {
            onComplete {
                resourceProductAvailability.postValue(Resource.success(it))
            }

            onError {
                resourceProductAvailability.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceProductAvailability.postValue(Resource.error(it))
            }
        }
    }
    fun updateStockCount(id: Int, locationId: Int, list_products: List<StockCountDetailResponse.StockCountDetail>) {
        val request = UpdateStockCountRequest(
            list_products = list_products
        )

        resourceStockCountObject.postValue(Resource.loading())

        stockCountUseCase.execute<StockCountResponse.StockCount>(StockCountParam(StockCountParam.Type.UPDATE_STOCK_COUNT, id, request)) {
            onComplete {
                resourceStockCountObject.postValue(Resource.success(it))
            }

            onError {
                resourceStockCountObject.postValue(Resource.error(it))
            }

            onCancel {
                resourceStockCountObject.postValue(Resource.error(it))
            }
        }
    }


    fun newStockCount(stockCountFilters:  HashMap<String, List<Int>>, name: String) {
        val request = NewStockCountRequest(stock_count_filters = stockCountFilters, name = name)

        resourceStockCountObject.postValue(Resource.loading())

        stockCountUseCase.execute<StockCountResponse.StockCount>(StockCountParam(StockCountParam.Type.NEW_STOCK_COUNT, request)) {
            onComplete {
                resourceStockCountObject.postValue(Resource.success(it))
            }

            onError {
                resourceStockCountObject.postValue(Resource.error(it))
            }

            onCancel {
                resourceStockCountObject.postValue(Resource.error(it))
            }
        }
    }

    fun voidStockCount(id: Int) {
        resourceVoid.postValue(Resource.loading())

        stockCountUseCase.execute<Array<StockCountResponse.StockCount?>>(StockCountParam(StockCountParam.Type.VOID_STOCK_COUNT, id)) {
            onComplete {
                resourceVoid.postValue(Resource.success(it))
            }

            onError {
                resourceVoid.postValue(Resource.error(it))
            }

            onCancel {
                resourceVoid.postValue(Resource.error(it))
            }
        }
    }

    fun finishStockCount(id: Int) {
        resourceFinish.postValue(Resource.loading())

        stockCountUseCase.execute<Array<StockCountResponse.StockCount?>>(StockCountParam(StockCountParam.Type.FINISH_STOCK_COUNT, id)) {
            onComplete {
                resourceFinish.postValue(Resource.success(it))
            }

            onError {
                resourceFinish.postValue(Resource.error(it))
            }

            onCancel {
                resourceFinish.postValue(Resource.error(it))
            }
        }
    }

    fun deleteStockCountDetail(id: Int) {
        resourceDeleteStockCountDetails.postValue(Resource.loading())

        stockCountUseCase.execute<Array<StockCountDetailResponse.StockCountDetail?>>(
            StockCountParam(
                StockCountParam.Type.DELETE_STOCK_COUNT_DETAIL, id)
        ) {
            onComplete {
                resourceDeleteStockCountDetails.postValue(Resource.success(it))
            }

            onError {
                resourceDeleteStockCountDetails.postValue(Resource.error(it))
            }

            onCancel {
                resourceDeleteStockCountDetails.postValue(Resource.error(it))
            }
        }
    }
}
