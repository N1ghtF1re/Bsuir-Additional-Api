package men.brakh.bsuirstudent.security.authentication.credentials

import men.brakh.bsuirstudent.SpringContext
import men.brakh.bsuirstudent.domain.iis.student.repository.StudentRepository
import men.brakh.bsuirstudent.security.authentication.bsuir.PasswordEncrypt
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

val userCredentialsRepository = SpringContext.getBean(UserCredentialsRepository::class.java)
val passwordEncrypt = SpringContext.getBean(PasswordEncrypt::class.java)
val studentRepository = SpringContext.getBean(StudentRepository::class.java)
fun getCurrentUserUsername(): String {
    val principal = SecurityContextHolder.getContext().authentication.principal

    return if (principal is UserDetails) {
        principal.username
    } else {
        principal.toString()
    }
}


inline fun useBsuirCredentials(func: (username: String, password: String) -> Any) {
    val username = getCurrentUserUsername()

    val credentials = userCredentialsRepository.findOneByUsername(username)!!

    func(username, passwordEncrypt.decrypt(credentials.password))
}
