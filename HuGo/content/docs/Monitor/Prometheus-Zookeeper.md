# 3.6.0 原生 Prometheus 监控



## Zookeeper 开启 Prometheus

详见: [Docker 方式安装](/Zookeeper/docs/Quick-Start/Install-by-Docker/)， 关键参数如下：

```
metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider
metricsProvider.httpPort=7070
```



## Prometheus 配置

### prometheus.yml

```bash
$ mkdir /private/docker/volumes/prometheus/

# 内容如下
# https://github.com/prometheus/prometheus/blob/master/documentation/examples/prometheus.yml
$ vim /private/docker/volumes/prometheus/prometheus.yml
global:
  scrape_interval:     15s
  evaluation_interval: 15s
scrape_configs:
  - job_name: 'prometheus'
    static_configs:
    - targets: ['localhost:9090']
  - job_name: 'zookeeper-monitor'
    static_configs:
    - targets: ['zoo1:7070','zoo2:7070','zoo3:7070']
```



### Compose 内容如下

```yaml
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

  prometheus:
    image: prom/prometheus:v2.23.0
    hostname: prometheus
    ports:
      - 19090:9090
    volumes:
      - /private/docker/volumes/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml
```

### 启动

```bash
$ docker stack deploy -c docker-prometheus.yml zoo-prom-cluster

# 删除
$ docker stack rm zoo-prom-cluster
```



## Grafana 报表

> ZooKeeper by Prometheus：[https://grafana.com/grafana/dashboards/10465](https://grafana.com/grafana/dashboards/10465)
>
> 导入 Prometheus 即可



## Read More

- [ZooKeeper Monitor Guide](https://zookeeper.apache.org/doc/current/zookeeperMonitor.html#Prometheus)

- Docker Hub [prom/prometheus](https://hub.docker.com/r/prom/prometheus)

  