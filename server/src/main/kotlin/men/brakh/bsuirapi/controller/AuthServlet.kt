package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.singleParameters
import men.brakh.bsuirapi.extentions.writeError
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import men.brakh.bsuirapicore.model.data.User
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthServlet: HttpServletWithErrorHandling(), JsonServlet {
    private val userRepo = Config.userRepository
    private val bsuirApi = BsuirApi
    private val passwordEncrypter = Config.passwordEncrypter
    private val accessJwtTokensFactory = Config.accessJwtTokenFactory


    private fun nexAugust(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 31)
        calendar.set(Calendar.MONTH, 7)
        calendar.add(Calendar.YEAR, 1)
        return calendar.time
    }
    /**
     * params:
     * @param login - IIS login
     * @param password - IIS password
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
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

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 0)
        calendar.set(Calendar.MONTH, 9)
        calendar.add(Calendar.YEAR, 1)

        val jwtToken = accessJwtTokensFactory.createToken(user, nexAugust())

        resp.writeJson(mapOf("token" to jwtToken))
    }
}
