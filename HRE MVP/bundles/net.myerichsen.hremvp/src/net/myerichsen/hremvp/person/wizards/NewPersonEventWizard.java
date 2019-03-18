package net.myerichsen.hremvp.person.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.event.providers.EventProvider;
import net.myerichsen.hremvp.person.providers.PersonEventProvider;

/**
 * Wizard to add an event for a person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 18. feb. 2019
 *
 */
public class NewPersonEventWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewPersonEventWizardPage1 page1;
	private final int personPid;

	/**
	 * Constructor
	 *
	 * @param personPid
	 * @param context
	 */
	public NewPersonEventWizard(int personPid, IEclipseContext context) {
		setWindowTitle("Add event");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
		this.personPid = personPid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.
	 * IWizardPage)
	 */
	@Override
	public void addPages() {
		page1 = new NewPersonEventWizardPage1(context);
		addPage(page1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			final List<List<String>> listOfLists = page1.getListOfLists();

			for (final List<String> list : listOfLists) {
				// Create an Event
				final EventProvider ep = new EventProvider();
				ep.setEventNamePid(Integer.parseInt(list.get(0)));
				ep.setFromDatePid(Integer.parseInt(list.get(3)));
				ep.setToDatePid(Integer.parseInt(list.get(5)));
				final int eventPid = ep.insert();

				LOGGER.info("Inserted event pid " + eventPid);

				// Create a person-personEvent to link them together
				final PersonEventProvider pep = new PersonEventProvider();
				pep.setEventPid(eventPid);
				pep.setPersonPid(personPid);
				pep.setPrimaryEvent(true);
				pep.setPrimaryPerson(true);
				pep.setRole(list.get(2));
				final int personEventPid = pep.insert();
				LOGGER.info("Inserted person-event pid " + personEventPid);
				eventBroker.post(
						net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC,
						personPid);
				return true;
			}
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}