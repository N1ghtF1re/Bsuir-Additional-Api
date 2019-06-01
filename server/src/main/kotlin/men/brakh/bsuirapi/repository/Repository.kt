package men.brakh.bsuirapi.repository

import men.brakh.bsuirapi.model.data.Identifiable

interface Repository<T : Identifiable> {
    fun add(entity: T): T
    fun add(entities: List<T>) = entities.forEach{ add(it) }
    fun delete(entity: T)
    fun findAll(): List<T>
    fun findById(id: Long): T?
    fun update(entity: T): T
    fun count(): Int
}
