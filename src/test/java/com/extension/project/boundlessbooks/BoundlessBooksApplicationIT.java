package com.extension.project.boundlessbooks;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.Ordered;

@Order(Ordered.HIGHEST_PRECEDENCE)
@SpringBootTest
class BoundlessBooksApplicationIT {

    @Test
    void contextLoads() {
    }

}
