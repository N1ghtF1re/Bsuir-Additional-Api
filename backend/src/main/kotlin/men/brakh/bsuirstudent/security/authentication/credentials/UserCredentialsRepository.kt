package men.brakh.bsuirstudent.security.authentication.credentials

import org.springframework.data.jpa.repository.JpaRepository

interface UserCredentialsRepository : JpaRepository<UserCredentials, Int> {
    fun findOneByUsername(username: String): UserCredentials?
}