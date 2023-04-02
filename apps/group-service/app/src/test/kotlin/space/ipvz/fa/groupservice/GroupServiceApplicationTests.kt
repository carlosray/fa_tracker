package space.ipvz.fa.groupservice

import space.ipvz.fa.cloud.profile.FaProfiles
import space.ipvz.fa.groupservice.GroupServiceApplication
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(classes = [GroupServiceApplication::class])
@ActiveProfiles(FaProfiles.TEST)
class GroupServiceApplicationTests {

    @Test
    fun contextLoads() {
    }
}
