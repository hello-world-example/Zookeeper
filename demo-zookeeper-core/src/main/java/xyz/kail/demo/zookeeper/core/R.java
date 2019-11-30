package xyz.kail.demo.zookeeper.core;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.impl.SimpleLogger;

import java.nio.charset.StandardCharsets;

public class R {

    public static void initSlf4jSimple() {
        // 默认是 System.err
        System.setProperty(SimpleLogger.LOG_FILE_KEY, "System.out");
        // 日志显示时间
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "yyyy-MM-dd HH:mm:ss.SSS");

        // 默认是 Error 级别
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
        // 当前项目为 Debug 级别
        System.setProperty(SimpleLogger.LOG_KEY_PREFIX + "xyz.kail", "DEBUG");
    }


    public static byte[] toByte(String str) {
        if (null == str) {
            return null;
        }
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static void watchPath(ZooKeeper zk, String path, Watcher watcher) throws KeeperException, InterruptedException {
        new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                watcher.process(event);

                try {
                    watch();
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            public void watch() throws KeeperException, InterruptedException {
                zk.exists(path, this);
            }

        }.watch();
    }

    public static void watchChildren(ZooKeeper zk, String path, Watcher watcher) throws KeeperException, InterruptedException {
        new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                watcher.process(event);

                try {
                    watch();
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            public void watch() throws KeeperException, InterruptedException {
                zk.getChildren(path, this);
            }

        }.watch();
    }


}
