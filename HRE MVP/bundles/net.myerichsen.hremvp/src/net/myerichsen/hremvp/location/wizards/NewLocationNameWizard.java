package net.myerichsen.hremvp.location.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.location.providers.LocationNamePartProvider;
import net.myerichsen.hremvp.location.providers.LocationNameProvider;

/**
 * Wizard to add a new location name
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 14. mar. 2019
 *
 */
public class NewLocationNameWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final IEclipseContext context;
	private NewLocationNameWizardPage1 page1;
	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	// FIXME Does not work yet
	public boolean performFinish() {
		final IEventBroker eventBroker = context.get(IEventBroker.class);
		try {
			final LocationNameProvider lnp = new LocationNameProvider();
	
			if (page3 == null) {
				return false;
			}
	
			lnp.setFromDatePid(page3.getFromDatePid());
			lnp.setToDatePid(page3.getFromDatePid());
			final int locationPid = lnp.insert();
			LOGGER.info("Inserted location name " + locationPid);
	
			lnp.setLocationPid(locationPid);
			lnp.setFromDatePid(page1.getFromDatePid());
			lnp.setToDatePid(page1.getFromDatePid());
			lnp.setPrimaryLocationName(true);
	
			final String s = page1.getComboLocationNameStyle();
			lnp.setLocationNameStylePid(Integer.parseInt(s));
	
			lnp.setPrimaryLocationName(
					page1.getBtnPrimaryLocationName().getSelection());
			lnp.setPreposition(page1.getTextPreposition().getText());
			final int locationNamePid = lnp.insert();
			LOGGER.info("Inserted location name " + locationNamePid);
	
			LocationNamePartProvider lnpp;
			List<List<String>> stringList = getPage2().getStringList();
	
			for (int i = 0; i < stringList.size(); i++) {
				lnpp = new LocationNamePartProvider();
				lnpp.setLocationNamePid(locationNamePid);
				lnpp.setPartNo(i + 1);
				lnpp.setLabel(stringList.get(i).get(4));
				final int locationNamePartPid = lnpp.insert();
				LOGGER.info(
						"Inserted location name part " + locationNamePartPid);
			}
	
			eventBroker.post("MESSAGE", locationName
					+ " inserted in the database as no. " + locationPid);
			eventBroker.post(Constants.LOCATION_PID_UPDATE_TOPIC, locationPid);
			return true;
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	private NewLocationNameWizardPage2 page2;
	private NewLocationNameWizardPage3 page3;
	private int locationNameStylePid = 0;
	private String locationName;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse Context
	 *
	 */
	public NewLocationNameWizard(IEclipseContext context) {
		setWindowTitle("New Location Name");
		setForcePreviousAndNextButtons(true);
		this.context = context;
	}

	/**
	 *
	 */
	public void addPage2() {
		page2 = new NewLocationNameWizardPage2(context);
		addPage(page2);
	}

	/**
	 *
	 */
	public void addBackPages() {
		page3 = new NewLocationNameWizardPage3(context);
		addPage(page3);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page1 = new NewLocationNameWizardPage1(context);
		addPage(page1);
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @return the locationNameStyle
	 */
	public int getLocationNameStylePid() {
		return locationNameStylePid;
	}

	/**
	 * @return the page1
	 */
	public NewLocationNameWizardPage1 getPage1() {
		return page1;
	}

	/**
	 * @return the page2
	 */
	public NewLocationNameWizardPage2 getPage2() {
		return page2;
	}

	/**
	 * @return the page3
	 */
	public NewLocationNameWizardPage3 getPage3() {
		return page3;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @param locationNameStyle the locationNameStyle to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		this.locationNameStylePid = locationNameStylePid;
	}

}
