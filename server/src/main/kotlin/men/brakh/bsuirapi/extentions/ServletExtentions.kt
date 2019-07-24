package men.brakh.bsuirapi.extentions

import men.brakh.bsuirapi.repository.TokenRepository
import men.brakh.bsuirapicore.extentions.gson
import men.brakh.bsuirapicore.extentions.toJson
import men.brakh.bsuirapicore.model.data.Token
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.writeError(msg: String, code: Int = HttpServletResponse.SC_BAD_REQUEST) {
    val errorMap = mapOf(
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

fun HttpServletRequest.extractToken(tokenRepo: TokenRepository) : Token?{
    val stringToken: String? = this.getHeader("Authorization")
    return stringToken?.let { tokenRepo.find(token = it) }
}

fun <T : Any> HttpServletRequest.extractBody(classz: Class<T>): T {
    return gson.fromJson(this.reader, classz)
}

fun HttpServletResponse.writeJson(obj: Any) {
    return gson.toJson(obj, this.writer)
}