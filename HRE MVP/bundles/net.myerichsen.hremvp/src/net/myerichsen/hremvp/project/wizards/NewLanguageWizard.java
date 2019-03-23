package net.myerichsen.hremvp.project.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.project.providers.LanguageProvider;

/**
 * Wizard to add a language for HRE
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 20. feb. 2019
 *
 */
public class NewLanguageWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewLanguageWizardPage1 page1;
	private LanguageProvider provider;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewLanguageWizard(IEclipseContext context) {
		setWindowTitle("Add a language");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.
	 * IWizardPage)
	 */
	@Override
	public void addPages() {
		page1 = new NewLanguageWizardPage1(context);
		addPage(page1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		final String isoCode = page1.getTextIsoCode().getText();
		final String label = page1.getTextLabel().getText();

		if ((isoCode.equals("") == false) && (label.equals("") == false)) {
			provider = new LanguageProvider();
			provider.setIsocode(isoCode);
			provider.setLabel(label);
			try {
				final int languagePid = provider.insert();
				LOGGER.info(
						"Inserted language pid " + languagePid + ", " + label);
				eventBroker.post("MESSAGE",
						"Inserted language pid " + languagePid + ", " + label);
				eventBroker.post(
						net.myerichsen.hremvp.Constants.LANGUAGE_PID_UPDATE_TOPIC,
						languagePid);
				return true;
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}

		}
		return false;
	}

}
