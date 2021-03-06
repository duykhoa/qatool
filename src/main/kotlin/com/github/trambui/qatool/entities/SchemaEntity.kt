package com.github.trambui.qatool.entities

import javax.persistence.*

@Entity
@Table(name = "schemas")
class SchemaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @OneToMany(mappedBy = "schema", fetch = FetchType.EAGER)
    val columns: List<SchemaColumnEntity>
) {
    private constructor(): this(0, "", emptyList())
}