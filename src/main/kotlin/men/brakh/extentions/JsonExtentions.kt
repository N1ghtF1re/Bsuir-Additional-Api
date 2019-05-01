package men.brakh.extentions

import com.google.gson.Gson

fun Any.toJson(): String {
    return Gson().toJson(this)
}