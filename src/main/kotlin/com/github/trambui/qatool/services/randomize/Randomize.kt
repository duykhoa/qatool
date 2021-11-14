package com.github.trambui.qatool.services.randomize

import com.github.javafaker.Faker
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

data class DateRangeBetween(val from: String, val to: String)

@Service
class Randomize {
    val faker: Faker

    constructor(faker: Faker) {
        this.faker = faker
    }

    fun random(type: String, conditionClass: String?, argument: Map<String, Object?>?): String {
        val faker = Faker()
        val randomFunction = mapOf(
            "number" to { faker.number().numberBetween(10001, Int.MAX_VALUE).toString() },
            "string" to { getString(conditionClass, argument) },
            "date" to {
                val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

                var date: Date = if (conditionClass == "between") {
                    var range = DateRangeBetween(from = argument!!["from"].toString(), to = argument!!["to"].toString())

                    faker.date()
                        .between(
                            localDateToDate(LocalDate.parse(range.from, dateFormatter)),
                            localDateToDate(LocalDate.parse(range.to, dateFormatter)),
                        )
                } else {
                    faker.date()
                        .between(
                            localDateToDate(LocalDate.of(1970, 1, 1)),
                            localDateToDate(LocalDate.of(2025, 12, 31))
                        )
                }


                dateToLocalDate(date).format(dateFormatter)
            }
        )

        return randomFunction[type]!!.invoke()
    }

    fun getString(conditionClass: String?, argument: Map<String, Object?>?): String {
        return when (conditionClass) {
            "name" -> {
                faker.name().toString()
            }
            else -> {
                faker.lorem().words(3).joinToString(" ")
            }
        }
    }

    fun localDateToDate(localDate: LocalDate): Date {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
    }

    fun dateToLocalDate(date: Date): LocalDate {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
}