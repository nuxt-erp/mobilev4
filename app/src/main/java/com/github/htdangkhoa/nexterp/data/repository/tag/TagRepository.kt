package com.github.htdangkhoa.nexterp.data.repository.tag

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse

interface TagRepository : BaseRepository {
    suspend fun getTag(list: Int, per_page: Int?): Result<Array<TagResponse.Tag>>
}
