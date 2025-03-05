package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity,Long?>,
    PagingAndSortingRepository<UserEntity, Long?> {

    fun findByUsername(username : String?) : UserEntity?
    fun existsByUsername(username : String?) : Boolean
}