package com.github.trambui.qatool.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import javax.persistence.AttributeConverter

class HashMapConverter : AttributeConverter<Map<String, Any?>, String?> {
    private val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: Map<String, Any?>?): String? {
        return try {
            objectMapper.writeValueAsString(attribute)
        } catch (e: RuntimeException) {
            null
        }
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any?>? {
        val typeRef: TypeReference<Map<String, Object?>> = object : TypeReference<Map<String, Object?>>() {}
        return try {
            objectMapper.readValue(dbData, typeRef)
        } catch (e: RuntimeException) {
            println(e)
            throw e
            return null
        }
    }
}