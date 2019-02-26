package net.myerichsen.hremvp.project.wizards;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.LanguageProvider;
import net.myerichsen.hremvp.project.providers.PersonNameStyleProvider;

/**
 * Add a person name style wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 26. feb. 2019
 *
 */
public class NewPersonNameStyleWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private Combo comboIsoCode;
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

		final Label lblIsoCode = new Label(container, SWT.NONE);
		lblIsoCode.setText("ISO Code");

		comboIsoCode = new Combo(container, SWT.BORDER);
		comboIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final LanguageProvider languageProvider = new LanguageProvider();
		try {
			final List<List<String>> languageList = languageProvider.get();

			final int llsSize = languageList.size();
			final String[] singleArray = new String[llsSize];
			String g = store.getString("GUILANGUAGE");
			int index = 0;

			for (int i = 0; i < llsSize; i++) {
				singleArray[i] = languageList.get(i).get(2);
				if (g.equals(languageList.get(i).get(1))) {
					index = i;
				}
			}

			comboIsoCode.setItems(singleArray);
			comboIsoCode.select(index);
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

		final Label lblStyleName = new Label(container, SWT.NONE);
		lblStyleName.setText("Style name");

		textStyleName = new Text(container, SWT.BORDER);
		textStyleName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNumberOfName = new Label(container, SWT.NONE);
		lblNumberOfName.setText("Number of name parts");

		// TODO Numeric
		// TODO Check for both fields to activate next page
		textNamePartCount = new Text(container, SWT.BORDER);
		textNamePartCount.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	/**
	 * @return the comboIsoCode
	 */
	public Combo getTextIsoCode() {
		return comboIsoCode;
	}

	/**
	 * @return the textStyleName
	 */
	public Text getTextStyleName() {
		return textStyleName;
	}
}
