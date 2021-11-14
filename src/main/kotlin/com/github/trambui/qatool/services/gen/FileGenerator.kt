package com.github.trambui.qatool.services.gen

import com.github.trambui.qatool.dto.FileGenDto
import com.github.trambui.qatool.entities.SchemaEntity
import java.util.*

interface FileGenerator {
    fun generate(fileGenDto: FileGenDto, schema: Optional<SchemaEntity>): String
}