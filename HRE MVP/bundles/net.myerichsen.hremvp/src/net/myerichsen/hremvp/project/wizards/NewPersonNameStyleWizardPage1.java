package net.myerichsen.hremvp.project.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.listeners.NumericVerifyListener;
import net.myerichsen.hremvp.project.providers.LanguageProvider;

/**
 * Add a person name style wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 22. mar. 2019
 *
 */
public class NewPersonNameStyleWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private Combo comboIsoCode;
	private Text textStyleName;
	private Text textNamePartCount;
	private List<List<String>> languageList;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonNameStyleWizardPage1(IEclipseContext context) {
		super("Person name style wizard page 1");
		setTitle("Person name style");
		setDescription("Add static data for a new person name style");
	}

	/**
	 *
	 */
	protected void checkCompletedPage() {
		final String styleName = textStyleName.getText();
		final String namePartCount = textNamePartCount.getText();

		if ((styleName.length() > 0) && (namePartCount.length() > 0)) {
			final NewPersonNameStyleWizard wizard = (NewPersonNameStyleWizard) getWizard();
			wizard.setStyleName(styleName);
			wizard.setNamePartCount(namePartCount);
			final int selectionIndex = comboIsoCode.getSelectionIndex();
			wizard.setIsoCode(languageList.get(selectionIndex).get(1));
			setPageComplete(true);
			wizard.addBackPages();
			wizard.getContainer().updateButtons();
		} else {
			setPageComplete(false);
		}
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		final Label lblIsoCode = new Label(container, SWT.NONE);
		lblIsoCode.setText("ISO Code");

		// FIXME Change combo to JFace
		comboIsoCode = new Combo(container, SWT.BORDER);
		comboIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final LanguageProvider languageProvider = new LanguageProvider();
		try {
			languageList = languageProvider.get();

			final int llsSize = languageList.size();
			final String[] singleArray = new String[llsSize];
			final String g = store.getString("GUILANGUAGE");
			int index = 0;

			for (int i = 0; i < llsSize; i++) {
				singleArray[i] = languageList.get(i).get(2);
				if (g.equals(languageList.get(i).get(1))) {
					index = i;
				}
			}

			comboIsoCode.setItems(singleArray);
			comboIsoCode.select(index);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

		final Label lblStyleName = new Label(container, SWT.NONE);
		lblStyleName.setText("Style name");

		textStyleName = new Text(container, SWT.BORDER);
		textStyleName.addModifyListener(new ModifyListener() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.
			 * events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				checkCompletedPage();
			}
		});
		textStyleName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblNumberOfName = new Label(container, SWT.NONE);
		lblNumberOfName.setText("Number of name parts");

		textNamePartCount = new Text(container, SWT.BORDER);
		textNamePartCount.addModifyListener(new ModifyListener() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.
			 * events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				checkCompletedPage();
			}
		});
		textNamePartCount.addVerifyListener(new NumericVerifyListener());
		textNamePartCount.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		setPageComplete(false);
	}
}
