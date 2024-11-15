package com.oivi.imgboxb.domain

import jakarta.persistence.*

import java.sql.Timestamp

@Entity(name = "imgboxes")
data class ImgBox(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    val id : Long?,

    @Column(name = "title")
    val title : String,

    @Column(name = "description")
    val description : String,

    @Column(name = "tags")
    var tags : MutableSet<String>,

    @ManyToOne(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "user_id")
    val user : User,

    @Column(name = "created_at")
    val createdAt : Timestamp
    )
