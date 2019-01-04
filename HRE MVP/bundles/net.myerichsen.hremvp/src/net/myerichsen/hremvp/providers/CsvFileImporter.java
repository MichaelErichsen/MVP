package net.myerichsen.hremvp.providers;

import java.sql.SQLException;
import java.util.logging.Logger;

import net.myerichsen.hremvp.databaseadmin.H2TableProvider;

/**
 * @version 2018-06-30
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 *
 */
public class CsvFileImporter {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String[] csvFileNames = { "DisplayTypeDefns.csv", "FieldDataTypeDefns.csv",
			"FieldFormatDefns.csv", "FieldTypeDefns.csv", "HDateCalendarDefns.csv", "HInterval Modifier Defns.csv",
			"LivingTypeDefns.csv", "NameOutTemplateDefns.csv", "ScriptActionTypeDefinitions.csv", "ScriptDefns.csv",
			"ScriptGroupDefns.csv", "Sentence Style Defns.csv", "SexDefns.csv", "SubstnParamNames.csv",
			"SubstnParamValues.csv", "Units Defns.csv" };
	private static final String[] tableNames = { "DISPLAY_TYPE_DEFNS", "FIELD_DATA_TYPE_DEFNS", "FIELD_FORMAT_DEFNS",
			"FIELD_TYPE_DEFNS", "HDATE_CALENDAR_DEFNS", "HINTERVAL_MODIFIER_DEFNS", "LIVING_TYPE_DEFNS",
			"NAME_OUT_TEMPLATE_DEFNS", "SCRIPT_ACTION_TYPE_DEFNS", "SCRIPT_DEFNS", "SCRIPT_GROUP_DEFNS",
			"SENTENCE_STYLE_DEFNS", "SEX_DEFNS", "SUBSTN_PARAM_NAMES", "SUBSTN_PARAM_VALUES", "UNITS_DEFNS" };

	/**
	 * Import all csv files.
	 */
	public static void importCsv() {
		for (int i = 0; i < tableNames.length; i++) {

			int rowCount = 0;
			try {
				final H2TableProvider provider = new H2TableProvider(tableNames[i]);
				rowCount = provider.importCsv("./" + csvFileNames[i]);
				LOGGER.info(rowCount + " rows has been imported from " + csvFileNames[i]);

			} catch (final SQLException e1) {
				e1.printStackTrace();
				LOGGER.severe(e1.getMessage());
			}
		}
	}
}
