package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.entities.TagEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TagRepository : JpaRepository<TagEntity,Long?> {
    fun findByName(name : String) : TagEntity?

    @Query(value = "SELECT t FROM TagEntity t ORDER BY size(t.imgboxes) desc")
    fun findAllOrderedByOccurrence(pageable: Pageable) : Page<TagEntity>
}