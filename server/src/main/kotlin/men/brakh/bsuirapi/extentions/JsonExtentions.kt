package men.brakh.bsuirapi.extentions

import com.google.gson.GsonBuilder
import men.brakh.bsuirapi.Config

val gson = GsonBuilder().setDateFormat(Config.dateFormat).create()

fun Any.toJson(): String {
    return gson.toJson(this)
}