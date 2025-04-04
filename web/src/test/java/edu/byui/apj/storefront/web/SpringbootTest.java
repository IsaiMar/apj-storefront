package edu.byui.apj.storefront.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringbootTest {

    @Test
    void contextLoads() {
        // If the application context fails to load, this test will fail
        assertThat(true).isTrue();
    }
}
