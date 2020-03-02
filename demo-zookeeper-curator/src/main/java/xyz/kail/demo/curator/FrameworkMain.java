package xyz.kail.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;


/**
 * Created by Kail on 2016/2/24.
 */
public class FrameworkMain {

    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:21810")
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace(FrameworkMain.class.getSimpleName())
                .defaultData(null)
                .build();
        client.start();


        client.getCuratorListenable().addListener(
                (curatorFramework, curatorEvent) -> {
                    System.out.println(curatorEvent.getPath());
                    System.out.println(curatorEvent.getName());
                    System.out.println(curatorEvent.getStat());
                    System.out.println(curatorEvent.getResultCode());
                    System.out.println(curatorEvent.toString());
                });

        String s = client.create().withMode(CreateMode.EPHEMERAL).forPath("/testSession");
        System.out.println(s);

        CloseableUtils.closeQuietly(client);

    }
}
