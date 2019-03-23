package net.myerichsen.hremvp.databaseadmin;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IContentProvider;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreH2ConnectionPool;

/**
 * Provide H2 data to the table navigator and the table editor
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 30. okt. 2018
 *
 */
public class H2TableProvider implements IContentProvider {
	// private final static Logger LOGGER =
	// Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Connection conn = null;

	private final String COUNT = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = ?";
	private final String COLUMNS = "SELECT COLUMN_NAME, TYPE_NAME, DATA_TYPE, NUMERIC_PRECISION, NUMERIC_SCALE, CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = ?";

	private int count;
	private final List<H2TableModel> modelList;
	private H2TableModel model;
	private final String tableName;
	private PreparedStatement ps;
	private ResultSet rs;
	private List<Object> row;
	private List<List<Object>> rowList;

	/**
	 * Constructor
	 *
	 * @param tableName Name of H2 table
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 *
	 */
	public H2TableProvider(String tableName) throws Exception {
		String type;

		conn = HreH2ConnectionPool.getConnection();
		this.tableName = tableName;
		modelList = new ArrayList<>();

		// Get number of columns in H2 table
		ps = conn.prepareStatement(COUNT);
		ps.setString(1, tableName);

		rs = ps.executeQuery();

		if (rs.next()) {
			count = rs.getInt(1);
		}

		// Get names and other properties of columns in H2 tables
		ps = conn.prepareStatement(COLUMNS);
		ps.setString(1, tableName);
		rs = ps.executeQuery();

		while (rs.next()) {
			model = new H2TableModel();
			model.setName(rs.getString(1));
			type = rs.getString(2);
			model.setType(type);
			model.setNumericType(rs.getInt(3));

			switch (model.getNumericType()) {
			case Constants.BIGINT:
			case Constants.DOUBLE:
			case Constants.SMALLINT:
			case Constants.INTEGER:
				model.setPrecision(rs.getInt(4));
				model.setScale(rs.getInt(5));
				break;
			case Constants.VARCHAR:
			case Constants.VARBINARY:
				model.setScale(rs.getInt(6));
				break;
			default:
				break;
			}

			modelList.add(model);
		}
	}

	/**
	 * Delete a row in the H2 table
	 *
	 * @param recordNum Key field
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public void delete(int recordNum) throws Exception {
		String s = tableName.substring(0, tableName.length() - 1);
		if (tableName.equals("SEXES")) {
			s = "SEXES";
		}

		final String DELETE = "DELETE FROM PUBLIC." + tableName + " WHERE " + s
				+ "_PID = ?";
		ps = conn.prepareStatement(DELETE);
		ps.setInt(1, recordNum);
		ps.executeUpdate();
	}

	/**
	 * Delete all rows in the H2 table
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public void deleteAll() throws Exception {
		final String DELETEALL = "DELETE FROM PUBLIC." + tableName;

		ps = conn.prepareStatement(DELETEALL);
		ps.executeUpdate();
	}

	/**
	 * Get the count of columns in the H2 table
	 *
	 * @return The count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Get a list of models, each with column name, type, and optional value
	 *
	 * @return A list of columns
	 */
	public List<H2TableModel> getModelList() {
		return modelList;
	}

	/**
	 * Import a CSV file into the H2 table
	 *
	 * @param fileName Name of the CSV file
	 * @return Number of rows imported
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public int importCsv(String fileName) throws Exception {
		String IMPORTCSV;
		int rowCount = 0;

		if (tableName.equals("HDATES")) {
			IMPORTCSV = "INSERT INTO PUBLIC.HDATES (SELECT HDATE_PID, "
					+ "TABLE_ID, ORIGINAL_TEXT, CONVERT(PARSEDATETIME(DATE, 'dd-MM-yyyy'), DATE), "
					+ "CONVERT(PARSEDATETIME(SORT_DATE, 'dd-MM-yyyy'), DATE), SURETY from csvread('hdates.csv'));";
		} else {
			IMPORTCSV = "INSERT INTO PUBLIC." + tableName
					+ " (SELECT * from csvread('" + fileName + "'));";
		}

		ps = conn.prepareStatement(IMPORTCSV);
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Insert a row into the H2 table
	 *
	 * @param columns Number of columns in the row
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public void insert(List<H2TableModel> columns) throws Exception {
		H2TableModel h2TableModel;

		final StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO PUBLIC." + tableName + " (");

		Iterator<H2TableModel> iterator = columns.iterator();

		if (iterator.hasNext()) {
			h2TableModel = iterator.next();
			sb.append(h2TableModel.getName());
		}

		while (iterator.hasNext()) {
			h2TableModel = iterator.next();
			sb.append(", " + h2TableModel.getName());
		}

		sb.append(") VALUES ('");

		iterator = columns.iterator();

		if (iterator.hasNext()) {
			h2TableModel = iterator.next();
			sb.append(h2TableModel.getValue());
		}

		while (iterator.hasNext()) {
			h2TableModel = iterator.next();
			sb.append("', '" + h2TableModel.getValue());
		}

		sb.append("');");
		final String INSERT = sb.toString();

		ps = conn.prepareStatement(INSERT);
		ps.executeUpdate();
	}

	/**
	 * Insert multiple rows into the H2 table
	 *
	 * @param rows Number of rows inserted
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public void insertSet(List<List<H2TableModel>> rows) throws Exception {
		for (int i = 0; i < rows.size(); i++) {
			insert(rows.get(i));
		}
	}

	/**
	 * Select a single row from the H2 table
	 *
	 * @param recordNum Key field
	 * @return List of rows
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public List<Object> select(int recordNum) throws Exception {
		row = new ArrayList<>();
		String field = "";

		if (recordNum == 0) {
			for (int i = 1; i < (count + 1); i++) {
				switch (modelList.get(i - 1).getNumericType()) {
				case Constants.BIGINT:
					row.add(0L);
					break;
				case Constants.BLOB:
					final byte[] ba = { 0 };
					Blob blob = null;
					try {
						blob = conn.createBlob();
						blob.setBytes(1, ba);
					} catch (final Exception e) {
						e.printStackTrace();
					}
					row.add(blob);
					break;
				case Constants.BOOLEAN:
					row.add(Boolean.FALSE);
					break;
				case Constants.CLOB:
					Clob clob = null;
					try {
						clob = conn.createClob();
						clob.setString(1, "");
					} catch (final Exception e) {
						e.printStackTrace();
					}
					row.add(clob);
					break;
				case Constants.DOUBLE:
					row.add(0D);
					break;
				case Constants.INTEGER:
					row.add(0);
					break;
				case Constants.SMALLINT:
					row.add((short) 0);
					break;
				case Constants.TIMESTAMP:
					row.add(new Timestamp(0L));
					break;
				case Constants.VARBINARY:
					final byte[] ba1 = { 0 };
					row.add(ba1);
					break;
				default:
					row.add("");
					break;
				}
			}
			return row;
		}

		String s = tableName.substring(0, tableName.length() - 1);
		if (tableName.equals("SEXES")) {
			s = "SEXES";
		}

		final String SELECT = "SELECT * FROM PUBLIC." + tableName + " WHERE "
				+ s + "_PID = ?";
		ps = conn.prepareStatement(SELECT);
		ps.setInt(1, recordNum);
		rs = ps.executeQuery();

		if (rs.next()) {
			for (int i = 1; i < (count + 1); i++) {
				switch (modelList.get(i - 1).getNumericType()) {
				case Constants.BIGINT:
					row.add(rs.getLong(i));
					break;
				case Constants.BLOB:
					row.add(rs.getBlob(i));
					break;
				case Constants.BOOLEAN:
					row.add(rs.getBoolean(i));
					break;
				case Constants.CHAR:
					row.add(rs.getString(i));
					break;
				case Constants.CLOB:
					row.add(rs.getClob(i));
					break;
				case Constants.DATE:
					row.add(rs.getString(i));
					break;
				case Constants.DOUBLE:
					row.add(rs.getDouble(i));
					break;
				case Constants.INTEGER:
					row.add(rs.getInt(i));
					break;
				case Constants.SMALLINT:
					row.add(rs.getShort(i));
					break;
				case Constants.TIMESTAMP:
					row.add(rs.getTimestamp(i));
					break;
				case Constants.VARBINARY:
					row.add(rs.getBytes(i));
					break;
				case Constants.VARCHAR:
					row.add(rs.getString(i));
					break;
				default:
					if ((field = rs.getString(i)) != null) {
						row.add(field);
					} else {
						row.add("");
					}
					break;
				}
			}
		}
		return row;
	}

	/**
	 * Select all rows from the H2 table
	 *
	 * @return A list of rows
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public List<List<Object>> selectAll() throws Exception {
		rowList = new ArrayList<>();
		String field = "";

		String s = tableName.substring(0, tableName.length() - 1);
		if (tableName.equals("SEXES")) {
			s = "SEXES";
		}

		final String SELECTALL = "SELECT * FROM PUBLIC." + tableName
				+ " ORDER BY " + s + "_PID";
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();

		while (rs.next()) {
			row = new ArrayList<>();

			for (int i = 1; i < (count + 1); i++) {
				if ((field = rs.getString(i)) != null) {
					row.add(field);
				} else {
					row.add("");
				}
			}
			rowList.add(row);
		}
		return rowList;
	}

	/**
	 * Update a row in the H2 table
	 *
	 * @param columns A list of field values
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public void update(List<H2TableModel> columns) throws Exception {
		H2TableModel h2TableModel;
		final StringBuilder sb = new StringBuilder();
		sb.append("UPDATE PUBLIC." + tableName + " SET ");

		final Iterator<H2TableModel> iterator = columns.iterator();

		if (iterator.hasNext()) {
			h2TableModel = iterator.next();

			if ((h2TableModel.getNumericType() == Constants.DATE)
					&& (h2TableModel.getValue() == "")) {
				sb.append(h2TableModel.getName() + " =  null");
			} else {
				sb.append(h2TableModel.getName() + "='"
						+ h2TableModel.getValue() + "'");
			}
		}

		while (iterator.hasNext()) {
			h2TableModel = iterator.next();

			if ((h2TableModel.getNumericType() == Constants.DATE)
					&& (h2TableModel.getValue() == "")) {
				sb.append(", " + h2TableModel.getName() + " =  null");
			} else {
				sb.append(", " + h2TableModel.getName() + "='"
						+ h2TableModel.getValue() + "'");
			}
		}

		String s = tableName.substring(0, tableName.length() - 1);
		if (tableName.equals("SEXES")) {
			s = "SEXES";
		}

		sb.append(" WHERE " + s + "_PID ='" + columns.get(0).getValue() + "';");

		final String UPDATE = sb.toString();
		ps = conn.prepareStatement(UPDATE);
		ps.executeUpdate();
	}
}