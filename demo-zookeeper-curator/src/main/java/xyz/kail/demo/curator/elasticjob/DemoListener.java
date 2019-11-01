package xyz.kail.demo.curator.elasticjob;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

@Slf4j
public class DemoListener implements TreeCacheListener {

    @Override
    public final void childEvent(final CuratorFramework client, final TreeCacheEvent event) throws Exception {
        ChildData childData = event.getData();
        if (null == childData) {
            return;
        }
        String path = childData.getPath();
        if (path.isEmpty()) {
            return;
        }

        log.info(path);
        log.info(event.getType().toString());
        log.info(new String(childData.getData()));
    }

}
