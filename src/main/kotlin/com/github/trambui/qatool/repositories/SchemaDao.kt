package com.github.trambui.qatool.repositories

import com.github.trambui.qatool.entities.SchemaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SchemaDao: CrudRepository<SchemaEntity, Long> {
    fun findByName(name: String): List<SchemaEntity>
}