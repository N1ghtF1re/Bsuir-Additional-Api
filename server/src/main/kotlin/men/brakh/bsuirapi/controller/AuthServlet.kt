package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.extentions.setDefaultJsonHeaders
import men.brakh.bsuirapi.extentions.singleParameters
import men.brakh.bsuirapi.extentions.writeError
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import men.brakh.bsuirapicore.model.data.User
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthServlet: HttpServletWithErrorHandling() {
    private val userRepo = Config.userRepository
    private val bsuirApi = BsuirApi
    private val passwordEncrypter = Config.passwordEncrypter
    private val accessJwtTokensFactory = Config.accessJwtTokenFactory

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        val params: Map<String, String> = req.singleParameters
        
        require("login" in params && "password" in params) {
            return resp.writeError("login and password is required", 400)
        }
        
        
        val login: String = params["login"]!!
        val password: String = params["password"]!!
        
        bsuirApi.tryAuth(login, password) ?: return resp.writeError("invalid login or password", 400)
        
        val encryptedPassword = passwordEncrypter.encrypt(password)

        val user = User(login = login, password = encryptedPassword)

        val dbUser = userRepo.find(login=login)
        if(dbUser == null) {
            userRepo.add(user)
        } else {
            userRepo.update(dbUser.copy(password = user.password))
        }

        val jwtToken = accessJwtTokensFactory.createToken(user, 1)

        resp.writeJson(mapOf("token" to jwtToken))
    }
}