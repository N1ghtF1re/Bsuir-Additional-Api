package men.brakh.bsuirapi.model

import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class PasswordEncrypter(key: String) {
    private val key: SecretKey
    private val cipher: Cipher = Cipher.getInstance("AES")

    init {
        val aesByte = Base64.getDecoder().decode(key)
        this.key = SecretKeySpec(aesByte, "AES")
    }

    fun encrypt(strPassword: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val bytes = cipher.doFinal(strPassword.toByteArray())
        return Base64.getEncoder().encodeToString(bytes)
    }

    fun decrypt(base64Password: String): String {
        val bytes = Base64.getDecoder().decode(base64Password)
        cipher.init(Cipher.DECRYPT_MODE, key)
        return String(cipher.doFinal(bytes))

    }
}
