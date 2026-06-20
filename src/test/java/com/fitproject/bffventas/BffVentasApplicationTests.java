package com.fitproject.bffventas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { "ms.ventas.url=http://localhost:8091" })
class BffVentasApplicationTests {
    @Test
    void contextLoads() {}
}