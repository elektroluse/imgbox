package com.oivi.imgboxb.services

import com.oivi.imgboxb.domain.entities.UserEntity

interface UserService {

    fun save(userEntity : UserEntity) : UserEntity
    fun list() : List<UserEntity>
}