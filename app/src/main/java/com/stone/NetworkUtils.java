package com.stone;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Jerry on 2017/12/8
 */

public class NetworkUtils {
    private static ScheduledExecutorService threadPool;

    public static ScheduledExecutorService getThreadPool() {
        if (threadPool == null) {
            threadPool = Executors.newScheduledThreadPool(3);
        }
        return threadPool;
    }
}
