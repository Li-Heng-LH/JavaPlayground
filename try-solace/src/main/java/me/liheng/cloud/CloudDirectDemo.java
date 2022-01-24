package me.liheng.cloud;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CloudDirectDemo {

    public static void main(String[] args) {

        ExecutorService service = null;
        try {
            CloudUtil.getMessagingService(); //To generate MessagingService once before all threads
            service = Executors.newSingleThreadExecutor();
            service.execute(CloudIterativeDirectPublisher::run);
            CloudDirectAsyncReceiver.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(service != null) service.shutdown();
        }
    }
}
