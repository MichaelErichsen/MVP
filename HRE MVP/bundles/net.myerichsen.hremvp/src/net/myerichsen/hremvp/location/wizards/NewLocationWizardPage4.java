package net.myerichsen.hremvp.location.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Location summary wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 14. mar. 2019
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
		setDescription("Confirm the new location");
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

		final NewLocationWizard wizard = (NewLocationWizard) getWizard();
		String x = wizard.getPage3().getTextXCoordinate().getText();
		String y = wizard.getPage3().getTextYCoordinate().getText();

		if ((x.isEmpty() || y.isEmpty())) {
			final Label label = new Label(container, SWT.NONE);
			label.setText("No map available");
		} else {
			final Browser browser = new Browser(container, SWT.NONE);
			browser.setLayoutData(
					new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			String urlString = "http://www.google.com/maps/@?api=1&map_action=map&center="
					+ wizard.getPage3().getTextXCoordinate().getText() + ", "
					+ wizard.getPage3().getTextYCoordinate().getText()
					+ "&basemap=terrain";
			browser.setUrl(urlString);
		}
	}
}
