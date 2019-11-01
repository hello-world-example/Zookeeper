package xyz.kail.demo.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import xyz.kail.demo.zookeeper.core.R;

import java.io.IOException;

/**
 * @Description TODO
 * @author: 杨凯彬
 * Copyright(c)2015/8/21 15:19 ttpai All Rights Reserved.
 */
public class ZkTest {
    public static void main(String[] args) throws IOException {
        System.out.println(R.ZK_HOST);
        System.out.println(R.ZK_PORT);
        System.out.println(R.ZK_ADDRESS);

        ZooKeeper zooKeeper = new ZooKeeper(R.ZK_ADDRESS, 60 * 1000, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
            }
        });


        System.out.println("end !!!");

    }
}
