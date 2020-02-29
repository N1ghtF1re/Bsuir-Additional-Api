package men.brakh.bsuirstudent.application.validation.utils

import men.brakh.bsuirstudent.application.exception.BadRequestException
import org.slf4j.LoggerFactory
import java.util.stream.Collectors
import javax.validation.ConstraintViolation
import javax.validation.Validator

object ValidationUtils {
    private val logger = LoggerFactory.getLogger(ValidationUtils::class.java)
    @Throws(BadRequestException::class)
    fun <T> validateAndThowIfInvalid(validator: Validator?, entity: T) {
        if (validator != null) {
            val errors = validator.validate(entity)
                .stream()
                .map { violation: ConstraintViolation<T> ->
                    "\t Invalid value in field: " + violation.propertyPath +
                            ". Message: " + violation.message
                }
                .collect(
                    Collectors.toList()
                )
            if (errors.size > 0) {
                logger.warn(
                    "$entity validation error. " +
                            errors.joinToString(separator = "\n")
                )
                throw BadRequestException(errors.joinToString(separator = "\n"))
            }
        }
    }
}