package net.myerichsen.hremvp.help.parts;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * Embedded browser for HRE Help System
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 7. jan. 2019
 *
 */
public class HelpSystemBrowser {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	public HelpSystemBrowser() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		final Browser browser = new Browser(parent, SWT.NONE);
		browser.setToolTipText("HRE Help");
		browser.setUrl("http://127.0.0.1:" + store.getInt("HELPSYSTEMPORT") + "/help/index.jsp");
		LOGGER.info("Browser pointing at " + browser.getUrl());
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

}