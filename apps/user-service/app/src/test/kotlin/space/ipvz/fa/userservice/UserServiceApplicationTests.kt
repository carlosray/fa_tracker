package space.ipvz.fa.userservice

import space.ipvz.fa.cloud.profile.FaProfiles
import space.ipvz.fa.userservice.UserServiceApplication
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
