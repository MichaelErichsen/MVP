package net.myerichsen.hremvp.location.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Location summary wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 17. mar. 2019
 *
 */
public class NewLocationWizardPage4 extends WizardPage {
	NewLocationWizard wizard;

	/**
	 * Constructor
	 *
	 */
	public NewLocationWizardPage4() {
		super("wizardPage");
		setTitle("Confirmation");
		setDescription("Confirm the new location");
		wizard = (NewLocationWizard) getWizard();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets. Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		final Browser browser = new Browser(container, SWT.NONE);
		browser.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		wizard = (NewLocationWizard) getWizard();
		String urlString = "http://www.google.com/maps/@?api=1&map_action=map&center="
				+ wizard.getxCoordinate() + ", " + wizard.getyCoordinate()
				+ "&basemap=terrain";
		browser.setUrl(urlString);
	}
}
