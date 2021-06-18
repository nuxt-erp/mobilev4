package com.github.htdangkhoa.nexterp.domain.tag
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.stocklocator.StockLocatorRepository
import com.github.htdangkhoa.nexterp.data.repository.tag.TagRepository

class TagUseCase(
    repository: TagRepository
) : BaseUseCase<TagRepository, TagParam>(repository) {
    override suspend fun buildUseCase(params: TagParam?): Result<*> {
        return when (params?.type) {
            TagParam.Type.GET_TAG -> repository.getTag(params.list, params.per_page)
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
