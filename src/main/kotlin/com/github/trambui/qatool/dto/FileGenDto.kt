package com.github.trambui.qatool.dto

data class FileGenDto (
    val schemaId: Int,
    val rows: Int = 1000,
    val format: String = "csv",
    val fileName: String
) {
}