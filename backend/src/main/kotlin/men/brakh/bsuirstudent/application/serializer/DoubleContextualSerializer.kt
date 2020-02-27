package men.brakh.bsuirstudent.application.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.ContextualSerializer
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode

class DoubleContextualSerializer : JsonSerializer<Double>(), ContextualSerializer {
    private val precision = 2

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(
        value: Double,
        gen: JsonGenerator,
        serializers: SerializerProvider
    ) {
        if (precision == 0) {
            gen.writeNumber(value)
        } else {
            var bd = BigDecimal(value)
            bd = bd.setScale(precision, RoundingMode.HALF_UP)
            gen.writeNumber(bd.toDouble())
        }
    }

    @Throws(JsonMappingException::class)
    override fun createContextual(prov: SerializerProvider?, property: BeanProperty): JsonSerializer<*> {
        return DoubleContextualSerializer()
    }
}