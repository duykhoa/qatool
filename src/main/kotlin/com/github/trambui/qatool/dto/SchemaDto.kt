package com.github.trambui.qatool.dto

data class ColumnDto(val name: String, val type: String, val conditionClass: String?, val arguments: Map<String, Any?>?)

data class SchemaDto(val name: String, val columns: List<ColumnDto>)