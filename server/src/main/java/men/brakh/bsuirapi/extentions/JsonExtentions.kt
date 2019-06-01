package men.brakh.bsuirapi.extentions

import com.google.gson.GsonBuilder
import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.serializer.TimestampSerializer
import java.util.*

val gson = GsonBuilder().registerTypeAdapter(Date::class.java, TimestampSerializer()).create()

fun Any.toJson(): String {
    return gson.toJson(this)
}