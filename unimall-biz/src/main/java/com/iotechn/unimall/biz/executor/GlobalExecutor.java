package com.iotechn.unimall.biz.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/12/27
 * Time: 21:04
 */
public class GlobalExecutor {

    private static final ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

}
