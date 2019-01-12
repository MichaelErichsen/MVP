package net.myerichsen.hremvp.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * Default values initializer
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 12. jan. 2019
 *
 */
public class DefaultValuesInitializer extends AbstractPreferenceInitializer {

	/**
	 * Constructor
	 *
	 */
	public DefaultValuesInitializer() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");

		// MVP
		store.setDefault("CSMODE", "STANDALONE");
		store.setDefault("LOGLEVEL", "INFO");
		store.setDefault("LOGFILEPATH", ".");
		store.setDefault("GUILANGUAGE", "ENGLISH");
		store.setDefault("HELPSYSTEMPORT", "8081");
		store.setDefault("HREFONT", "1|Segoe UI|12.0|0|WINDOWS|1|-16|0|0|0|400|0|0|0|0|3|2|1|34|Segoe UI");
		store.setDefault("TLS", "true");
		store.setDefault("UPDATESITE", "http://www.myerichsen.net/HRERepository");
		store.setDefault("WEBSITELIST", "https://www.google.dk/search?oq=");

		// H2
		store.setDefault("H2TRACELEVEL", "1");
		store.setDefault("DBPATH", "C:\\Program Files\\HRE\\eclipse");
		store.setDefault("DBNAME", "MVP");
		store.setDefault("USERID", "sa");
		store.setDefault("PASSWORD", "");
		store.setDefault("H2VERSION", "1.3.168");

		// Jetty
		store.setDefault("SERVERADDRESS", "127.0.0.1");
		store.setDefault("SERVERROOT", "mvp/v100");
		store.setDefault("SERVERPORT", 8000);
		store.setDefault("JETTYLOGLEVEL", "INFO");
		store.setDefault("SERVERLIST", "127.0.0.1:8000");

		store.setDefault("projectcount", "1");
		store.setDefault("project.0.name", "MVP");
		store.setDefault("project.0.lastupdated", "2019-01-06 12:41:36");
		store.setDefault("project.0.summary", "This is the Default project");
		store.setDefault("project.0.localserver", "LOCAL");
		store.setDefault("project.0.path", "C:/Program Files/HRE/eclipse/MVP");
	}
}
