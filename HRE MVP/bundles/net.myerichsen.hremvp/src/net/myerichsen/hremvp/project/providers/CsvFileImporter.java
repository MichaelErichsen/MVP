package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.logging.Logger;

import net.myerichsen.hremvp.databaseadmin.H2TableProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 24. feb. 2019
 *
 */
public class CsvFileImporter {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String[] csvFileNames = {
//			"hdates.csv",
			"languages.csv",
//			"sextypes.csv", "eventtypes.csv", "eventnames.csv",
//			"locationnamestyles.csv", "LocationNameMaps.csv", "namestyles.csv",
//			"PersonNameMaps.csv" 
	};

	private static final String[] tableNames = {
//			"HDATES", 
			"LANGUAGES",
//			"SEX_TYPES", "EVENT_TYPES", "EVENT_NAMES", "LOCATION_NAME_STYLES",
//			"LOCATION_NAME_MAPS", "NAME_STYLES", "NAME_MAPS"
	};

	/**
	 * Import all csv files.
	 */
	public static void importCsv() {
		for (int i = 0; i < tableNames.length; i++) {

			int rowCount = 0;
			try {
				final H2TableProvider provider = new H2TableProvider(
						tableNames[i]);
				rowCount = provider.importCsv("./" + csvFileNames[i]);
				LOGGER.info(rowCount + " rows has been imported from "
						+ csvFileNames[i]);

			} catch (final SQLException e1) {
				e1.printStackTrace();
				LOGGER.severe(e1.getMessage());
			}
		}
	}
}
