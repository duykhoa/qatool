package com.github.trambui.qatool.controllers

import com.github.javafaker.Faker
import com.github.trambui.qatool.dto.FileGenDto
import com.github.trambui.qatool.entities.SchemaColumnEntity
import com.github.trambui.qatool.entities.SchemaEntity
import com.github.trambui.qatool.repositories.SchemaDao
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileOutputStream
import java.util.*


@RestController
@RequestMapping("/filegen")
class FileGeneratorController {
    private var schemaDao: SchemaDao

    constructor(schemaDao: SchemaDao) {
        this.schemaDao = schemaDao
    }

    @PostMapping("/")
    fun index(@RequestBody fileGenDto: FileGenDto): ResponseEntity<String> {
        val schema = schemaDao.findById(fileGenDto.schemaId)

        if (schema.isEmpty) {
            return ResponseEntity("Schema doesn't exist", HttpStatus.UNPROCESSABLE_ENTITY)
        }

        val workbook = createWorkBook(fileGenDto, schema)

        val fileLocation = writeToFile(workbook, fileGenDto.fileName)
        return ResponseEntity(fileLocation, HttpStatus.OK)
    }

    fun writeToFile(workbook: Workbook, fileName: String): String {
        val currDir = File(".")
        val path: String = currDir.absolutePath
        val fileLocation = path.substring(0, path.length - 1) + "${fileName}.xlsx"

        val outputStream = FileOutputStream(fileLocation)
        workbook.write(outputStream)
        workbook.close()

        return fileLocation
    }

    fun createWorkBook(fileGenDto: FileGenDto, schema: Optional<SchemaEntity>): Workbook {
        val faker = Faker()

        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("Data")

        val headerRow = sheet.createRow(0)
        val columns = schema.get().columns

        columns.forEachIndexed { idx, column ->
            val cell1 = headerRow.createCell(idx)
            cell1.setCellValue(column.name)
        }

        for (i in 1..fileGenDto.rows) {
            val dataRow = sheet.createRow(i)
            columns.forEachIndexed { idx, column ->
                val cell1 = dataRow.createCell(idx)
                cell1.setCellValue(randomData(column, faker))
            }
        }

        return workbook;
    }

    private fun randomData(column: SchemaColumnEntity, faker: Faker): String {
        val randomFunction = mapOf(
            "number" to { -> "${faker.number()}" },
            "string" to { -> faker.lorem().words(3).joinToString(" ") }
        )

        return randomFunction[column.type]!!.invoke()
    }
}