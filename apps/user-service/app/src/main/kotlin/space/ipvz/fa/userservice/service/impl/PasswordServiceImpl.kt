package space.ipvz.fa.userservice.service.impl;

import space.ipvz.fa.userservice.service.PasswordService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordServiceImpl(private val passwordEncoder: PasswordEncoder) : PasswordService {
    override fun matches(input: String, password: String): Boolean = passwordEncoder.matches(input, password)

    override fun encode(input: String): String = passwordEncoder.encode(input)
}
