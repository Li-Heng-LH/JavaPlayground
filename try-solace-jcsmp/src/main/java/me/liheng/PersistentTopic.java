package me.liheng;

import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPSession;

import java.io.IOException;

public class PersistentTopic {

    // Queues set up in Broker:
    // persistent/one -> queue1
    //                -> queue2
    // persistent/two -> queue2
    public static void main(String[] args) throws IOException, JCSMPException, InterruptedException {

        final JCSMPSession session = Util.getSession();


        Thread.sleep(2000); //Needed
        //prod.close();
        //cons.close();
        session.closeSession();
    }
}
