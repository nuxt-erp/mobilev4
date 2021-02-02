package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount

import com.github.htdangkhoa.nexterp.data.repository.availability.ProductAvailabilityRepository
import com.github.htdangkhoa.nexterp.data.repository.availability.ProductAvailabilityRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.locationbin.BinRepository
import com.github.htdangkhoa.nexterp.data.repository.locationbin.BinRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.brand.BrandRepository
import com.github.htdangkhoa.nexterp.data.repository.brand.BrandRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.category.CategoryRepository
import com.github.htdangkhoa.nexterp.data.repository.category.CategoryRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.stockcount.StockCountRepository
import com.github.htdangkhoa.nexterp.data.repository.stockcount.StockCountRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.stocklocator.StockLocatorRepository
import com.github.htdangkhoa.nexterp.data.repository.stocklocator.StockLocatorRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.tag.TagRepository
import com.github.htdangkhoa.nexterp.data.repository.tag.TagRepositoryImp
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityUseCase
import com.github.htdangkhoa.nexterp.domain.locationbin.BinUseCase
import com.github.htdangkhoa.nexterp.domain.brand.BrandUseCase
import com.github.htdangkhoa.nexterp.domain.category.CategoryUseCase
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountUseCase
import com.github.htdangkhoa.nexterp.domain.stocklocator.StockLocatorUseCase
import com.github.htdangkhoa.nexterp.domain.tag.TagUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object StockCountModule {
    val module = module {
        single(override = true) { provideStockCountRepository(get()) }

        single(override = true) { provideStockCountUseCase(get()) }

        single(override = true) { provideBinRepository(get()) }

        single(override = true) { provideBinUseCase(get()) }

        single(override = true) { provideAvailabilityRepository(get()) }

        single(override = true) { provideAvailabilityUseCase(get()) }

        single(override = true) { provideBrandRepository(get()) }

        single(override = true) { provideBrandUseCase(get()) }

        single(override = true) { provideCategoryRepository(get()) }

        single(override = true) { provideCategoryUseCase(get()) }

        single(override = true) { provideTagRepository(get()) }

        single(override = true) { provideTagUseCase(get()) }

        single(override = true) { provideStockLocatorRepository(get()) }

        single(override = true) { provideStockLocatorUseCase(get()) }

        viewModel { StockCountViewModel(get(), get(), get(), get(), get(), get(), get()) }
    }
    private fun provideAvailabilityRepository(apiService: ApiService): ProductAvailabilityRepository =
        ProductAvailabilityRepositoryImp(apiService)

    private fun provideAvailabilityUseCase(availabilityRepository: ProductAvailabilityRepository) = ProductAvailabilityUseCase(availabilityRepository)

    private fun provideStockCountRepository(apiService: ApiService): StockCountRepository =
        StockCountRepositoryImp(apiService)

    private fun provideStockCountUseCase(stockCountRepository: StockCountRepository) = StockCountUseCase(stockCountRepository)

    private fun provideBinRepository(apiService: ApiService): BinRepository =
        BinRepositoryImp(apiService)

    private fun provideBinUseCase(binRepository: BinRepository) = BinUseCase(binRepository)

    private fun provideBrandRepository(apiService: ApiService): BrandRepository =
        BrandRepositoryImp(apiService)

    private fun provideBrandUseCase(availabilityRepository: BrandRepository) = BrandUseCase(availabilityRepository)

    private fun provideCategoryRepository(apiService: ApiService): CategoryRepository =
        CategoryRepositoryImp(apiService)

    private fun provideCategoryUseCase(availabilityRepository: CategoryRepository) = CategoryUseCase(availabilityRepository)

    private fun provideTagRepository(apiService: ApiService): TagRepository =
        TagRepositoryImp(apiService)

    private fun provideTagUseCase(availabilityRepository: TagRepository) = TagUseCase(availabilityRepository)

    private fun provideStockLocatorRepository(apiService: ApiService): StockLocatorRepository =
        StockLocatorRepositoryImp(apiService)

    private fun provideStockLocatorUseCase(availabilityRepository: StockLocatorRepository) = StockLocatorUseCase(availabilityRepository)

}
