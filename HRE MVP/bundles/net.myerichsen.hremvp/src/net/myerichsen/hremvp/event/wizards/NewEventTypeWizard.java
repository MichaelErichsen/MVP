package net.myerichsen.hremvp.event.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.event.providers.EventTypeProvider;
import net.myerichsen.hremvp.project.providers.DictionaryProvider;

/**
 * Wizard to add a event type
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 23. feb. 2019
 *
 */
public class NewEventTypeWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewEventTypeWizardPage1 page1;
	private EventTypeProvider provider;

	/**
	 * Constructor
	 * 
	 * @param eventTypePid
	 *
	 * @param context
	 */
	public NewEventTypeWizard(int eventTypePid, IEclipseContext context) {
		setWindowTitle("Add an event type");
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
		page1 = new NewEventTypeWizardPage1(context);
		addPage(page1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean performFinish() {
		DictionaryProvider dp;
		String abbreviation = page1.getTextAbbreviation().getText();

		if (abbreviation.equals("") == false) {
			try {
				provider = new EventTypeProvider();
				provider.setAbbreviation(abbreviation);

				int eventTypePid = provider.insert();
				LOGGER.info("Inserted event type " + eventTypePid);
				eventBroker.post("MESSAGE",
						"Inserted event type " + eventTypePid);

				int labelPid = provider.getLabelPid();

				List<List<String>> input = (List<List<String>>) page1
						.getTableViewer().getInput();

				for (int i = 0; i < input.size(); i++) {
					dp = new DictionaryProvider();
					dp.setIsoCode(input.get(i).get(0));
					dp.setLabel(input.get(i).get(1));
					dp.setLabelPid(labelPid);
					int dictionaryPid = dp.insert();
					LOGGER.info("Inserted dictionary element " + dictionaryPid
							+ ", " + input.get(i).get(0) + ", "
							+ input.get(i).get(1));
				}

				eventBroker.post(
						net.myerichsen.hremvp.Constants.EVENT_TYPE_PID_UPDATE_TOPIC,
						eventTypePid);
				return true;
			} catch (Exception e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}

		}

		return false;
	}

}
