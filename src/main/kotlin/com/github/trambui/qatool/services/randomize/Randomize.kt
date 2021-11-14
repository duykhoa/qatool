package com.github.trambui.qatool.services.randomize

import com.github.javafaker.Faker
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.datetime.DateFormatter
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class Randomize {
    val faker: Faker

    constructor(faker: Faker) {
        this.faker = faker
    }

    fun random(type: String, conditionClass: String?, argument: Object?): String {
        val faker = Faker()
        val randomFunction = mapOf(
            "number" to { "${faker.number().randomNumber()}" },
            "string" to { faker.lorem().words(3).joinToString(" ") },
            "date" to {
                val date = faker.date()
                    .between(
                        localDateToDate(LocalDate.of(1970, 1, 1)),
                        localDateToDate(LocalDate.of(2022, 12, 31))
                    )

                val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/YYYY")

                dateToLocalDate(date).format(dateFormatter)
            }
        )

        return randomFunction[type]!!.invoke()
    }

    fun localDateToDate(localDate: LocalDate): Date {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
    }

    fun dateToLocalDate(date: Date): LocalDate {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
}