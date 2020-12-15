# Docker 方式安装





## 准备

```bash
$ docker pull zookeeper:3.6.2
```







## 单实例

### 启动

```bash
$ docker run -d \
  --name zoo \
  -p 2181:2181 \
  -p 8080:8080 \
  -p 7070:7070 \
  -e "ZOO_MY_ID=1" \
  -e "ZOO_ADMINSERVER_ENABLED=true" \
  -e "ZOO_4LW_COMMANDS_WHITELIST=*" \
  \
  -e "ZOO_CFG_EXTRA=metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider metricsProvider.httpPort=7070" \
  \
  -v /private/docker/volumes/zookeeper/1/data:/data \
  zookeeper:3.6.2
```

### HTTP API

```bash
# Admin
$ curl http://localhost:8080/commands

# Prometheus
$ http://localhost:7070/metrics
```



### zkCli

```bash
# Docker
$ docker run -it --rm \
		--link zoo:zk \
		zookeeper:3.6.2 zkCli.sh -server zk

# 其他 zk
$ docker run -it --rm zookeeper:3.6.2 zkCli.sh -server 10.10.16.160
```



### 四字

```bash
$ echo 'conf' | nc localhost 2181
```



## 集群

### stack.yml

```bash
version: '3.1'

services:
  zoo1:
    image: zookeeper:3.6.2
    hostname: zoo1
    ports:
      - 12181:2181
      - 18080:8080
      - 17070:7070
    environment:
      ZOO_MY_ID: 1
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888
      ZOO_CFG_EXTRA: metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider metricsProvider.httpPort=7070

  zoo2:
    image: zookeeper:3.6.2
    hostname: zoo2
    ports:
      - 22181:2181
      - 28080:8080
      - 27070:7070
    environment:
      ZOO_MY_ID: 2
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888
      ZOO_CFG_EXTRA: metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider metricsProvider.httpPort=7070
            
  zoo3:
    image: zookeeper:3.6.2
    hostname: zoo3
    ports:
      - 32181:2181
      - 38080:8080
      - 37070:7070
    environment:
      ZOO_MY_ID: 3
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888
      ZOO_CFG_EXTRA: metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider metricsProvider.httpPort=7070
```

### stack

```bash
# 
$ docker swarm init

# 启动
$ docker stack deploy -c stack.yml zoo-cluster

# 删除
$ docker stack rm zoo-cluster

# ------------------------------------------------------------
# ------------------------------------------------------------

$ docker stack ls
NAME                SERVICES            ORCHESTRATOR
zoo-cluster         3                   Swarm

$ docker stack services zoo-cluster
ID                  NAME                MODE                REPLICAS            IMAGE               PORTS
lhd9cp10ta0a        zoo-cluster_zoo3    replicated          1/1                 zookeeper:3.6.2     *:32181->2181/tcp, *:38080->8080/tcp
pd0l7fvp7iyb        zoo-cluster_zoo1    replicated          1/1                 zookeeper:3.6.2     *:12181->2181/tcp, *:18080->8080/tcp
x7s4q39ygs42        zoo-cluster_zoo2    replicated          1/1                 zookeeper:3.6.2     *:22181->2181/tcp, *:28080->8080/tcp
```





## Read More

- https://hub.docker.com/_/zookeeper 官方镜像