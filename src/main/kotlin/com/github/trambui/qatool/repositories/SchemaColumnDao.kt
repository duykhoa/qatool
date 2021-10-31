package com.github.trambui.qatool.repositories

import com.github.trambui.qatool.entities.SchemaColumnEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SchemaColumnDao: CrudRepository<SchemaColumnEntity, Long>