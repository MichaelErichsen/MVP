package net.myerichsen.hremvp.preferences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.LanguageProvider;

/**
 * Preference page for client
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 23. feb. 2019
 *
 */
public class ClientPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private ComboFieldEditor comboFieldEditorLogLevel;
	private ComboFieldEditor comboFieldEditorCsMode;
	private FontFieldEditor fontFieldEditor;
	private StringFieldEditor updateSiteFieldEditor;
	private IntegerFieldEditor helpportIntegerFieldEditor;
	private IntegerFieldEditor serverportIntegerFieldEditor;
	private ComboFieldEditor comboGuiLanguage;

	/**
	 * Constructor
	 *
	 */
	public ClientPreferencePage() {
		super(GRID);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors
	 * ()
	 */
	@Override
	protected void createFieldEditors() {
		final Composite composite = getFieldEditorParent();

		comboFieldEditorCsMode = new ComboFieldEditor("CSMODE",
				"Client/Server Mode",
				new String[][] { { "STANDALONE", "STANDALONE" },
						{ "CLIENT", "CLIENT" }, { "SERVER", "SERVER" } },
				composite);
		addField(comboFieldEditorCsMode);

		addField(new StringFieldEditor("LOGFILEPATH", "MVP Log File Path", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE, composite));
		comboFieldEditorLogLevel = new ComboFieldEditor("LOGLEVEL",
				"MVP Log Level",
				new String[][] { { "OFF", "OFF" }, { "SEVERE", "SEVERE" },
						{ "WARNING", "WARNING" }, { "INFO", "INFO" },
						{ "CONFIG", "CONFIG" }, { "FINE", "FINE" },
						{ "FINER", "FINER" }, { "FINEST", "FINEST" },
						{ "ALL", "ALL" } },
				composite);
		addField(comboFieldEditorLogLevel);

		fontFieldEditor = new FontFieldEditor("HREFONT", "Font Selection", null,
				getFieldEditorParent());
		addField(fontFieldEditor);

		updateSiteFieldEditor = new StringFieldEditor("UPDATESITE",
				"HRE Update Site", -1, StringFieldEditor.VALIDATE_ON_KEY_STROKE,
				getFieldEditorParent());
		addField(updateSiteFieldEditor);

		helpportIntegerFieldEditor = new IntegerFieldEditor("HELPSYSTEMPORT",
				"Port number for Help System", getFieldEditorParent());
		addField(helpportIntegerFieldEditor);

		serverportIntegerFieldEditor = new IntegerFieldEditor("SERVERPORT",
				"Port Number for local HRE Server", getFieldEditorParent());
		addField(serverportIntegerFieldEditor);

		final LanguageProvider languageProvider = new LanguageProvider();
		try {
			final List<List<String>> languageList = languageProvider.get();

			final int llsSize = languageList.size();
			final String[][] doubleArray = new String[llsSize][2];

			for (int i = 0; i < llsSize; i++) {
				doubleArray[i][0] = languageList.get(i).get(2);
				doubleArray[i][1] = languageList.get(i).get(1);
			}

			comboGuiLanguage = new ComboFieldEditor("GUILANGUAGE",
					"GUI Language", doubleArray, getFieldEditorParent());
			addField(comboGuiLanguage);
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

		// TODO Populate from database table
		addField(new ComboFieldEditor("DEFAULTPERSONNAMESTYLE",
				"Default Person Name Style",
				new String[][] { { "name_1", "1" }, { "name_2", "value_2" } },
				getFieldEditorParent()));

		// TODO Populate from database table
		addField(new ComboFieldEditor(
				"id", "Default Location Name Style", new String[][] {
						{ "name_1", "value_1" }, { "name_2", "value_2" } },
				getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#propertyChange(
	 * org. eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		final IPreferenceStore store = new ScopedPreferenceStore(
				InstanceScope.INSTANCE, "net.myerichsen.hremvp");

		super.propertyChange(event);

		if (event.getSource() == comboFieldEditorCsMode) {
			final String newValue = event.getNewValue().toString();

			if ((newValue.equals("STANDALONE"))
					|| (newValue.equals("SERVER"))) {
				final String dbName = store.getString("DBNAME");
				final String userId = store.getString("USERID");
				final String passWord = store.getString("PASSWORD");

				Connection conn = null;
				try {
					conn = DriverManager.getConnection("jdbc:h2:" + dbName,
							userId, passWord);
				} catch (final Exception e) {
					LOGGER.severe(e.getClass() + " " + e.getMessage());
				}
				LOGGER.info("Changed property "
						+ comboFieldEditorCsMode.getPreferenceName() + " from "
						+ event.getOldValue() + " to " + event.getNewValue());
				LOGGER.info("Database connection " + conn);
			}
		} else if (event.getSource() == comboFieldEditorLogLevel) {
			LOGGER.setLevel(Level.parse(event.getNewValue().toString()));

			LOGGER.info("Changed property "
					+ comboFieldEditorLogLevel.getPreferenceName() + " from "
					+ event.getOldValue() + " to " + event.getNewValue());
		}
	}

}
