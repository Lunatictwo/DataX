# DataX ElasticSearch Writer 插件文档

## 1. 快速介绍
es5xwriter 插件提供向Elasticsearch 指定索引同步其他数据源的功能，同时提供解析字段为json body的功能。

## 2. 功能与限制
1. 用户可通过配置，实现ES数据的导入，对于需要解析为json的字段，比如url参数body，可以在配置文件中显式指定。
2. 目前使用过MySQL reader读取数据，es5xwriter 写入ES，效果理想
3. 目前仅支持ES 5.X版本，如需支持其他版本，用户只需修改部分代码及依赖即可。

## 3. 使用方式
#### 3.1 配置文件样例
```$json
    {
        "job": {
            "content": [
                {
                    "reader": {
                        "name": "mysqlreader",
                        "parameter": {
                            "column": ["*"],
                            "connection": [
                                {
                                    "jdbcUrl": ["jdbc:mysql://127.0.0.1:3306/testdb"],
    				                "querySql": ["select * from t_test"],
                                }
                            ],
    			            "password": "pass",
                            "username": "username",
                            "where": "",
    			            "splitPk":"id"
                        }
                    },
                    "writer": {
                        "name": "es5xwriter",
                        "parameter": {
                            "attributeNameSplit": ",",
    			            "attributeNameString": "id,create_time,uid,data_str",
                            "batchSize": "1000",
                            "className": "com.alibaba.datax.plugin.writer.es5xwriter.SimpleTestEntity",
                            "esClusterIP": "192.168.1.1",
                            "esClusterName": "test-es-cluster",
    			            "esClusterPort": "9300",
    			            "esEnableSniff": false,
                            "esIndex": "test_index",
                            "esType": "doc",
    			            "urlFieldToParseJson": "data_str",
    			            "timeField":"create_time"
                        }
                    }
                }
            ],
            "setting": {
                "speed": {
                    "channel": 10
                }
            }
        }
    }

```
#### 3.1 参数说明
* attributeNameString    
    * 要存入的属性名（列名）
    * 必选：是 
    * 默认值：无 
* attributeNameSplit
    * 属性分隔符
    * 必选：是 
    * 默认值：无 
* batchSize
    * 每次写入ES的条数
    * 必选：是 
    * 默认值：无 
* className
    * ES index对应的实体类
    * 必选：是 
    * 默认值：无 
* esClusterIP
    * ES 集群ip
    * 必选：是 
    * 默认值：无 
* esClusterName
    * ES 集群名
    * 必选：是 
    * 默认值：无 
* esClusterPort
    * ES 集群通信端口
    * 必选：否 
    * 默认值：9300
* esEnableSniff
    * 该节点加入时是否开启嗅探
    * 必选：是 
    * 默认值：true 
* esIndex
    * ES 索引名
    * 必选：是 
    * 默认值：无 
* esType
    * ES 索引doc_type名
    * 必选：是 
    * 默认值：无 
* urlFieldToParseJson
    * 需要解析为json对象的字段
    * 必选：否
    * 默认值：无 
* timeField
    * 需要在ES mapping中映射为时间的字段
    * 必选：否
    * 默认值：无 
    
    
#### 3.2 ES Mapping设置
由于Mapping设置自由度较高，场景不固定，建议用户自己手动设置ES mapping template，方便日后维护。

## 4. 配置步骤
同其他DataX插件配置步骤。

## 5. 约束限制
略

## 6. FAQ
略