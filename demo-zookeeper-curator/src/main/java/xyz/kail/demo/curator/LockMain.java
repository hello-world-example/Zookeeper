package xyz.kail.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Created by kail on 2016/5/15.
 */
public class LockMain {

    private static String lock_path = "/kail/zk/locktest";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:21810")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, lock_path);

        try {
            interProcessMutex.acquire();
            System.out.println("asd");
            TimeUnit.SECONDS.sleep(123);
        } finally {
            interProcessMutex.release();
            client.close();
        }

    }
}
