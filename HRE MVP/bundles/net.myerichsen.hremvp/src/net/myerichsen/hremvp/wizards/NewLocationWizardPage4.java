package net.myerichsen.hremvp.wizards;

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
 * @version 30. okt. 2018
 *
 */
public class NewLocationWizardPage4 extends WizardPage {
	/**
	 * Constructor
	 *
	 */
	public NewLocationWizardPage4() {
		super("wizardPage");
		setTitle("Confirmation");
		setDescription("Insert the new location");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.
	 * Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		final NewLocationWizard wizard = (NewLocationWizard) getWizard();
		final Browser browser = new Browser(container, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		browser.setUrl("http://www.google.com/maps/@?api=1&map_action=map&center="
				+ wizard.getPage1().getTextXCoordinate().getText() + ", "
				+ wizard.getPage1().getTextYCoordinate().getText() + "&basemap=terrain");

	}
}
