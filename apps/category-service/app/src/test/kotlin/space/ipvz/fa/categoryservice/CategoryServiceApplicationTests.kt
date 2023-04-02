package space.ipvz.fa.categoryservice

import space.ipvz.fa.cloud.profile.FaProfiles
import space.ipvz.fa.categoryservice.CategoryServiceApplication
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [CategoryServiceApplication::class])
@ActiveProfiles(FaProfiles.TEST)
class CategoryServiceApplicationTests {

    @Test
    fun contextLoads() {
    }
}
