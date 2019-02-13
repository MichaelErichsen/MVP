package net.myerichsen.hremvp.help.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Embedded browser for HRE Web Site.
 *
 * @version 2018-06-22
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 *
 */
public class HelpHreWebsite {

	public HelpHreWebsite() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		final Browser browser = new Browser(parent, SWT.NONE);
		browser.setToolTipText("HRE Web Site");
		browser.setUrl("https://historyresearchenvironment.org/");
		browser.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

}
