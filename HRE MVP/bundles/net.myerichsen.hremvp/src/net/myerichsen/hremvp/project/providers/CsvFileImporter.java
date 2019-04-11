package net.myerichsen.hremvp.project.providers;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.myerichsen.hremvp.databaseadmin.H2TableProvider;

/**
 * Lists csv files to be imported into HRE tables when cerating a new project
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. apr. 2019
 *
 */
public class CsvFileImporter {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String[] csvFileNames = { "languages.csv",
			"sextypes.csv", "eventtypes.csv", "eventroles.csv",
			"locationnamestyles.csv", "locationnamemaps.csv",
			"personnamestyles.csv", "personnamemaps.csv", "dictionary.csv" };

	private static final String[] tableNames = { "LANGUAGES", "SEX_TYPES",
			"EVENT_TYPES", "EVENT_ROLES", "LOCATION_NAME_STYLES",
			"LOCATION_NAME_MAPS", "PERSON_NAME_STYLES", "PERSON_NAME_MAPS",
			"DICTIONARY" };

	/**
	 * Constructor
	 *
	 */
	private CsvFileImporter() {
		super();
		// TODO Auto-generated constructor stub
	}

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
				LOGGER.log(Level.INFO, "{0} rows has been imported from {1}",
						new Object[] {rowCount, csvFileNames[i]});

			} catch (final Exception e1) {
				LOGGER.log(Level.SEVERE, e1.toString(), e1);
			}
		}
	}
}
