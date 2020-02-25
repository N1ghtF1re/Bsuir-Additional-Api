package men.brakh.bsuirstudent.domain;

import java.io.Serializable;

/**
 * Base entity
 * @param <T> Identifier's type
 */
interface BaseEntity<I> : Serializable {
    var id: I?
}
