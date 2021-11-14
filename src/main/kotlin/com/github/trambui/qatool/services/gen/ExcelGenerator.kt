package com.github.trambui.qatool.services.gen

import com.github.trambui.qatool.dto.FileGenDto
import com.github.trambui.qatool.entities.SchemaEntity
import com.github.trambui.qatool.services.randomize.Randomize
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Service()
class ExcelGenerator : FileGenerator {
    val randomize: Randomize

    constructor(randomize: Randomize) {
        this.randomize = randomize
    }

    override fun generate(fileGenDto: FileGenDto, schema: Optional<SchemaEntity>): String {
        val workbook = SXSSFWorkbook(10000)
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
                cell1.setCellValue(this.randomize.random(column.type, column.conditionClass, null))
            }
        }

        return write(fileGenDto.fileName, workbook);
    }

    private fun write(fileName: String, workbook: Workbook): String {
        val currDir = File(".")
        val path: String = currDir.absolutePath
        val fileLocation = path.substring(0, path.length - 1) + "${fileName}.xlsx"

        val outputStream = FileOutputStream(fileLocation)
        workbook.write(outputStream)
        workbook.close()

        return fileLocation
    }
}