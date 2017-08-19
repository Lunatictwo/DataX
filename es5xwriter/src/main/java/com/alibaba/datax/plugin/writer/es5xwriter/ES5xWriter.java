package com.alibaba.datax.plugin.writer.es5xwriter;

import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.plugin.RecordReceiver;
import com.alibaba.datax.common.spi.Writer;
import com.alibaba.datax.common.util.Configuration;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zehui on 2017/8/14.
 * TODO:ES mapping 设置日期字段测试
 */
public class ES5xWriter extends Writer {
    public static class Job extends Writer.Job {
        private Configuration originalConfiguration = null;

        @Override
        public void init() {
            this.originalConfiguration = super.getPluginJobConf();
        }

        @Override
        public void prepare() {
            super.prepare();
        }

        @Override
        public void preCheck() {
            super.preCheck();
        }

        @Override
        public void preHandler(Configuration jobConfiguration) {
            super.preHandler(jobConfiguration);
        }

        @Override
        public void post() {
            super.post();
        }

        @Override
        public void postHandler(Configuration jobConfiguration) {
            super.postHandler(jobConfiguration);
        }

        @Override
        public void destroy() {

        }

        @Override
        public List<Configuration> split(int mandatoryNumber) {
            List<Configuration> writerSplitConfiguration = new ArrayList<Configuration>();
            for (int i = 0; i < mandatoryNumber; i++) {
                writerSplitConfiguration.add(this.originalConfiguration);
            }
            return writerSplitConfiguration;
        }
    }

    public static class Task extends Writer.Task {
        private Configuration writerSliceConfiguration = null;

        private String esClusterName = null;

        private String esClusterIP = null;

        private Integer esClusterPort = null;

        private Boolean esEnableSniff = null;

        private String esIndex = null;

        private String esType = null;

        private String attributeNameString = null;

        private String attributeNameSplit = null;

        private String[] attributeNames = null;

        private String className = null;

        //Gson序列化的时候限制格式，使用GsonBuilder
        private Gson gson = null;
        private JsonParser jsonParser = null;

        private TransportClient client = null;

        private Integer batchSize = null;

        private static final Logger LOG = LoggerFactory.getLogger(Task.class);

        private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        private String urlFieldToParseJson = null;

        private String timeField = null;

        @Override
        public void init() {
            this.writerSliceConfiguration = super.getPluginJobConf();
            this.esClusterName = writerSliceConfiguration.getString(Key.esClusterName);
            this.esClusterIP = writerSliceConfiguration.getString(Key.esClusterIP);
            this.esClusterPort = writerSliceConfiguration.getInt(Key.esClusterPort, 9300);
            this.esEnableSniff = writerSliceConfiguration.getBool(Key.esEnableSniff, false);
            this.esIndex = writerSliceConfiguration.getString(Key.esIndex);
            this.esType = writerSliceConfiguration.getString(Key.esType);
            this.attributeNameString = writerSliceConfiguration.getString(Key.attributeNameString);
            this.attributeNameSplit = writerSliceConfiguration.getString(Key.attributeNameSplit, ",");
            attributeNames = attributeNameString.split(attributeNameSplit);
            this.className = writerSliceConfiguration.getString(Key.className);
            this.batchSize = writerSliceConfiguration.getInt(Key.batchSize, 1000);
            this.gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            this.jsonParser = new JsonParser();
            this.urlFieldToParseJson = writerSliceConfiguration.getString(Key.urlFieldToParseJson, "");
            this.timeField = writerSliceConfiguration.getString(Key.timeField, "");
        }

        @Override
        public void prepare() {
            super.prepare();
            try {
                Settings esSettings = Settings.builder()
                        .put("cluster.name", this.esClusterName) //设置ES实例的名称
                        .put("client.transport.sniff", false) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中(外网连接请置为false)
                        .build();
                this.client = new PreBuiltTransportClient(esSettings);//初始化client较老版本发生了变化，此方法有几个重载方法，初始化插件等。
                //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(this.esClusterIP), this.esClusterPort));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void preCheck() {
            super.preCheck();
        }

        @Override
        public void preHandler(Configuration jobConfiguration) {
            super.preHandler(jobConfiguration);
        }

        @Override
        public void post() {
            super.post();
        }

        @Override
        public void postHandler(Configuration jobConfiguration) {
            super.postHandler(jobConfiguration);
        }

        @Override
        public void destroy() {
            client.close();
        }

        @Override
        public void startWrite(RecordReceiver lineReceiver) {
            List<Record> writerBuffer = new ArrayList<Record>(this.batchSize);
            Record record = null;
            while ((record = lineReceiver.getFromReader()) != null) {
                writerBuffer.add(record);
                if (writerBuffer.size() >= this.batchSize) {
                    bulkSaveOrUpdateES(writerBuffer);
                    writerBuffer.clear();
                }
            }
            if (!writerBuffer.isEmpty()) {
                bulkSaveOrUpdateES(writerBuffer);
                writerBuffer.clear();
            }
        }

        private void bulkSaveOrUpdateES(List<Record> writerBuffer) {
            Record record = null;
            Object object = null;
            Object value = null;
            Map<String, String> attributeValueMap = null;
            List<ESEntity> entities = new ArrayList<ESEntity>();
            try {
                for (int w = 0, wlen = writerBuffer.size(); w < wlen; w++) {
                    //此时获取到record为DataX中的数据类型
                    record = writerBuffer.get(w);
                    object = Class.forName(className).newInstance();
                    int fieldNum = record.getColumnNumber();
                    if (null != record && fieldNum > 0) {
                        attributeValueMap = new HashMap<String, String>();
                        for (int i = 0; i < fieldNum; i++) {
                            attributeValueMap.put(attributeNames[i].toLowerCase(), record.getColumn(i).asString());
                        }
                        Class<?> superClass = object.getClass();
                        Field[] fields = superClass.getDeclaredFields();
                        for (Field field : fields) {
                            String fieldNameLowerCase = field.getName().toLowerCase();
                            //如果配置中未填写类的该列字段，跳过
                            if (!attributeValueMap.containsKey(fieldNameLowerCase)) {
                                continue;
                            }
                            String valueString = attributeValueMap.get(fieldNameLowerCase);
                            try {
                                //如果是需要解析Json的字段，解析为Json
                                if (this.urlFieldToParseJson.equals(fieldNameLowerCase)) {
                                    //url 转码
                                    valueString = URLDecoder.decode(valueString, "utf-8");
                                    String[] paramTuples = valueString.split("&");
                                    JSONObject jsonObject = new JSONObject();
                                    for (String singleTuple : paramTuples) {
                                        jsonObject.put(singleTuple.split("=", -1)[0], singleTuple.split("=", -1)[1]);
                                    }
                                    value = jsonObject;
                                } else {
                                    value = convertValueByFieldType(field.getType(), valueString);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (field.isAccessible()) { // if field is private or public
                                field.set(object, value);
                            } else {
                                field.setAccessible(true);
                                field.set(object, value);
                                field.setAccessible(false);
                            }
                        }
                        entities.add((ESEntity) object);
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
            bulkSaveOrUpdate(entities, esIndex, esType);
        }

        private void bulkSaveOrUpdate(List<ESEntity> entities, String esIndex, String esType) {
            if (null == entities || entities.isEmpty())
                return;
            BulkRequestBuilder prepareBulk = client.prepareBulk();
            for (ESEntity entity : entities) {
                IndexRequestBuilder indexRequestBuilder = this.client.prepareIndex()
                        .setIndex(esIndex).setType(esType).setId(entity.getId());
                JsonObject entityJsonObj = jsonParser.parse(gson.toJson(entity)).getAsJsonObject();
                entityJsonObj.remove("id");
                if (!"".equals(this.timeField)) {
                    entityJsonObj.add("@timestamp", entityJsonObj.get(this.timeField));
                }
                indexRequestBuilder.setSource(gson.toJson(entityJsonObj));
//                LOG.info(gson.toJson(entityJsonObj));
                prepareBulk.add(indexRequestBuilder);
            }
            prepareBulk.execute().actionGet();
        }

        private Object convertValueByFieldType(Class<?> type, Object value) throws ParseException {
            Object finalValue = value;
            if (String.class.isAssignableFrom(type)) {
                finalValue = (null == value) ? "NA" : String.valueOf(value);
            } else if (Boolean.class.isAssignableFrom(type)) {
                finalValue = (null == value) ? Boolean.FALSE : Boolean.parseBoolean(String.valueOf(value));
            } else if (Integer.class.isAssignableFrom(type)) {
                finalValue = (null == value) ? 0 : Integer.parseInt(String.valueOf(value));
            } else if (Long.class.isAssignableFrom(type)) {
                finalValue = (null == value) ? 0 : Long.parseLong(String.valueOf(value));
            } else if (Float.class.isAssignableFrom(type)) {
                finalValue = (null == value) ? 0 : Float.parseFloat(String.valueOf(value));
            } else if (Double.class.isAssignableFrom(type)) {
                finalValue = (null == value) ? 0 : Double.parseDouble(String.valueOf(value));
            } else if (Date.class.isAssignableFrom(type)) {
                finalValue = (null == value) ? null : format.parse((String) value);
            } else if (BigDecimal.class.isAssignableFrom(type)) {
                finalValue = (null == value) ? new BigDecimal("0") : new BigDecimal(String.valueOf(value));
            }
            return finalValue;
        }
    }




}
