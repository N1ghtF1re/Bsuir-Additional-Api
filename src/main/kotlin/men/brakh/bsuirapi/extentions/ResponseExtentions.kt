package men.brakh.bsuirapi.extentions

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.setDefaultJsonHeaders() {
    this.contentType = "application/json"
    this.characterEncoding = "UTF-8"
}

fun HttpServletResponse.writeError(msg: String, code: Int = HttpServletResponse.SC_BAD_REQUEST) {
    val errorMap = mapOf(
            "ok" to false,
            "msg" to msg
    )

    this.status = code

    this.writer.write(errorMap.toJson())
}

/**
 * Return parameters map without array in value
 */
val HttpServletRequest.singleParameters: Map<String, String>
    get() = this.parameterMap.mapValues { (_, value) -> value.first() }