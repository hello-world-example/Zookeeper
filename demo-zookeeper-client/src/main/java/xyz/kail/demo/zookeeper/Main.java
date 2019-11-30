package xyz.kail.demo.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.kail.demo.zookeeper.core.R;

import java.io.IOException;
import java.util.Objects;

/**
 *
 */
public class Main {

    static {
        R.initSlf4jSimple();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static void doit(ZooKeeper zk) throws KeeperException, InterruptedException {
        LOGGER.info("Session Id: {}", zk.getSessionId());
        LOGGER.info("State: {}", zk.getState());

        // read
        LOGGER.info("{}", zk.getChildren("/", true));
        LOGGER.info("{}", zk.getChildren("/zookeeper", true));

        if (Objects.isNull(zk.exists("/kail", false))) {
            // 临时节点下 EPHEMERAL，无法再创建临时节点
            final String pathKail = zk.create("/kail", R.toByte("noting"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            LOGGER.info("pathKail: {}", pathKail);
        }

        // 监听事件
//        zk.getChildren("/kail", event -> LOGGER.info("/kail event: {}", event));
//        zk.exists("/kail", event -> LOGGER.info("/kail event: {}", event));

        R.watchPath(zk, "/kail", event -> LOGGER.info("/kail event: {}", event));
        R.watchChildren(zk, "/kail", event -> LOGGER.info("/kail Children event: {}", event));


        final String path = zk.create(
                "/kail/test-crete-" + System.currentTimeMillis(),
                R.toByte("noting"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL
        );
        LOGGER.info("{}", path);

    }

    public static void main(String[] args) throws KeeperException, IOException, InterruptedException {

        ZooKeeper zk = new ZooKeeper(
                RR.ZK_HOST,
                60 * 1000,
                event -> LOGGER.info("{}", event)
        );

        try {
            doit(zk);
        } finally {
            System.in.read();

            // zk.close();

            LOGGER.info("end !!!");
        }

    }
}
