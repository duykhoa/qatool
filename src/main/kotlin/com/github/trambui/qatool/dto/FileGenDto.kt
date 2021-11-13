package com.github.trambui.qatool.dto

import com.github.trambui.qatool.enums.FileType

data class FileGenDto (
    val schemaId: Int,
    val rows: Int = 1000,
    val format: FileType = FileType.EXCEL,
    val fileName: String
) {
}