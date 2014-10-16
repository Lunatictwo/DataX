package com.alibaba.datax.plugin.reader.mysqlreader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.datax.common.element.BoolColumn;
import com.alibaba.datax.common.element.BytesColumn;
import com.alibaba.datax.common.element.DateColumn;
import com.alibaba.datax.common.element.DoubleColumn;
import com.alibaba.datax.common.element.LongColumn;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.common.plugin.RecordSender;
import com.alibaba.datax.common.spi.Reader;
import com.alibaba.datax.common.util.Configuration;
import com.alibaba.datax.plugin.rdbms.util.DBUtil;
import com.alibaba.datax.plugin.rdbms.util.SqlFormatUtil;
import com.alibaba.datax.plugin.reader.mysqlreader.util.MysqlReaderSplitUtil;
import com.alibaba.datax.plugin.reader.mysqlreader.util.OriginalConfPretreatmentUtil;

public class MysqlReader extends Reader {

	public static class Master extends Reader.Master {
		private static final Logger LOG = LoggerFactory
				.getLogger(MysqlReader.Master.class);

		private static final boolean IS_DEBUG = LOG.isDebugEnabled();

		private Configuration originalConfig = null;

		@Override
		public void init() {
			this.originalConfig = getPluginJobConf();

			OriginalConfPretreatmentUtil.doPretreatment(this.originalConfig);

			if (IS_DEBUG) {
				LOG.debug("After init(), the originalConfig now is:[\n{}\n]",
						this.originalConfig.toJSON());
			}
		}

		@Override
		public List<Configuration> split(int adviceNumber) {
			return MysqlReaderSplitUtil.doSplit(this.originalConfig,
					adviceNumber);
		}

		@Override
		public void post() {
			LOG.debug("post()");
		}

		@Override
		public void destroy() {
			LOG.debug("destroy()");
		}

	}

	public static class Slave extends Reader.Slave {
		private static final Logger LOG = LoggerFactory
				.getLogger(MysqlReader.Slave.class);

		private Configuration readerSliceConfig;

		private String username;

		private String password;

		private String jdbcUrl;

		// 作为日志显示信息时，需要附带的通用信息。比如信息所对应的数据库连接等信息，针对哪个表做的操作
		private static String BASIC_MESSAGE;

		@Override
		public void init() {
			this.readerSliceConfig = getPluginJobConf();

			/* for database connection */
			this.username = this.readerSliceConfig.getNecessaryValue(
					Key.USERNAME, MysqlReaderErrorCode.REQUIRED_VALUE);
			this.password = this.readerSliceConfig.getNecessaryValue(
					Key.PASSWORD, MysqlReaderErrorCode.REQUIRED_VALUE);
			this.jdbcUrl = this.readerSliceConfig.getNecessaryValue(
					Key.JDBC_URL, MysqlReaderErrorCode.REQUIRED_VALUE);

			BASIC_MESSAGE = String.format("jdbcUrl:[%s]", this.jdbcUrl);
		}

		@Override
		public void startRead(RecordSender recordSender) {
			String querySql = this.readerSliceConfig.getString(Key.QUERY_SQL);
			String formattedSql = null;

			try {
				formattedSql = SqlFormatUtil.format(querySql);
			} catch (Exception unused) {
				// ignore it
			}
			LOG.info("\nSql [{}] \nTo jdbcUrl:[{}].",
					null != formattedSql ? formattedSql : querySql, jdbcUrl);

			ResultSet rs = null;

			int columnNumber = 0;
			Connection conn = DBUtil.getConnection("mysql", jdbcUrl, username,
					password);

			dealSessionConf(conn,
					this.readerSliceConfig.getMap(Key.SESSION, String.class));

			try {
				rs = DBUtil.query(conn, querySql, Integer.MIN_VALUE);
				ResultSetMetaData metaData = rs.getMetaData();
				columnNumber = rs.getMetaData().getColumnCount();

				while (rs.next()) {
					transportOneRecord(recordSender, rs, metaData, columnNumber);
				}

			} catch (Exception e) {
				LOG.error("Origin cause:[{}].", e.getMessage());
				throw new DataXException(MysqlReaderErrorCode.READ_RECORD_FAIL,
						e);
			} finally {
				DBUtil.closeDBResources(null, conn);
			}
		}

		private static String SESSION_TEMPLATE = "SET %s=%s;";

		private static void dealSessionConf(Connection conn,
				Map<String, String> sessionConfs) {

			boolean hasNoSessionConfs = (null == sessionConfs || sessionConfs
					.isEmpty());
			if (hasNoSessionConfs) {
				return;
			}

			Statement stmt;
			try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				LOG.error(
						"Origin cause:[%s], failed to createStatement, from:[{}].",
						e.getMessage(), BASIC_MESSAGE);
				throw new DataXException(MysqlReaderErrorCode.SQL_EXECUTE_FAIL,
						e);
			}

			String sessionSql = null;
			for (Entry<String, String> entry : sessionConfs.entrySet()) {
				sessionSql = String.format(SESSION_TEMPLATE, entry.getKey(),
						entry.getValue());

				LOG.info("execute sql:[{}], from:[{}].", sessionSql,
						BASIC_MESSAGE);
				try {
					DBUtil.executeSqlWithoutResultSet(stmt, sessionSql);
				} catch (SQLException e) {
					LOG.error(String
							.format("Origin cause:[%s], failed to execute sql:[%s], from:[%s].",
									e.getMessage(), sessionSql, BASIC_MESSAGE));
					throw new DataXException(
							MysqlReaderErrorCode.SQL_EXECUTE_FAIL, e);
				}
			}

			DBUtil.closeDBResources(stmt, null);
		}

		private void transportOneRecord(RecordSender recordSender,
				ResultSet rs, ResultSetMetaData metaData, int columnNumber) {
			Record record = recordSender.createRecord();

			try {
				for (int i = 1; i <= columnNumber; i++) {
					switch (metaData.getColumnType(i)) {

					case Types.CHAR:
					case Types.NCHAR:
					case Types.CLOB:
					case Types.NCLOB:
					case Types.VARCHAR:
					case Types.LONGVARCHAR:
					case Types.NVARCHAR:
					case Types.LONGNVARCHAR:
						record.addColumn(new StringColumn(rs.getString(i)));
						break;

					case Types.SMALLINT:
					case Types.TINYINT:
					case Types.INTEGER:
						record.addColumn(new LongColumn(rs.getString(i)));
						break;

					case Types.BIGINT:
					case Types.NUMERIC:
						record.addColumn(new LongColumn(rs.getString(i)));
						break;

					case Types.DECIMAL:
					case Types.FLOAT:
					case Types.REAL:
					case Types.DOUBLE:
						record.addColumn(new DoubleColumn(rs.getString(i)));
						break;

					case Types.TIME:
						record.addColumn(new DateColumn(rs.getTime(i)));
						break;

					case Types.DATE:
						record.addColumn(new DateColumn(rs.getDate(i)));
						break;

					case Types.TIMESTAMP:
						record.addColumn(new DateColumn(rs.getTimestamp(i)));
						break;

					case Types.BINARY:
					case Types.VARBINARY:
					case Types.BLOB:
					case Types.LONGVARBINARY:
						record.addColumn(new BytesColumn(rs.getBytes(i)));
						break;

					// TODO  可能要把 bit 删除
					case Types.BOOLEAN:
					case Types.BIT:
						record.addColumn(new BoolColumn(rs.getBoolean(i)));
						break;

					// TODO 添加BASIC_MESSAGE
					default:
						throw new Exception(
								String.format(
										"Unsupported Mysql Data Type. ColumnName:[%s], ColumnType:[%s], ColumnClassName:[%s].",
										metaData.getColumnName(i),
										metaData.getColumnType(i),
										metaData.getColumnClassName(i)));
					}

				}
				recordSender.sendToWriter(record);
			} catch (Exception e) {
				this.getSlavePluginCollector().collectDirtyRecord(record, e);
			}
		}

		@Override
		public void post() {
		}

		@Override
		public void destroy() {
		}

	}
}
