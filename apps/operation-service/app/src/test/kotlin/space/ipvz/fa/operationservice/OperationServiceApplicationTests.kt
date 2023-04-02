package space.ipvz.fa.operationservice

import space.ipvz.fa.cloud.profile.FaProfiles
import space.ipvz.fa.operationservice.OperationServiceApplication
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [OperationServiceApplication::class])
@ActiveProfiles(FaProfiles.TEST)
class OperationServiceApplicationTests {

    @Test
    fun contextLoads() {
    }
}
