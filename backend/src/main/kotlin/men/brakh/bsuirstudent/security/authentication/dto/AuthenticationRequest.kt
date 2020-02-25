package men.brakh.bsuirstudent.security.authentication.dto;

data class AuthenticationRequest(
  val username: String,
  val password: String
)