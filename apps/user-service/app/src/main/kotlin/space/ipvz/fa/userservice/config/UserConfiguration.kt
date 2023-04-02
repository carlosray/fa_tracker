package space.ipvz.fa.userservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.security.SecureRandom
import java.util.Random

@Configuration
class UserConfiguration {

    @Bean
    fun encoder(@Value("\${pwd.encoder.strength}") strength: Int): PasswordEncoder {
        val seed = ByteArray(10)
        Random().nextBytes(seed)
        return BCryptPasswordEncoder(strength, SecureRandom(seed))
    }
}