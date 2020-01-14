package com.cqjtu.csi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
class CsiApplicationTests {

    @Test
    void contextLoads() {
        Date from = Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
        System.out.println(from);
    }

}
