package net.myerichsen.hremvp.event.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

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
	private String EventName = "";
	private int languagePid = 0;
	private int fromDatePid = 0;
	private int toDatePid = 0;

	// Page 2
	private int locationPid = 0;
	private Boolean isPrimaryLocation = true;
	private Boolean isPrimaryLocationEvent = true;

	// Page 3
	private int personPid = 0;
	private int eventRolePid = 0;
	private Boolean isPrimaryPerson = true;
	private Boolean isPrimaryPersonEvent = true;

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
	 * @return the EventName
	 */
	public String getEventName() {
		return EventName;
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
	 * @return the isPrimaryLocation
	 */
	public Boolean getIsPrimaryLocation() {
		return isPrimaryLocation;
	}

	/**
	 * @return the isPrimaryLocationEvent
	 */
	public Boolean getIsPrimaryLocationEvent() {
		return isPrimaryLocationEvent;
	}

	/**
	 * @return the isPrimaryPerson
	 */
	public Boolean getIsPrimaryPerson() {
		return isPrimaryPerson;
	}

	/**
	 * @return the isPrimaryPersonEvent
	 */
	public Boolean getIsPrimaryPersonEvent() {
		return isPrimaryPersonEvent;
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
//		// Page 1
//		private int eventTypePid = 0;
//		private String EventName = "";
//		private int languagePid = 0;
//		private int fromDatePid = 0;
//		private int toDatePid = 0;
//
//		// Page 2
//		private int locationPid = 0;
//		private Boolean isPrimaryLocation = true;
//		private Boolean isPrimaryLocationEvent = true;
//
//		// Page 3
//		private int personPid = 0;
//		private int eventRolePid = 0;
//		private Boolean isPrimaryPerson = true;
//		private Boolean isPrimaryPersonEvent = true;
		return false;
	}

	/**
	 * @param EventName the EventName to set
	 */
	public void setEventName(String EventName) {
		this.EventName = EventName;
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
	 * @param isPrimaryLocation the isPrimaryLocation to set
	 */
	public void setIsPrimaryLocation(Boolean isPrimaryLocation) {
		this.isPrimaryLocation = isPrimaryLocation;
	}

	/**
	 * @param isPrimaryLocationEvent the isPrimaryLocationEvent to set
	 */
	public void setIsPrimaryLocationEvent(Boolean isPrimaryLocationEvent) {
		this.isPrimaryLocationEvent = isPrimaryLocationEvent;
	}

	/**
	 * @param isPrimaryPerson the isPrimaryPerson to set
	 */
	public void setIsPrimaryPerson(Boolean isPrimaryPerson) {
		this.isPrimaryPerson = isPrimaryPerson;
	}

	/**
	 * @param isPrimaryPersonEvent the isPrimaryPersonEvent to set
	 */
	public void setIsPrimaryPersonEvent(Boolean isPrimaryPersonEvent) {
		this.isPrimaryPersonEvent = isPrimaryPersonEvent;
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
