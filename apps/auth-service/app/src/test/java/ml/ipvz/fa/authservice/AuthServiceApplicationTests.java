package ml.ipvz.fa.authservice;

import ml.ipvz.fa.cloud.profile.FaProfiles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(FaProfiles.TEST)
class AuthServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
