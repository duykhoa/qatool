package com.github.trambui.qatool.dto

data class ColumnDto(val name: String, val type: String, val conditionClass: String?, val arguments: Map<String, Object?>?)

data class SchemaDto(val name: String, val columns: List<ColumnDto>)