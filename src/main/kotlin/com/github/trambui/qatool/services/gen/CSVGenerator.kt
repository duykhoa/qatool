package com.github.trambui.qatool.services.gen

import com.github.javafaker.Faker
import com.github.trambui.qatool.dto.FileGenDto
import com.github.trambui.qatool.entities.SchemaEntity
import com.github.trambui.qatool.services.randomize.Randomize
import com.opencsv.CSVWriter
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.util.*

@Service()
class CSVGenerator : FileGenerator {
    val randomize : Randomize

    constructor(randomize: Randomize) {
        this.randomize = randomize
    }

    override fun generate(fileGenDto: FileGenDto, schema: Optional<SchemaEntity>): String {
        val currDir = File(".")
        val path: String = currDir.absolutePath
        val fileLocation = path.substring(0, path.length - 1) + "${fileGenDto.fileName}.csv"
        val writer = CSVWriter(FileWriter(fileLocation))

        val columns = schema.get().columns

        writer.writeNext(columns.map { c -> c.name }.toTypedArray())

        for (i in 1..fileGenDto.rows) {
            val data = columns.map { c -> this.randomize.random(c.type, c.conditionClass, null) }.toTypedArray()
            writer.writeNext(data)
        }

        writer.close()

        return fileLocation
    }
}