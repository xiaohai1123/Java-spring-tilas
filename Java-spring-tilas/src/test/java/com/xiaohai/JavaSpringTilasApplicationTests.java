package com.xiaohai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class JavaSpringTilasApplicationTests {

    @Test
    public void testUuid() {
        for (int i = 0; i < 100; i++) {
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
        }
    }

}
