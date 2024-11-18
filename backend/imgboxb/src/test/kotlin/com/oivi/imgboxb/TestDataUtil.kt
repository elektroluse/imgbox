package com.oivi.imgboxb

import com.oivi.imgboxb.domain.dto.UserDto

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