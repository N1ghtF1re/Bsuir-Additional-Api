package men.brakh.bsuirapi.inrfastructure.keys

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.crypto.KeyGenerator

object KeysInitializer {
    private val keysFolder: URL = this::class.java.classLoader.getResource("")!!
    private val logger = LoggerFactory.getLogger(KeysInitializer.javaClass)

    fun initJwtKey() : String {

        val jwtKeyFile =  Paths.get(keysFolder.path + "jwt.key")

        return if(Files.exists(jwtKeyFile)) {
            String(Files.readAllBytes(jwtKeyFile))
        } else {
            val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
            val base64Key = Base64.getEncoder().encodeToString(key.encoded)
            Files.write(jwtKeyFile, base64Key.toByteArray())
            logger.info("Created jwt key file: ${jwtKeyFile.toAbsolutePath()}")

            base64Key
        }

    }

    fun initPasswordKey(): String {
        val keyFile = Paths.get(keysFolder.path + "aes.key")


        return if(Files.exists(keyFile)) {
            String(Files.readAllBytes(keyFile))
        } else {
            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(256)

            val key = Base64.getEncoder().encodeToString(keyGenerator.generateKey().encoded)
            Files.write(keyFile, key.toByteArray())
            logger.info("Created aes key file: ${keyFile.toAbsolutePath()}")
            key
        }
    }
}