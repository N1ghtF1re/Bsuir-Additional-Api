package men.brakh.bsuirstudent

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import javax.validation.Validation
import javax.validation.Validator

@SpringBootApplication
@EnableScheduling
open class BsuirStudentApplication {
    @Bean
    open fun validator(): Validator? {
        val validatorFactory =
            Validation.buildDefaultValidatorFactory()
        return validatorFactory.usingContext().validator
    }
    @Bean
    open fun objectMapper(): ObjectMapper {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper
    }
}

fun main(args: Array<String>) {
    runApplication<BsuirStudentApplication>(*args)
}