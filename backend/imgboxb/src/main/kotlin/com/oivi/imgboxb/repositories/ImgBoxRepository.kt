package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ImgBoxRepository : JpaRepository<ImgBoxEntity,Long?> {

    fun findAllByUserId(userId : Long) : List<ImgBoxEntity>
    fun findByTitleIgnoreCaseContaining(searchCriteria : String) : List<ImgBoxEntity>
}