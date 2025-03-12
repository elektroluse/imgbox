package com.oivi.imgboxb.services

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.TagEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TagService {

    fun create(tagEntity: TagEntity): TagEntity
    fun getIfExistsOrCreate(name: String): TagEntity
    fun list(): List<TagEntity>
    fun listImgboxesWithTag(tag : String) : List<ImgBoxEntity>
    fun listByOccurence(pageable: Pageable): Page<TagEntity>
    fun getIfExistsOrNull(name: String, pageable: Pageable): TagEntity?
}