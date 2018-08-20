package com.concredito.Main;
import com.concredito.models.Request;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static void processRequests() {
        Request requestsHandler = new Request();
        requestsHandler.processRequests(requestsHandler.getNextRequest());
    }

    public static void main(String[] args) {
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                processRequests();
            }
        };
        t.schedule(tt, 10000, 10000);
    }
}
