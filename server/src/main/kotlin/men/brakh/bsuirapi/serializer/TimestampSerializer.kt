package men.brakh.bsuirapi.serializer

import java.io.IOException
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.google.gson.TypeAdapter
import java.util.*


class TimestampSerializer : TypeAdapter<Date>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Date?) {
        if (value == null)
            out.nullValue()
        else
            out.value(value.time / 1000)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader?): Date? {
        return if (`in` != null)
            Date(`in`.nextLong() * 1000)
        else
            null
    }
}