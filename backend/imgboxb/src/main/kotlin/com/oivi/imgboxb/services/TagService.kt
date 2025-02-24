package com.oivi.imgboxb.services

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.TagEntity

interface TagService {

    fun create(tagEntity: TagEntity): TagEntity
    fun getIfExistsOrCreate(name: String): TagEntity
    fun list(): List<TagEntity>
    fun listImgboxesWithTag(tag : String) : List<ImgBoxEntity>
}