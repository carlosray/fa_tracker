package space.ipvz.fa.accountservice

import space.ipvz.fa.cloud.profile.FaProfiles
import space.ipvz.fa.accountservice.AccountServiceApplication
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [AccountServiceApplication::class])
@ActiveProfiles(FaProfiles.TEST)
class AccountServiceApplicationTests {

    @Test
    fun contextLoads() {
    }
}
