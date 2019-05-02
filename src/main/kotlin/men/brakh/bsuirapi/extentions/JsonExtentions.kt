package men.brakh.bsuirapi.extentions

import com.google.gson.Gson

fun Any.toJson(): String {
    return Gson().toJson(this)
}