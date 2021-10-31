package com.github.trambui.qatool.controllers

import com.github.trambui.qatool.dto.SchemaDto
import com.github.trambui.qatool.entities.SchemaColumnEntity
import com.github.trambui.qatool.entities.SchemaEntity
import com.github.trambui.qatool.repositories.SchemaColumnDao
import com.github.trambui.qatool.repositories.SchemaDao
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schema")
class SchemaController {
    private var schemaColumnDao: SchemaColumnDao
    private var schemaDao: SchemaDao

    constructor(schemaDao: SchemaDao, schemaColumnDao: SchemaColumnDao) {
        this.schemaDao = schemaDao
        this.schemaColumnDao = schemaColumnDao
    }

    @GetMapping("/")
    fun index(): String {
        return "test first";
    }

    @PostMapping("/")
    fun create(@RequestBody schemaDto: SchemaDto): SchemaEntity {

        val schema = SchemaEntity(0, name = schemaDto.name, columns = listOf())
        val createSchema = schemaDao.save(schema)

        val columns = schemaDto.columns.map { c ->
            SchemaColumnEntity(
                0,
                name = c.name,
                type = c.type,
                conditionClass = c.conditionClass,
                schema = schema
            )
        }

        val createColumns = schemaColumnDao.saveAll(columns)

        return createSchema
    }

    @GetMapping("/{name}")
    fun get(@PathVariable name: String): Int {
        val schemas = this.schemaDao.findByName(name)

        return schemas.size
    }
}