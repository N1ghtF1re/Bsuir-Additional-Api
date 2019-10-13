package men.brakh.bsuirapi.app.authorization

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.app.authorization.jwt.RawJwtToken
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest
import javax.servlet.http.HttpServletRequest

inline fun authorized(req: HttpServletRequest, block: (UserAuthorizationRequest) -> Unit) {
    val accessJwtTokensFactory = Config.accessJwtTokenFactory

    val token: String = req.getHeader("Authorization") ?: throw UnauthorizedException()
    val jwtToken =  accessJwtTokensFactory.fromRaw(RawJwtToken(token))
    val authorizedUserAuthorizationRequest: UserAuthorizationRequest = jwtToken.getUser() ?: throw UnauthorizedException()
    block(authorizedUserAuthorizationRequest)
}