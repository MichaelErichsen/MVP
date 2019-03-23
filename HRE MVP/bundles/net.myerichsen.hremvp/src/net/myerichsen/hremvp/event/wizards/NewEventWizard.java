package net.myerichsen.hremvp.event.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.event.providers.EventProvider;
import net.myerichsen.hremvp.location.providers.LocationEventProvider;
import net.myerichsen.hremvp.person.providers.PersonEventProvider;

/**
 * Wizard to add a new Event
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. mar. 2019
 *
 */
public class NewEventWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private NewEventWizardPage1 page1;
	private NewEventWizardPage2 page2;
	private NewEventWizardPage3 page3;

	// Page 1
	private int eventTypePid = 0;
	private String eventName = "";
	private int languagePid = 0;
	private int fromDatePid = 0;
	private int toDatePid = 0;

	// Page 2
	private int locationPid = 0;
	private Boolean primaryLocation = true;
	private Boolean primaryLocationEvent = true;

	// Page 3
	private int personPid = 0;
	private int eventRolePid = 0;
	private Boolean primaryPerson = true;
	private Boolean primaryPersonEvent = true;

	private final IEventBroker eventBroker;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse Context
	 *
	 */
	public NewEventWizard(IEclipseContext context) {
		setWindowTitle("New Event");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page1 = new NewEventWizardPage1(context);
		addPage(page1);
		page2 = new NewEventWizardPage2(context);
		addPage(page2);
		page3 = new NewEventWizardPage3(context);
		addPage(page3);
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @return the eventRolePid
	 */
	public int getEventRolePid() {
		return eventRolePid;
	}

	/**
	 * @return the eventTypePid
	 */
	public int getEventTypePid() {
		return eventTypePid;
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @return the primaryLocation
	 */
	public Boolean getIsPrimaryLocation() {
		return primaryLocation;
	}

	/**
	 * @return the primaryLocationEvent
	 */
	public Boolean getIsPrimaryLocationEvent() {
		return primaryLocationEvent;
	}

	/**
	 * @return the primaryPerson
	 */
	public Boolean getIsPrimaryPerson() {
		return primaryPerson;
	}

	/**
	 * @return the primaryPersonEvent
	 */
	public Boolean getIsPrimaryPersonEvent() {
		return primaryPersonEvent;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return languagePid;
	}

	/**
	 * @return the locationPid
	 */
	public int getLocationPid() {
		return locationPid;
	}

	/**
	 * @return the page1
	 */
	public NewEventWizardPage1 getPage1() {
		return page1;
	}

	/**
	 * @return the page2
	 */
	public NewEventWizardPage2 getPage2() {
		return page2;
	}

	/**
	 * @return the page3
	 */
	public NewEventWizardPage3 getPage3() {
		return page3;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			EventProvider provider = new EventProvider();
			provider.setFromDatePid(fromDatePid);
			provider.setToDatePid(toDatePid);
			provider.setEventName(eventName);
			int eventPid = provider.insert();
			LOGGER.info("Inserted event pid " + eventPid);

			if (locationPid != 0) {
				LocationEventProvider lep = new LocationEventProvider();
				lep.setLocationPid(locationPid);
				lep.setPrimaryLocation(primaryLocation);
				lep.setPrimaryEvent(primaryLocationEvent);

				// FIXME SEVERE: Referential integrity constraint violation:
				// "CONSTRAINT_C8: PUBLIC.LOCATION_EVENTS FOREIGN
				// KEY(LOCATION_PID)
				// REFERENCES PUBLIC.LOCATIONS(LOCATION_PID) (2)"; SQL
				// statement:
				// INSERT INTO PUBLIC.LOCATION_EVENTS( LOCATION_EVENTS_PID,
				// EVENT_PID, LOCATION_PID, PRIMARY_EVENT, PRIMARY_LOCATION,
				// INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID) VALUES (?, ?, ?, ?, ?,
				// CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4) [23506-168]

				int locationEventPid = lep.insert();
				LOGGER.info("Inserted location event " + locationEventPid);
			}

			if (personPid != 0) {
				PersonEventProvider pep = new PersonEventProvider();
				pep.setPersonPid(personPid);
				pep.setEventPid(eventRolePid);
				pep.setPrimaryPerson(primaryPerson);
				pep.setPrimaryEvent(primaryPersonEvent);
				int personEventPid = pep.insert();
				LOGGER.info("Inserted person event pid " + personEventPid);
			}

			eventBroker.post("MESSAGE",
					eventName + " inserted in the database as no. " + eventPid);
			eventBroker.post(Constants.EVENT_PID_UPDATE_TOPIC, eventPid);
			return true;
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String EventName) {
		this.eventName = EventName;
	}

	/**
	 * @param eventRolePid the eventRolePid to set
	 */
	public void setEventRolePid(int eventRolePid) {
		this.eventRolePid = eventRolePid;
	}

	/**
	 * @param eventTypePid the eventTypePid to set
	 */
	public void setEventTypePid(int eventTypePid) {
		this.eventTypePid = eventTypePid;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param primaryLocation the primaryLocation to set
	 */
	public void setIsPrimaryLocation(Boolean isPrimaryLocation) {
		this.primaryLocation = isPrimaryLocation;
	}

	/**
	 * @param primaryLocationEvent the primaryLocationEvent to set
	 */
	public void setIsPrimaryLocationEvent(Boolean isPrimaryLocationEvent) {
		this.primaryLocationEvent = isPrimaryLocationEvent;
	}

	/**
	 * @param primaryPerson the primaryPerson to set
	 */
	public void setIsPrimaryPerson(Boolean isPrimaryPerson) {
		this.primaryPerson = isPrimaryPerson;
	}

	/**
	 * @param primaryPersonEvent the primaryPersonEvent to set
	 */
	public void setIsPrimaryPersonEvent(Boolean isPrimaryPersonEvent) {
		this.primaryPersonEvent = isPrimaryPersonEvent;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		this.languagePid = languagePid;
	}

	/**
	 * @param locationPid the locationPid to set
	 */
	public void setLocationPid(int locationPid) {
		this.locationPid = locationPid;
	}

	/**
	 * @param page1 the page1 to set
	 */
	public void setPage1(NewEventWizardPage1 page1) {
		this.page1 = page1;
	}

	/**
	 * @param page2 the page2 to set
	 */
	public void setPage2(NewEventWizardPage2 page2) {
		this.page2 = page2;
	}

	/**
	 * @param page3 the page3 to set
	 */
	public void setPage3(NewEventWizardPage3 page3) {
		this.page3 = page3;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}

}
