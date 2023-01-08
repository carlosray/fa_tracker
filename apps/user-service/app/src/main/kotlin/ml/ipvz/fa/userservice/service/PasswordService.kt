package ml.ipvz.fa.userservice.service;

interface PasswordService {
    fun matches(input: String, password: String): Boolean
    fun encode(input: String): String
}
