package com.oivi.imgboxb

import com.oivi.imgboxb.domain.dto.UserDto
import com.oivi.imgboxb.domain.entities.UserEntity

fun testUserDtoA(id : Long? = null) =
     UserDto(
        id = id,
        username = "elektroluse"
    )

fun testUserDtoB(id : Long? = null) =
    UserDto(
        id = id,
        username = "testbrukerb"
    )

fun testUserEntityA(id : Long? = null) =
    UserEntity(
        id = id,
        username = "elektroluse"
    )