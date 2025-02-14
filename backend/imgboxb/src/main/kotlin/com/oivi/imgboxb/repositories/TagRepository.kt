package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.entities.TagEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<TagEntity,Long?> {
    fun findByName(name : String) : TagEntity?
}