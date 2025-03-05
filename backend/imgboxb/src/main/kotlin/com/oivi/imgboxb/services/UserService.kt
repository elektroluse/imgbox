package com.oivi.imgboxb.services

import com.oivi.imgboxb.domain.entities.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun create(userEntity : UserEntity) : UserEntity
    fun list() : List<UserEntity>
    fun getUser(username: String) : UserEntity
    fun list(pageable: Pageable): Page<UserEntity>
}