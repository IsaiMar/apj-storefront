package edu.byui.apj.storefront.tutorial112;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Component
@EnableScheduling
public class ScheduledTasks {

    // List of names to process
    private final List<String> names = Arrays.asList(
            "Alice", "Bob", "Charlie", "David", "Emma",
            "Frank", "Grace", "Henry", "Ivy", "Jack",
            "Karen", "Liam", "Mia", "Noah", "Olivia",
            "Paul", "Quinn", "Ryan", "Sophia", "Thomas"
    );

    // Scheduled task that runs at a fixed time using cron-based scheduling
    @Scheduled(cron = "0 30 10 * * ?") // Runs every day at 10:30 AM
    public void reportCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current time: " + sdf.format(System.currentTimeMillis()));
        executeBatchProcessing();
    }

    private void executeBatchProcessing() {
        // Split names into two batches
        List<String> batch1 = names.subList(0, names.size() / 2);
        List<String> batch2 = names.subList(names.size() / 2, names.size());

        // Create ExecutorService to process batches in parallel
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Submit two tasks, one for each batch
        executorService.submit(() -> printNames(batch1));
        executorService.submit(() -> printNames(batch2));

        // Shutdown the executor service after tasks are submitted
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    private void printNames(List<String> batch) {
        for (String name : batch) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(System.currentTimeMillis());
            System.out.println("Name: " + name + ", Time: " + currentTime);
        }
        if (batch.equals(names.subList(0, names.size() / 2))) {
            System.out.println("Batch 1 done!");
        } else {
            System.out.println("Batch 2 done!");
        }
    }

    // Additional scheduled task to report "All done here!" after all tasks
    @Scheduled(cron = "0 45 10 * * ?") // Runs every day at 10:45 AM (15 minutes after batch task)
    public void reportCompletion() {
        System.out.println("All done here!");
    }
}
