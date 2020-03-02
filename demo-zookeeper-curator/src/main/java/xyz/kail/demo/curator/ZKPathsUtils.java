package xyz.kail.demo.curator;

import org.apache.curator.framework.imps.DefaultACLProvider;
import org.apache.curator.utils.DefaultZookeeperFactory;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kail on 2016/2/29.
 */
public class ZKPathsUtils {

    private static void needZookeeper() throws Exception {

        ZooKeeper zooKeeper = new DefaultZookeeperFactory().newZooKeeper("127.0.0.1:21810", 1000 * 60, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
            }
        }, false);


        System.out.println("\r\n==== getSortedChildren ====");
        List<String> sortedChildren = ZKPaths.getSortedChildren(zooKeeper, "/");
        for (String path : sortedChildren) {
            System.out.println(path);
        }

//        System.out.println("\r\n==== mkdirs ====");
//        ZKPaths.mkdirs(zooKeeper, "/curator/test/mkdirs");

        System.out.println("\r\n==== mkdirs ====");
        ZKPaths.mkdirs(zooKeeper, "/curator/test/mkdirs", true, new DefaultACLProvider() {

            @Override
            public List<ACL> getAclForPath(String path) {
                return new ArrayList<ACL>() {{
                    add(new ACL(ZooDefs.Perms.ALL, new Id("kail", "1723")));
                }};
            }
        });


//        System.out.println("\r\n==== deleteChildren ====");
//        ZKPaths.deleteChildren(zooKeeper, "/curatora", true);

    }

    private static void localKit() throws Exception {

        System.out.println("\n==== getPathAndNode ====");
        ZKPaths.PathAndNode pathAndNode = ZKPaths.getPathAndNode("/asd");
        System.out.println(pathAndNode.getPath());
        System.out.println(pathAndNode.getNode());

        System.out.println("\r\n==== getPathAndNode ====");
        String nodeFromPath = ZKPaths.getNodeFromPath("/asd");
        System.out.println(nodeFromPath);

        System.out.println("\r\n==== fixForNamespace ====");
        String fixForNamespace = ZKPaths.fixForNamespace("namespace", "node");
        System.out.println(fixForNamespace);

        System.out.println("\r\n==== makePath ====");
        String makePath = ZKPaths.makePath("parent", "node");
        System.out.println(makePath);
    }


    public static void main(String[] args) throws Exception {

//        localKit();
        System.out.println("\r\n---------------------------");
        needZookeeper();

    }

}
