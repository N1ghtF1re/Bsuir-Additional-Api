package men.brakh.bsuirapi.extentions

import com.google.gson.GsonBuilder

private val gson = GsonBuilder().setDateFormat("dd.MM.yyyy HH:mm").create()

fun Any.toJson(): String {
    return gson.toJson(this)
}
