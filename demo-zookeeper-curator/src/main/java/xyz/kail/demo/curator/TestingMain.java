package xyz.kail.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Kail on 2016/2/29.
 */
public class TestingMain {

    private static TestingServer testingServer = null;

    static {
        try {
            testingServer = new TestingServer(-1, new File("D:/opt/data/zookeeper"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        CuratorFramework server = CuratorFrameworkFactory.builder()
                .connectString(testingServer.getConnectString())
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .build();

        server.start();

        List<String> paths = server.getChildren().forPath("/");
        for (String path : paths) {
            System.out.println(path);
        }

        String path = server.create().forPath("/testing");
        System.out.println(path);

        testingServer.stop();
    }
}
