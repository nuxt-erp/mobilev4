package com.github.htdangkhoa.nexterp.domain.category
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.category.CategoryRepository

class CategoryUseCase(
    repository: CategoryRepository
) : BaseUseCase<CategoryRepository, CategoryParam>(repository) {
    override suspend fun buildUseCase(params: CategoryParam?): Result<*> {
        return when (params?.type) {
            CategoryParam.Type.GET_CATEGORIES -> repository.getCategory(params.list, params.is_enabled)
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
