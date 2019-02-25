package net.myerichsen.hremvp.project.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.PersonNameStyleProvider;

/**
 * Wizard to add a person name style
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 25. feb. 2019
 *
 */
public class NewPersonNameStyleWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewPersonNameStyleWizardPage1 page1;
	private NewPersonNameStyleWizardPage2 page2;
	private PersonNameStyleProvider provider;

	/**
	 * Constructor
	 *
	 * @param personNameStylePid
	 * @param context
	 */
	public NewPersonNameStyleWizard(int personNameStylePid,
			IEclipseContext context) {
		setWindowTitle("Add a person name style");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	@Override
	public void addPages() {
		page1 = new NewPersonNameStyleWizardPage1(context);
		addPage(page1);
		page2 = new NewPersonNameStyleWizardPage2(context);
		addPage(page2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#canFinish()
	 */
	@Override
	public boolean performFinish() {
		DictionaryProvider dp;
//		final String abbreviation = page1.getTextAbbreviation().getText();

//		if (abbreviation.equals("") == false) {
		try {
			provider = new PersonNameStyleProvider();
//				provider.setAbbreviation(abbreviation);

			final int personNameStylePid = provider.insert();
			LOGGER.info("Inserted person name style " + personNameStylePid);
			eventBroker.post("MESSAGE",
					"Inserted person name style " + personNameStylePid);

			final int labelPid = provider.getLabelPid();

//			final List<List<String>> input = (List<List<String>>) page1
//					.getTableViewer().getInput();
//
//			for (int i = 0; i < input.size(); i++) {
//				dp = new DictionaryProvider();
//				dp.setIsoCode(input.get(i).get(2));
//				dp.setLabel(input.get(i).get(3));
//				dp.setLabelPid(labelPid);
//				dp.setLabelType("NAMESTYLE");
//				final int dictionaryPid = dp.insert();
//				LOGGER.info("Inserted dictionary element " + dictionaryPid
//						+ ", " + input.get(i).get(2) + ", "
//						+ input.get(i).get(3));
//			}

			eventBroker.post(
					net.myerichsen.hremvp.Constants.PERSON_NAME_STYLE_PID_UPDATE_TOPIC,
					personNameStylePid);
			return true;
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

//		}

		return false;
	}

}
