package net.myerichsen.hremvp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.h2.jdbcx.JdbcConnectionPool;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * Singleton class that instantiates a JDBC Connection Pool and returns a
 * connection to it
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 1. feb. 2019
 */
public class HreH2ConnectionPool {
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static JdbcConnectionPool connectionPool = null;
	private static int h2TraceLevel = 1;
	private static String dbPath = null;
	private final static String GETH2VERSION = "SELECT H2VERSION() FROM DUAL";

	/**
	 * Exists only to defeat instantiation
	 */
	protected HreH2ConnectionPool() {
	}

	/**
	 * Dispose connection pool and recreate to create a new data base.
	 *
	 * @param dbName Name of the database
	 * @throws BackingStoreException Error in preferences file access
	 */
	public static void createNew(String dbName) throws BackingStoreException {
		try {
			connectionPool.dispose();
		} catch (Exception e) {
			LOGGER.info("No connection pool to dispose");
		}
		dbPath = store.getString("DBPATH");
		h2TraceLevel = store.getInt("H2TRACELEVEL");
		final String jdbcUrl = "jdbc:h2:" + dbPath + "/" + dbName + ";TRACE_LEVEL_FILE=" + h2TraceLevel
				+ ";TRACE_LEVEL_SYSTEM_OUT=" + h2TraceLevel;
		LOGGER.info("JDBC URL: " + jdbcUrl);
		connectionPool = JdbcConnectionPool.create(jdbcUrl, "sa", "");
		LOGGER.info("Connection pool has been created");
		connectionPool.setMaxConnections(500);
		store.setValue("DBNAME", dbName);
		LOGGER.info("Preferences dbname set to " + dbName);
	}

	/**
	 * Dispose the connection pool.
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public static void dispose() throws SQLException {
		final Connection conn = HreH2ConnectionPool.getConnection();
		final PreparedStatement ps = conn.prepareStatement("SHUTDOWN");
		ps.executeUpdate();

		if (connectionPool != null) {
			connectionPool.dispose();
		}
		connectionPool = null;
	}

	/**
	 * @return A JDBC connection
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public static Connection getConnection() throws SQLException {
		final String dbName = store.getString("DBNAME");
		h2TraceLevel = store.getInt("H2TRACELEVEL");
		dbPath = store.getString("DBPATH");

		if (connectionPool == null) {
			h2TraceLevel = store.getInt("H2TRACELEVEL");
			final String jdbcUrl = "jdbc:h2:" + dbPath + "/" + dbName + ";IFEXISTS=TRUE;TRACE_LEVEL_FILE="
					+ h2TraceLevel + ";TRACE_LEVEL_SYSTEM_OUT=" + h2TraceLevel;
			LOGGER.info("JDBC URL: " + jdbcUrl);
			connectionPool = JdbcConnectionPool.create(jdbcUrl, store.getString("USERID"), store.getString("PASSWORD"));
			connectionPool.setMaxConnections(500);
			String h2Version = store.getString("H2VERSION");

			try {
				final Connection conn = connectionPool.getConnection();
				final PreparedStatement ps = conn.prepareStatement(GETH2VERSION);
				final ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					h2Version = rs.getString(1);
					store.setValue("H2VERSION", h2Version);

					LOGGER.info("H2 Version is " + h2Version);
				}
			} catch (final SQLException e) {
				LOGGER.severe(e.getMessage());
				store.setValue("H2VERSION", h2Version);

				LOGGER.info("H2 Version defaults to " + h2Version);
			}
		}

		// FIXME Open other
		LOGGER.fine("Reusing connection pool, Max: " + connectionPool.getMaxConnections() + ", Active: "
				+ connectionPool.getActiveConnections());
		// FIXME 2019-02-01 17:53:38 jdbc[3]: java.lang.Exception: Open Stack Trace
		return connectionPool.getConnection();
	}

	/**
	 * Get a JDBCconnection
	 *
	 * @param dbName Name of database
	 * @return A JDBC Connection
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public static Connection getConnection(String dbName) throws SQLException {
		h2TraceLevel = store.getInt("H2TRACELEVEL");
		dbPath = store.getString("DBPATH");
		final String jdbcUrl = "jdbc:h2:" + dbPath + "/" + dbName + ";IFEXISTS=TRUE;TRACE_LEVEL_FILE=" + h2TraceLevel
				+ ";TRACE_LEVEL_SYSTEM_OUT=" + h2TraceLevel;
		LOGGER.info("JDBC URL: " + jdbcUrl);
		connectionPool = JdbcConnectionPool.create(jdbcUrl, "sa", "");
		connectionPool.setMaxConnections(500);
		LOGGER.info("Connection pool has been created");

		return connectionPool.getConnection();
	}
}