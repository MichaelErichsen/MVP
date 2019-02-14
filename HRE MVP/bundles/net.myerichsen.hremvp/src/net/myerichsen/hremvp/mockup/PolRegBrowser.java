package net.myerichsen.hremvp.mockup;

import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class PolRegBrowser extends AbstractHREGuiPart {
	String searchArgument;

	public PolRegBrowser() {
		store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				"org.historyresearchenvironment.usergui");
	}

	@Override
	protected void callBusinessLayer(int i) {
	}

	@PostConstruct
	public void createControls(Composite parent) {
		try {
			parent.setLayout(new GridLayout(1, false));
			final Browser browser = new Browser(parent, 0);
			browser.setLayoutData(new GridData(4, 4, true, true, 2, 1));
			searchArgument = store.getString("POLREGID");
			final String polRegUrl = "http://www.politietsregisterblade.dk/component/sfup/?controller=politregisterblade&task=viewRegisterblad&id="
					+ URLEncoder.encode(searchArgument, "UTF-8");
			browser.setUrl(polRegUrl);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

	@Override
	protected void updateGui() {
	}
}
