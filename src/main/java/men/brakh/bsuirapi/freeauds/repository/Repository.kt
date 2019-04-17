package men.brakh.bsuirapi.freeauds.repository

import men.brakh.bsuirapi.freeauds.model.Lesson

interface Repository<T> {
    fun add(entity: T): T
    fun delete(entity: T)
    fun findAll(): List<T>
    fun findById(id: Long): T?
    fun update(entity: T): T
}