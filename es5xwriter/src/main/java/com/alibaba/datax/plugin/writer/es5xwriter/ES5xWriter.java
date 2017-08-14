package com.alibaba.datax.plugin.writer.es5xwriter;

import com.alibaba.datax.common.plugin.RecordReceiver;
import com.alibaba.datax.common.spi.Writer;
import com.alibaba.datax.common.util.Configuration;

import java.util.ArrayList;
import java.util.List;

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

        @Override
        public void init() {

        }

        @Override
        public void destroy() {

        }

        @Override
        public void startWrite(RecordReceiver lineReceiver) {

        }
    }


}
