package xyz.kail.demo.curator;

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
 * @see xyz.kail.demo.curator.TestingMain
 */
@Slf4j
public class TestingMain {

    private static TestingServer testingServer = null;

    static {
        try {
            testingServer = new TestingServer(-1, new File("/tmp/zookeeper"));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void main(String[] args) throws Exception {

        log.info(testingServer.getConnectString());

        CuratorFramework server = CuratorFrameworkFactory.builder()
                .connectString(testingServer.getConnectString())
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .build();

        server.start();

        List<String> paths = server.getChildren().forPath("/");
        for (String path : paths) {
            log.info("path:{} ", path);
        }

        String path = server.create().forPath("/testing");
        log.info(path);

        testingServer.stop();
    }
}
