package space.ipvz.fa.balanceservice

import space.ipvz.fa.balanceservice.BalanceServiceApplication
import space.ipvz.fa.cloud.profile.FaProfiles
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [BalanceServiceApplication::class])
@ActiveProfiles(FaProfiles.TEST)
class BalanceServiceApplicationTests {

    @Test
    fun contextLoads() {
    }
}
