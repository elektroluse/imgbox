package com.oivi.imgboxb.domain.entities

import jakarta.persistence.*

import java.sql.Timestamp

@Entity(name = "imgboxes")
data class ImgBoxEntity(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imgbox_id_seq")
    val id : Long?,

    @Column(name = "title")
    val title : String,

    @Column(name = "description")
    val description : String,

    @Column(name = "tags")
    var tags : MutableSet<String>,

    @Column(name = "file_url")
    var fileUrl : String,

    @ManyToOne(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "user_id")
    val user : UserEntity,

    @Column(name = "created_at")
    val createdAt : Timestamp
    )
