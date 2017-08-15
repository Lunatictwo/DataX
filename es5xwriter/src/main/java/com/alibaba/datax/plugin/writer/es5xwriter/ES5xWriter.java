package com.alibaba.datax.plugin.writer.es5xwriter;

import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.plugin.RecordReceiver;
import com.alibaba.datax.common.spi.Writer;
import com.alibaba.datax.common.util.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zehui on 2017/8/14.
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

        private String esIndex = null;

        private String esType = null;

        private String attributeNameString = null;

        private String attributeNameSplit = null;

        private String[] attributeNames = null;

        private String className = null;

        //Gson序列化的时候限制格式，使用GsonBuilder
        private Gson gson = null;

        private TransportClient client = null;

        private Integer batchSize = null;

        private static final Logger LOG = LoggerFactory.getLogger(Task.class);

        private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public void init() {
            this.writerSliceConfiguration = super.getPluginJobConf();
            this.esClusterName = writerSliceConfiguration.getString(Key.esClusterName);
            this.esClusterIP = writerSliceConfiguration.getString(Key.esClusterIP);
            this.esClusterPort = writerSliceConfiguration.getInt(Key.esClusterPort, 9300);
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
        }

        @Override
        public void prepare() {
            super.prepare();
            try {
                Settings esSettings = Settings.builder()
                        .put("cluster.name", "my-application") //设置ES实例的名称
                        .put("client.transport.sniff", false) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中(外网连接请置为false)
                        .build();
                this.client = new PreBuiltTransportClient(esSettings);//初始化client较老版本发生了变化，此方法有几个重载方法，初始化插件等。
                //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
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
                    LOG.info("---------------------------------single record---------------------------------------");
                    object = Class.forName(className).newInstance();
                    int fieldNum = record.getColumnNumber();
                    if (null != record && fieldNum > 0) {
                        LOG.info(record.toString());//单条数据
                        LOG.info(String.valueOf(fieldNum));//单条数据中的字段数
                        attributeValueMap = new HashMap<String, String>();
                        for (int i = 0; i < fieldNum; i++) {
                            attributeValueMap.put(attributeNames[i].toLowerCase(), record.getColumn(i).asString());
                        }
                        LOG.info(attributeValueMap.toString());
                        Class<?> superClass = object.getClass();
                        Field[] fields = superClass.getDeclaredFields();
                        LOG.info(String.valueOf(fields.length));
                        LOG.info(String.valueOf(fields));
                        for (int i = 0, len = fields.length; i < len; i++) {
                            Field field = fields[i];
                            String fieldNameLowerCase = field.getName().toLowerCase();
                            LOG.info(fieldNameLowerCase);
                            //如果实体类不包含该列字段，continue
                            if (!attributeValueMap.containsKey(fieldNameLowerCase)) continue;
                            String valueString = attributeValueMap.get(fieldNameLowerCase);
                            LOG.info(valueString);
                            try {
                                value = convertValueByFieldType(field.getType(), valueString);
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
                        .setIndex(esIndex).setType(esIndex).setId(entity.getId()).setSource(gson.toJson(entity));
                LOG.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                LOG.info(gson.toJson(entity));
                LOG.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                prepareBulk.add(indexRequestBuilder);
//                entity.remove_id();
//                String source = gson.toJson(entity);
//                irb.setSource(source);
//                prepareBulk.add(irb);
//                LOG.info(entity.toString());
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
