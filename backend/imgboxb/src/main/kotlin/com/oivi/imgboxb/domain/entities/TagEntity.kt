package com.oivi.imgboxb.domain.entities

import jakarta.persistence.*

@Entity
@Table(name = "tags")
class TagEntity (

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_seq")
    val id : Long?,

    @Column(name = "name", unique = true)
    val name : String,

    @ManyToMany(mappedBy = "tags")
    val imgboxes : MutableSet<ImgBoxEntity>

)
{
    fun addImgbox(imgBoxEntity: ImgBoxEntity){
        imgboxes.add(imgBoxEntity);
    }
    fun removeImgbox(imgBoxEntity: ImgBoxEntity){
        imgboxes.remove(imgBoxEntity);
    }
}