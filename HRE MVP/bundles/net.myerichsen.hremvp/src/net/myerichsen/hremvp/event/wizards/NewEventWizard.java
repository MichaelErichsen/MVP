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
	private IEclipseContext context;

	private NewEventWizardPage1 page1;
	private NewEventWizardPage2 page2;
	private NewEventWizardPage3 page3;

	private int EventNameStyle = 0;
	private String EventName;

	private final IEventBroker eventBroker;

	private int fromDatePid;
	private int toDatePid;

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
	 * @return the EventNameStyle
	 */
	public int getEventNameStyle() {
		return EventNameStyle;
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
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
		return false;
	}

	/**
	 * @param EventName the EventName to set
	 */
	public void setEventName(String EventName) {
		this.EventName = EventName;
	}

	/**
	 * @param EventNameStyle the EventNameStyle to set
	 */
	public void setEventNameStyle(int EventNameStyle) {
		this.EventNameStyle = EventNameStyle;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}

}
