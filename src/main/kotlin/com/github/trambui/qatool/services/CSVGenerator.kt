package com.github.trambui.qatool.services

import com.github.javafaker.Faker
import com.github.trambui.qatool.dto.FileGenDto
import com.github.trambui.qatool.entities.SchemaColumnEntity
import com.github.trambui.qatool.entities.SchemaEntity
import com.opencsv.CSVWriter
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.util.*

@Service()
class CSVGenerator : FileGenerator {
    override fun generate(fileGenDto: FileGenDto, schema: Optional<SchemaEntity>): String {
        val currDir = File(".")
        val path: String = currDir.absolutePath
        val fileLocation = path.substring(0, path.length - 1) + "${fileGenDto.fileName}.csv"
        val writer = CSVWriter(FileWriter(fileLocation))

        val faker = Faker()
        val columns = schema.get().columns

        writer.writeNext(columns.map { c -> c.name }.toTypedArray())

        for (i in 1..fileGenDto.rows) {
            val data = columns.map { c -> randomData(c, faker) }.toTypedArray()
            writer.writeNext(data)
        }

        writer.close()

        return fileLocation
    }

    private fun randomData(column: SchemaColumnEntity, faker: Faker): String {
        val randomFunction = mapOf(
            "number" to { "${faker.number().randomNumber()}" },
            "string" to { faker.lorem().words(3).joinToString(" ") }
        )

        return randomFunction[column.type]!!.invoke()
    }
}