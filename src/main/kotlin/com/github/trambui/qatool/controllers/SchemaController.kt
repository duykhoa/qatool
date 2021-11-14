package com.github.trambui.qatool.controllers

import com.github.trambui.qatool.dto.ColumnRespDto
import com.github.trambui.qatool.dto.SchemaDto
import com.github.trambui.qatool.dto.SchemaRespDto
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
        return "test first"
    }

    @PostMapping("/")
    fun create(@RequestBody schemaDto: SchemaDto): SchemaRespDto {

        val schema = SchemaEntity(0, name = schemaDto.name, columns = listOf())
        val createSchema = schemaDao.save(schema)

        val columns = schemaDto.columns.map { c ->
            SchemaColumnEntity(
                0,
                name = c.name,
                type = c.type,
                conditionClass = c.conditionClass,
                arguments = c.arguments,
                schema = schema
            )
        }

        schemaColumnDao.saveAll(columns)

        return SchemaRespDto(
            createSchema.id,
            createSchema.name,
            createSchema.columns.map { c -> ColumnRespDto(c.id, c.name, c.type, c.conditionClass) }
        )
    }

    @GetMapping("/{name}")
    fun get(@PathVariable name: String): List<SchemaRespDto> {
        val schemas = this.schemaDao.findByName(name)

        return schemas.map { schema -> toSchemaResp(schema) }
    }

    private fun toSchemaResp(schema: SchemaEntity): SchemaRespDto {
        val columns = schema.columns.map { col -> ColumnRespDto(col.id, col.name, col.type, col.conditionClass) }
        return SchemaRespDto(schema.id, schema.name, columns)
    }
}