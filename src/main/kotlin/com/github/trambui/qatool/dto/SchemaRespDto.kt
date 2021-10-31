package com.github.trambui.qatool.dto

data class ColumnRespDto(val id: Int, val name: String, val type: String, val conditionClass: String?)

data class SchemaRespDto(val id: Int, val name: String, val columns: List<ColumnRespDto>)