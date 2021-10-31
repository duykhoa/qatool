package com.github.trambui.qatool.entities

import javax.persistence.*

@Entity
@Table(name = "schema_columns")
class SchemaColumnEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = true)
    val conditionClass: String?,

    @ManyToOne()
    @JoinColumn(name = "schema_id")
    val schema: SchemaEntity?,
)