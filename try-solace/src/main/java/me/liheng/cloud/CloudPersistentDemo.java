package me.liheng.cloud;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CloudPersistentDemo {

    public static void main(String[] args) {

        // Queues set up in Broker:
        // persistent/one -> queue1
        //                -> queue2
        // persistent/two -> queue2

        ExecutorService service = null;
        try {
            CloudUtil.getMessagingService(); //To generate MessagingService once before all threads
            service = Executors.newFixedThreadPool(3);
            service.execute(CloudIterativePersistentPublisher::run);
            service.execute(new QueueReceiver("queue1")::run);
            service.execute(new QueueReceiver("queue2")::run);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(service != null) service.shutdown();
        }
    }
}
