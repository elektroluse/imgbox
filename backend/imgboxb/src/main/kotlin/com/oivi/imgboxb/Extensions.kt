package com.oivi.imgboxb

import com.oivi.imgboxb.domain.dto.*
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.RoleEntity
import com.oivi.imgboxb.domain.entities.TagEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.repositories.RoleRepository
import org.springframework.security.crypto.password.PasswordEncoder
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun UserEntity.toUserDto() = UserDto(
        id = this.id,
        username = this.username,
        password = this.password
    )

fun UserEntity.toUserProfile() = UserProfile(
    username = this.username,
    id = this.id
)

fun UserDto.toUserEntity() = UserEntity(
    id = this.id,
    username = this.username,
    password = this.password
)

fun RegistrationForm.toUserEntity(encodedPassword : String) = UserEntity(
    id = null,
    username = this.username,
    password = encodedPassword
)

fun UserEntity.toUserDtoOmitP() = UserDto(
    id = this.id,
    username = this.username,
    password = ""
)

fun ImgBoxEntity.toImgBoxDto() = ImgBoxDto(

    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags.toMutableSetString(),
    user = this.user.toUserDto(),
    fileUrl = this.fileUrl,
    createdAt = this.createdAt

)

fun ImgBoxEntity.toImgBoxDtoSafe() = ImgBoxDto(

    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags.toMutableSetString() ,
    user = this.user.toUserDtoOmitP(),
    fileUrl = this.fileUrl,
    createdAt = this.createdAt

)

fun ImgBoxEntity.toImgBoxDtoKeyForm() = ImgBoxDto(
    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags.toMutableSetString(),
    user = this.user.toUserDtoOmitP(),
    fileUrl = this.fileUrl.substringAfter("imgboxes/"),
    createdAt = this.createdAt
)


fun ImgBoxDto.toImgBoxEntity() = ImgBoxEntity(

    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags.toMutableSetTagEntity(),
    fileUrl = this.fileUrl,
    user = this.user.toUserEntity(),
    createdAt = this.createdAt

)

fun ImgboxFormDto.toImgBoxEntity(userEntity : UserEntity) = ImgBoxEntity(
    id = null,
    title = this.title,
    description = this.description,
    tags = this.tags.toMutableSetTagEntity(),
    fileUrl = "",
    user = userEntity,
    createdAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
)

fun MutableSet<TagEntity>.toMutableSetString() : MutableSet<String>{
    return (this.map{it.toStringOnly()}).toMutableSet()
}

fun String.toTagEntity() : TagEntity = TagEntity(
    id = null,
    name = this,
    imgboxes = mutableSetOf()
)
fun TagEntity.toStringOnly() : String {
    return this.name;
}
fun TagEntity.toTagCountDto() = TagCountDto(
    name = this.name,
    count = this.imgboxes.size
)

fun MutableSet<String>.toMutableSetTagEntity() : MutableSet<TagEntity>{
    return (this.map{ it.toTagEntity()}).toMutableSet()
}