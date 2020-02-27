package men.brakh.bsuirstudent.application.search;

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.*
import java.util.function.Consumer
import javax.persistence.criteria.*

class SearchRequest(
    val filters: List<Condition>? = null,
    val sortBy: Sort? = null,
    val page: Int = 0,
    val pageSize: Int = 15
) : Specification<Any> {

    override fun toPredicate(
        root: Root<Any>,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val predicates =
            buildPredicates(root, criteriaQuery, criteriaBuilder)
        addSort(root, criteriaQuery, criteriaBuilder)
        return if (predicates.size > 1) {
            criteriaBuilder.and(*predicates.toTypedArray())
        } else if (predicates.size == 1) {
            predicates[0]
        } else {
            criteriaBuilder.isTrue(criteriaBuilder.literal(true))
        }
    }

    val pageable: Pageable
        get() = PageRequest.of(page, pageSize)

    private fun addSort(
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ) {
        if (sortBy != null) {
            var order: Order? = null
            val path: Path<*> = root.get<Any>(sortBy.field)
            order = when (sortBy.type) {
                SortType.ASC -> criteriaBuilder.asc(path)
                SortType.DESC -> criteriaBuilder.desc(path)
            }
            criteriaQuery.orderBy(order)
        }
    }

    private fun buildPredicates(
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): List<Predicate> {
        if (filters == null) {
            return emptyList()
        }
        val predicates: MutableList<Predicate> =
            ArrayList()
        filters.forEach(Consumer { condition: Condition ->
            predicates.add(
                buildPredicate(condition, root, criteriaQuery, criteriaBuilder)
            )
        })
        return predicates
    }

    private fun buildPredicate(
        condition: Condition,
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return when (condition.comparison) {
            Comparison.EQ -> buildEqualsPredicateToCriteria(
                condition,
                root,
                criteriaQuery,
                criteriaBuilder
            )
            Comparison.GT -> buildGreaterThanPredicate(
                condition,
                root,
                criteriaQuery,
                criteriaBuilder
            )
            Comparison.LT -> buildLessThanPredicate(
                condition,
                root,
                criteriaQuery,
                criteriaBuilder
            )
            Comparison.NE -> buildNotEqualsPredicateToCriteria(
                condition,
                root,
                criteriaQuery,
                criteriaBuilder
            )
            Comparison.CT -> buildContainsPredicate(
                condition,
                root,
                criteriaQuery,
                criteriaBuilder
            )
            Comparison.ISNULL -> buildIsNullPredicate(
                condition,
                root,
                criteriaQuery,
                criteriaBuilder
            )
            Comparison.IN -> throw IllegalArgumentException("Not supported")
        }
        throw RuntimeException()
    }

    private fun buildEqualsPredicateToCriteria(
        condition: Condition,
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return cb.equal(root.get<Any>(condition.field), condition.fieldValue)
    }

    private fun buildNotEqualsPredicateToCriteria(
        condition: Condition,
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return cb.notEqual(root.get<Any>(condition.field), condition.fieldValue)
    }

    private fun buildGreaterThanPredicate(
        condition: Condition,
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return if (condition.type === Type.date) {
            cb.greaterThan(
                root.get<Any>(condition.field) as Path<Date>?,
                condition.fieldValue as Date
            )
        } else {
            cb.gt(root[condition.field], condition.fieldValue as Number)
        }
    }

    private fun buildLessThanPredicate(
        condition: Condition,
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return if (condition.type === Type.date) {
            cb.lessThan(
                root.get<Any>(condition.field) as Path<Date>?,
                condition.fieldValue as Date
            )
        } else {
            cb.lt(root[condition.field], condition.fieldValue as Number)
        }
    }

    private fun buildContainsPredicate(
        condition: Condition,
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate {
        require(!(condition.type !== Type.string)) { "Contains criteria can be used only with strings" }
        val valueString = condition.fieldValue.toString().replace("%".toRegex(), "~%")
        return cb.like(root[condition.field], "%$valueString%", '~')
    }

    private fun buildIsNullPredicate(
        condition: Condition,
        root: Root<*>,
        criteriaQuery: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return cb.isNull(root.get<Any>(condition.field))
    }
}