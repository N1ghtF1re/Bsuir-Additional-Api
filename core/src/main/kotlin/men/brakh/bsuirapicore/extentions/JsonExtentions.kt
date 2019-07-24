package men.brakh.bsuirapicore.extentions

import com.google.gson.GsonBuilder
import men.brakh.bsuirapicore.serializer.TimestampSerializer
import java.util.*

val gson = GsonBuilder().registerTypeAdapter(Date::class.java, TimestampSerializer()).serializeNulls().create()

fun Any.toJson(): String {
    return gson.toJson(this)
}