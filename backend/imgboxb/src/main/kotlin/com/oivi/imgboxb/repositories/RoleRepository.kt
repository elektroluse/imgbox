package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.entities.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleEntity,Int?> {
     fun findByName(name : String) : RoleEntity?

}