package net.myerichsen.hremvp.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Embedded browser for HRE Help System
 * 
 * @version 2018-06-22
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 *
 */
public class HelpSystemBrowser {

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
		browser.setUrl("http://127.0.0.1:8081/help/index.jsp");
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

}
