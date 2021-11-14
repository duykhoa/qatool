package com.github.trambui.qatool.entities

import com.github.trambui.qatool.utils.HashMapConverter
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

    @Column(nullable = true, columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter::class)
    val arguments: Map<String, Object?>?,

    @ManyToOne()
    @JoinColumn(name = "schema_id")
    val schema: SchemaEntity?,
) {
    private constructor(): this(0, "", "", "", null, null)
}