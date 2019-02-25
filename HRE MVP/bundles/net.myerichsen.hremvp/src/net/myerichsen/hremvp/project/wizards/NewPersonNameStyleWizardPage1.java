package net.myerichsen.hremvp.project.wizards;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.project.providers.PersonNameStyleProvider;

/**
 * Add a person name style wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 25. feb. 2019
 *
 */
public class NewPersonNameStyleWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Text textLabelPid;
	private Combo textIsoCode;
	private Text textStyleName;
	private PersonNameStyleProvider provider;
	private Text textNamePartCount;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonNameStyleWizardPage1(IEclipseContext context) {
		super("Person name style wizard Page 1");
		setTitle("Person name style");
		setDescription("Add static data of a new person name style");
		try {
			provider = new PersonNameStyleProvider();
		} catch (final SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		final Label lblNewPersonName = new Label(container, SWT.NONE);
		lblNewPersonName.setText("New person name style pid");

		textLabelPid = new Text(container, SWT.BORDER);
		textLabelPid.setEditable(false);
		textLabelPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblIsoCode = new Label(container, SWT.NONE);
		lblIsoCode.setText("ISO Code");

		textIsoCode = new Combo(container, SWT.BORDER);
		textIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblStyleName = new Label(container, SWT.NONE);
		lblStyleName.setText("Style name");

		textStyleName = new Text(container, SWT.BORDER);
		textStyleName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNumberOfName = new Label(container, SWT.NONE);
		lblNumberOfName.setText("Number of name parts");

		textNamePartCount = new Text(container, SWT.BORDER);
		textNamePartCount.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	/**
	 * @return the textIsoCode
	 */
	public Combo getTextIsoCode() {
		return textIsoCode;
	}

	/**
	 * @return the textLabelPid
	 */
	public Text getTextLabelPid() {
		return textLabelPid;
	}

	/**
	 * @return the textStyleName
	 */
	public Text getTextStyleName() {
		return textStyleName;
	}
}
