package men.brakh.bsuirstudent.application.validation.utils;

import men.brakh.bsuirstudent.application.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationUtils {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtils.class);

    public static <T> void validateAndThowIfInvalid(Validator validator, T entity) throws BadRequestException {
        if (validator != null) {
            List<String> errors = validator.validate(entity)
                    .stream()
                    .map(violation -> "\t Invalid value in field: " + violation.getPropertyPath() +
                            ". Message: " + violation.getMessage())
                    .collect(Collectors.toList());

            if (errors.size() > 0) {
                logger.warn(entity.getClass().getSimpleName() + " validation error. " +
                        String.join("\n", errors));
                throw new BadRequestException(String.join("\n", errors));
            }
        }
    }
}
