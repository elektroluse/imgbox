package com.oivi.imgboxb.domain.dto

data class ImgboxWithFileDto(

    val imgboxDto : ImgBoxDto,
    val imageFile : ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImgboxWithFileDto

        if (imgboxDto != other.imgboxDto) return false
        if (!imageFile.contentEquals(other.imageFile)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imgboxDto.hashCode()
        result = 31 * result + imageFile.contentHashCode()
        return result
    }
}
