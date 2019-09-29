package men.brakh.bsuirapi.bot

class FreeAudsRequest(
        var building: Int,
        var floor: Int? = null,
        var time: String? = null,
        var date: String? = null
) {
    fun toRequestParams(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("?building=$building")
        floor?.let { stringBuilder.append("&floor=$floor") }
        date?.let { stringBuilder.append("&date=$date") }
        time?.let { stringBuilder.append("&time=$time") }

        return stringBuilder.toString()
    }
}