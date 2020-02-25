package men.brakh.bsuirstudent.domain;

/**
 * Interface which have to implement child entities.
 * @param <T> Parent identifier's type.
 */
interface ParentAware<T>: BaseEntity<T> {
    val parentId: T
}
