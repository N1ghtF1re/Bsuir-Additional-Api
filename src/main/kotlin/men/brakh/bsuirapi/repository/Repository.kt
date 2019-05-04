package men.brakh.bsuirapi.repository

interface Repository<T> {
    fun add(entity: T): T
    fun add(entities: List<T>)
    fun delete(entity: T)
    fun findAll(): List<T>
    fun findById(id: Long): T?
    fun update(entity: T): T
}