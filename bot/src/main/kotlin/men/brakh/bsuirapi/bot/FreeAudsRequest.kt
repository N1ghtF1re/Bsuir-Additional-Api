package men.brakh.bsuirapi.bot

import java.text.SimpleDateFormat

class FreeAudsRequest(
        var building: Int,
        var floor: Int? = null,
        var time: String = "",
        var date: String = ""
) {
    fun toRequestParams(): String {
        val lessonDate = SimpleDateFormat("dd.MM.yyyy").parse(date)
        val dateString = SimpleDateFormat("yyyy-MM-dd").format(lessonDate)
        val stringBuilder = StringBuilder()
        stringBuilder.append("?building=$building")
        floor?.let { stringBuilder.append("&floor=$floor") }
        date.let { stringBuilder.append("&date=$dateString") }
        time.let { stringBuilder.append("&time=$time") }

        return stringBuilder.toString()
    }
}