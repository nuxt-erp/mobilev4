package com.github.htdangkhoa.nexterp.data.repository.tag

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.bin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse

interface TagRepository : BaseRepository {
    suspend fun getTag(list: Int): Result<Array<TagResponse.Tag>>
}
