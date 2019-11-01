package xyz.kail.demo.curator.elasticjob;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

@Slf4j
public final class RegistryCenterConnectionStateListener implements ConnectionStateListener {

    @Override
    public void stateChanged(final CuratorFramework client, final ConnectionState newState) {
        log.error("{}", newState);
    }
}
