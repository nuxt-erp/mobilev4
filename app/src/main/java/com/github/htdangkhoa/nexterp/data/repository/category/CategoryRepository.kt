package com.github.htdangkhoa.nexterp.data.repository.category

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse

interface CategoryRepository : BaseRepository {
    suspend fun getCategory(list: Int, is_enabled: Int, per_page: Int?): Result<Array<CategoryResponse.Category>>
}
