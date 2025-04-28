package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.TagEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ImgBoxRepository : JpaRepository<ImgBoxEntity,Long?>
     {

    fun findAllByUserId(userId : Long) : List<ImgBoxEntity>
    fun findByTitleIgnoreCaseContaining(searchCriteria : String) : List<ImgBoxEntity>
    fun findByTitleIgnoreCaseContaining(pageable: Pageable,searchTerm : String) : Page<ImgBoxEntity>
    fun findAllByUserId(pageable: Pageable, id : Long) : Page<ImgBoxEntity>
    fun findAllByTags (pageable: Pageable, tagEntity : TagEntity) : Page<ImgBoxEntity>
}