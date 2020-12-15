# 四字命令 



## 使用方式

```bash
$ echo mntr | nc localhost 2181
```



## 简介

|    命令    | since |         Admin         | 简介                                                         |
| :--------: | :---: | :-------------------: | ------------------------------------------------------------ |
| **`conf`** | 3.3.0 |     configuration     | 查看服务配置                                                 |
| **`cons`** | 3.3.0 |      connections      | 连接到服务器的 客户端的完全的连接 */* 会话的详细信息<br />包括 “接受 */* 发送” 的包数量、会话 *id* 、操作延迟、最后的操作执行等等信息。 |
|   *crst*   | 3.3.0 | connection_stat_reset | 重置 `cons`                                                  |
|   `dump`   |       |         dump          | 列出未完成会话和临时节点                                     |
| **`envi`** |       |      environment      | 服务端环境信息                                               |
|   `ruok`   |       |         ruok          | 服务处于正确状态 返回 `imok` ”，否则不做人任何返回           |
|            |       |                       |                                                              |
|   `srvr`   |       |     server_stats      | 性能统计信息                                                 |
| **`stat`** | 3.3.0 |         stats         | 客户端的列表 和 `srvr`                                       |
|   *stst*   |       |      stat_reset       | 重置 `stat`                                                  |
|            |       |                       |                                                              |
| **`wchs`** | 3.3.0 |        watchs         | Watch 信息 统计                                              |
|   `wchc`   | 3.3.0 |     watch_summary     | Watch 信息 Group by Session                                  |
|   `wchp`   | 3.3.0 |    watches_by_path    | Watch 信息 Group by Path                                     |
|            |       |                       |                                                              |
|    dirs    | 3.5.1 |         dirs          | 以字节为单位显示快照和日志文件的总大小                       |
| **`mntr`** | 3.4.0 |        monitor        | 集群运行状态整体指标                                         |
|            |       |                       |                                                              |
|    isro    | 3.4.0 |     is_read_only      | 测试集群是否处于 ReadOnly 状态，`ro` 、`rw`                  |



## 重要指令

### mntr

| 指标 | 简介 | 示例 |
| ---- | ---- | ---- |
|zk_version|版本|3.4.6-1569965, built on 02/20/2014 09:09 GMT|
|**zk_min_latency**|最小 延时|0|
|**zk_avg_latency**|平均 延时|0|
|zk_max_latency|最大 延时|16|
|zk_packets_received|接收包数量|6705007|
|zk_packets_sent|发送包数量|6743582|
|**zk_num_alive_connections**|活跃，配合 `cons` 查看详情|125|
|**zk_outstanding_requests**|排队请求数，zk 到达瓶颈时该值会增加|0|
|zk_server_state|当前节点角色|follower|
|**zk_znode_count**|znode 节点数|12148|
|**zk_watch_count**|watch 数量|2634|
|zk_ephemerals_count|临时节点数|1030|
|zk_approximate_data_size|近似数据大小|7935888|
|**zk_open_file_descriptor_count**|打开文件数|153|
|zk_max_file_descriptor_count|最大打开文件数|204800|



### cons

> queued=0,recved=267621,sent=267621,sid=0x17644c9559f00a4,lop=PING,est=1607479755363,to=5000,lcxid=0x0,lzxid=0xffffffffffffffff,lresp=1607926070374,llat=0,minlat=0,avglat=0,maxlat=6

| 指标 | 描述 | 示例 |
| ---- | ---- | ---- |
|queued|队列中待处理的数量|0|
|recved|接收包数量|267621|
|sent|发送的包数量|267621|
|sid|Session ID|0x17644c9559f00a4|
|lop|最后的操作|PING|
|est|连接的时间|1607479755363|
|to|超时时间|5000|
|lcxid|客户端最后操作的序列号|0x0|
|lzxid|最后的事务序列号|0xffffffffffffffff|
|lresp|最后回复时间戳|1607926070374|
|llat|最后操作的延时|0|
|minlat|最小延时|0|
|avglat|平均延时|0|
|maxlat|最大延时|6|



## Read More

- [ZooKeeper Commands: The Four Letter Words](https://zookeeper.apache.org/doc/r3.4.14/zookeeperAdmin.html#sc_zkCommands)
- 3.5.0 后增加 [The AdminServer](https://zookeeper.apache.org/doc/r3.6.2/zookeeperAdmin.html#sc_adminserver) ， 四字命令可能会被废弃