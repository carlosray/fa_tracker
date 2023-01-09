package ml.ipvz.userservice

import ml.ipvz.fa.cloud.profile.FaProfiles
import ml.ipvz.fa.userservice.UserServiceApplication
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [UserServiceApplication::class])
@ActiveProfiles(FaProfiles.TEST)
class UserServiceApplicationTests {

    @Test
    fun contextLoads() {
    }
}
