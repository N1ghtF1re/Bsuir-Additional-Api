package men.brakh.bsuirstudent.security.authentication.credentials;

import men.brakh.bsuirstudent.domain.BaseEntity
import javax.persistence.*

@Entity(name = "user_credentials")
data class UserCredentials (
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  override var id: Int? = null,

  val password: String,

  @Column(unique = true)
  val username: String
) :  BaseEntity<Int?>
