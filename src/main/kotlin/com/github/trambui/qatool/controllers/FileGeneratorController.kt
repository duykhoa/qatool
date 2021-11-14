package com.github.trambui.qatool.controllers

import com.github.trambui.qatool.dto.FileGenDto
import com.github.trambui.qatool.enums.FileType
import com.github.trambui.qatool.repositories.SchemaDao
import com.github.trambui.qatool.services.gen.CSVGenerator
import com.github.trambui.qatool.services.gen.ExcelGenerator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/filegen")
class FileGeneratorController {
    private var schemaDao: SchemaDao
    private var excelFileGen: ExcelGenerator
    private var csvFileGen: CSVGenerator

    constructor(schemaDao: SchemaDao, excelFileGen: ExcelGenerator, csvFileGen: CSVGenerator) {
        this.schemaDao = schemaDao
        this.excelFileGen = excelFileGen
        this.csvFileGen = csvFileGen
    }

    @PostMapping("/")
    fun index(@RequestBody fileGenDto: FileGenDto): ResponseEntity<String> {
        val schema = schemaDao.findById(fileGenDto.schemaId)

        if (schema.isEmpty) {
            return ResponseEntity("Schema doesn't exist", HttpStatus.UNPROCESSABLE_ENTITY)
        }

        return when (fileGenDto.format) {
            FileType.EXCEL -> {
                val fileLocation = excelFileGen.generate(fileGenDto, schema)
                ResponseEntity(fileLocation, HttpStatus.OK)
            }
            FileType.CSV -> {
                val fileLocation = csvFileGen.generate(fileGenDto, schema)
                ResponseEntity(fileLocation, HttpStatus.OK)
            }
        }
    }
}