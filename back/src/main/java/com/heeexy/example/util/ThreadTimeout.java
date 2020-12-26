package com.heeexy.example.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadTimeout {
    public static void main(String[] args) {
        System.out.println("程序开始");
        final ExecutorService exec = Executors.newFixedThreadPool(1);

        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                // 开始执行耗时操作
                Thread.sleep(1000 * 3);
                System.out.println("程序早结束了，已经没用了。");
                return "线程执行完成.";
            }
        };
        Future<String> future = null;
        try {
            future = exec.submit(call);
            String obj = future.get(1000 * 1, TimeUnit.MILLISECONDS); // 任务处理超时时间设为 1 秒
            System.out.println("任务成功返回:" + obj);
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦....");
            if (future != null) {
                future.cancel(true);
            }
        } catch (Exception e) {
            System.out.println("处理失败.");
        } finally {
            System.out.println("关闭线程池");
            // 关闭线程池
            exec.shutdown();
        }
        System.out.println("程序结束");
    }
}