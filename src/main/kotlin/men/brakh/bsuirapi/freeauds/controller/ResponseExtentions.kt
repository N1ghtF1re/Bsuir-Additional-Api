package men.brakh.bsuirapi.freeauds.controller

import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.setDefaultJsonHeaders() {
    this.contentType = "application/json"
    this.characterEncoding = "UTF-8"
}