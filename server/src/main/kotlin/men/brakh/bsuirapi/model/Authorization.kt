package men.brakh.bsuirapi.model

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.model.jwt.RawJwtToken
import men.brakh.bsuirapicore.model.data.User
import javax.servlet.http.HttpServletRequest

inline fun authorized(req: HttpServletRequest, block: (User) -> Unit) {
    val accessJwtTokensFactory = Config.accessJwtTokenFactory

    val token: String = req.getHeader("Authorization") ?: throw UnauthorizedException()
    val jwtToken =  accessJwtTokensFactory.fromRaw(RawJwtToken(token))
    val authorizedUser: User = jwtToken.getUser() ?: throw UnauthorizedException()
    block(authorizedUser)
}