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

        return when {
            predicates.size > 1 -> {
                criteriaBuilder.and(*predicates.toTypedArray())
            }
            predicates.size == 1 -> {
                predicates[0]
            }
            else -> {
                criteriaBuilder.isTrue(criteriaBuilder.literal(true))
            }
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
        val path = getPath(root, condition.field)
        return when (condition.comparison) {
            Comparison.EQ -> buildEqualsPredicateToCriteria(
                condition,
                path,
                criteriaBuilder
            )
            Comparison.GT -> buildGreaterThanPredicate(
                condition,
                path,
                criteriaBuilder
            )
            Comparison.LT -> buildLessThanPredicate(
                condition,
                path,
                criteriaBuilder
            )
            Comparison.NE -> buildNotEqualsPredicateToCriteria(
                condition,
                path,
                criteriaBuilder
            )
            Comparison.CT -> buildContainsPredicate(
                condition,
                path as Path<String>,
                criteriaBuilder
            )
            Comparison.ISNULL -> buildIsNullPredicate(
                path,
                criteriaBuilder
            )
            Comparison.IN -> throw IllegalArgumentException("Not supported")
        }
    }

    private fun buildEqualsPredicateToCriteria(
        condition: Condition,
        path: Path<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return cb.equal(path, condition.fieldValue)
    }

    private fun buildNotEqualsPredicateToCriteria(
        condition: Condition,
        path: Path<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return cb.notEqual(path, condition.fieldValue)
    }

    private fun buildGreaterThanPredicate(
        condition: Condition,
        path: Path<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return if (condition.type === Type.date) {
            cb.greaterThan(
                path as Path<Date>,
                condition.fieldValue as Date
            )
        } else {
            cb.gt(path as Path<Number>, condition.fieldValue as Number)
        }
    }

    private fun buildLessThanPredicate(
        condition: Condition,
        path: Path<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return if (condition.type === Type.date) {
            cb.lessThan(
                path as Path<Date>?,
                condition.fieldValue as Date
            )
        } else {
            cb.lt(path as Path<Number>, condition.fieldValue as Number)
        }
    }

    private fun buildContainsPredicate(
        condition: Condition,
        path: Path<String>,
        cb: CriteriaBuilder
    ): Predicate {
        require(!(condition.type !== Type.string)) { "Contains criteria can be used only with strings" }
        val valueString = condition.fieldValue.toString().replace("%".toRegex(), "~%")
        return cb.like(path, "%$valueString%", '~')
    }

    private fun buildIsNullPredicate(
        path: Path<*>,
        cb: CriteriaBuilder
    ): Predicate {
        return cb.isNull(path)
    }

    private fun getPath(root: Root<*>, fieldName: String): Path<*> {
        val fieldPath = listOf(*fieldName.split(".").toTypedArray())
        val resultPath: Path<*>
        if (isRootField(fieldPath)) {
            resultPath = root.get<Any>(fieldName)
        } else {
            var resultJoin: Join<*, *> = root.join<Any, Any>(fieldPath[0])
            for (i in 1..fieldPath.size - 2) {
                resultJoin = resultJoin.join<Any, Any>(fieldPath[i])
            }
            resultPath = resultJoin.get<Any>(fieldPath[fieldPath.size - 1])
        }
        return resultPath
    }

    private fun isRootField(fieldPath: List<String>): Boolean {
        return fieldPath.size == 1
    }

}