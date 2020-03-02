package xyz.kail.demo.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.io.File;
import java.util.List;

/**
 * 创建测试服务器
 *
 * @see xyz.kail.demo.server.TestingServerApp
 */
@Slf4j
public class TestingServerApp {


    public static void main(String[] args) throws Exception {

        try (TestingServer testingServer = new TestingServer(21810, new File("/tmp/zookeeper"))) {

            log.info("ConnectString:{}", testingServer.getConnectString());

            CuratorFramework server = CuratorFrameworkFactory.builder()
                    .connectString(testingServer.getConnectString())
                    .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                    .build();

            server.start();


            List<String> paths = server.getChildren().forPath("/");
            for (String path : paths) {
                log.info("list path:{} ", path);
            }

            System.in.read();

        }


    }
}
