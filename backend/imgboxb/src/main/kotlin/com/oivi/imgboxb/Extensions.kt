package com.oivi.imgboxb

import com.oivi.imgboxb.domain.dto.UserDto
import com.oivi.imgboxb.domain.entities.UserEntity

fun UserEntity.toUserDto() = UserDto(
        id = this.id,
        username = this.username
    )

fun UserDto.toUserEntity() = UserEntity(
    id = this.id,
    username = this.username
)