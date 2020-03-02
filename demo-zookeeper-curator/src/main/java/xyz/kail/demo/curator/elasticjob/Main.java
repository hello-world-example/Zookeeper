package xyz.kail.demo.curator.elasticjob;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {

    private static final String SERVER_LISTS = "172.16.2.219:2181";

    private static final int MAX_RETRIES = 3;
    private static final int BASE_SLEEP_TIME_MILLISECONDS = 1000;
    private static final int MAX_SLEEP_TIME_MILLISECONDS = 3000;

    private static final String NAMESPACE = "kail";

    public static void main(String[] args) throws Exception {

        final ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(BASE_SLEEP_TIME_MILLISECONDS, MAX_RETRIES, MAX_SLEEP_TIME_MILLISECONDS);

        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(SERVER_LISTS)
                .retryPolicy(exponentialBackoffRetry)
                .namespace(NAMESPACE);


        final CuratorFramework client = builder.build();
        client.start();

        log.info("CuratorFramework.start()");


        if (!client.blockUntilConnected(MAX_SLEEP_TIME_MILLISECONDS * MAX_RETRIES, TimeUnit.MILLISECONDS)) {
            client.close();
            throw new KeeperException.OperationTimeoutException();
        }

        // client.create().forPath("/job");

        /*
         *
         */
        client.getConnectionStateListenable().addListener(new RegistryCenterConnectionStateListener());

        /*
         *
         */
        TreeCache cache = new TreeCache(client, "/job");
        cache.start();

        cache.getListenable().addListener(new DemoListener());


        // client.create().forPath("/job/asd");
        // client.create().forPath("/job/asd1");



        /*
         *
         */
        System.in.read();

    }

}
