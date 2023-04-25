package me.liheng.paralle;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Utils {
    private static final AtomicInteger batchNumber = new AtomicInteger(0);

    private Utils(){}

    public static Set<Integer> processBatch(List<Integer> batch) {
        System.out.println(Thread.currentThread().getName() + ": Processing Batch: " + batchNumber.addAndGet(1));
        System.out.println(Thread.currentThread().getName() + ": "+ batch);
        Map<Integer, Integer> map = batch.stream()
                .collect(Collectors.toMap(i -> i, i -> i));
        System.out.println(Thread.currentThread().getName() + ": Map size: " + map.size());
        return map.keySet();
    }
}
