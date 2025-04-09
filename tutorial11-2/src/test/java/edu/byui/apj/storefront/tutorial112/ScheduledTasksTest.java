package edu.byui.apj.storefront.tutorial112;

import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

import static org.awaitility.Awaitility.await;

@SpringBootTest
public class ScheduledTasksTest {

    @SpyBean
    ScheduledTasks tasks;

    @Test
    public void reportCurrentTime() {
        // Manually trigger the method to test the logic
        tasks.reportCurrentTime();

        // Use Awaitility to ensure the method was called at least twice
        await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
            verify(tasks, atLeast(2)).reportCurrentTime();
        });
    }
}
