package net.myerichsen.hremvp.databaseadmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IContentProvider;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.HreH2ConnectionPool;

/**
 * Provides H2 data to the database navigator
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 30. okt. 2018
 *
 */
public class H2DatabaseProvider implements IContentProvider {
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String SELECT13 = "SELECT TABLE_NAME, 0 FROM INFORMATION_SCHEMA.TABLES "
			+ "WHERE TABLE_TYPE = 'TABLE' ORDER BY TABLE_NAME";
	private static final String SELECT = "SELECT TABLE_NAME, ROW_COUNT_ESTIMATE FROM INFORMATION_SCHEMA.TABLES "
			+ "WHERE TABLE_TYPE = 'TABLE' ORDER BY TABLE_NAME";

	private Connection conn = null;
	private final List<H2DatabaseModel> modelList = new ArrayList<>();
	private H2DatabaseModel model;

	/**
	 * Constructor
	 *
	 * @throws Exception When failing
	 *
	 */
	public H2DatabaseProvider() throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		final String dbName = store.getString("DBNAME");

		LOGGER.log(Level.INFO, "Database name: " + dbName);

		conn = HreH2ConnectionPool.getConnection(dbName);
		PreparedStatement ps;
		final String h2Version = store.getString("H2VERSION");
		LOGGER.log(Level.INFO, "Retrieved H2 version from preferences: "
				+ h2Version.substring(0, 3));

		if (h2Version.substring(0, 3).equals("1.3")) {
			ps = conn.prepareStatement(SELECT13);
		} else {
			ps = conn.prepareStatement(SELECT);
		}

		final ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			model = new H2DatabaseModel(rs.getString(1), rs.getLong(2));
			modelList.add(model);
		}
	}

	/**
	 * @return the modelList
	 */
	public List<H2DatabaseModel> getModelList() {
		return modelList;
	}
}