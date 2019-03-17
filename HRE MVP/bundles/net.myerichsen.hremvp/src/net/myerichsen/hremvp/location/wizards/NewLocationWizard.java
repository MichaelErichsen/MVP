package net.myerichsen.hremvp.location.wizards;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.location.providers.LocationNamePartProvider;
import net.myerichsen.hremvp.location.providers.LocationNameProvider;
import net.myerichsen.hremvp.location.providers.LocationProvider;

/**
 * Wizard to add a new location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 17. mar. 2019
 *
 */
public class NewLocationWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final IEclipseContext context;
	private NewLocationWizardPage1 page1;
	private NewLocationWizardPage2 page2;
	private NewLocationWizardPage3 page3;
	private NewLocationWizardPage4 page4;
	private int locationNameStylePid = 0;
	private String locationName;
	private Double xCoordinate = 0D;
	private Double yCoordinate = 0D;
	private Double zCoordinate = 0D;
	private Boolean primary = true;
	private List<List<String>> locationNamePartList;

	// FIXME Does not save location name parts
	/**
	 * Constructor
	 *
	 * @param context The Eclipse Context
	 *
	 */
	public NewLocationWizard(IEclipseContext context) {
		setWindowTitle("New Location");
		setForcePreviousAndNextButtons(true);
		this.context = context;
	}

	/**
	 *
	 */
	public void addBackPages() {
		page3 = new NewLocationWizardPage3(context);
		addPage(page3);
		page4 = new NewLocationWizardPage4();
		addPage(page4);
	}

	/**
	 *
	 */
	public void addPage2() {
		page2 = new NewLocationWizardPage2(context);
		addPage(page2);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page1 = new NewLocationWizardPage1(context);
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
	public NewLocationWizardPage1 getPage1() {
		return page1;
	}

	/**
	 * @return the page2
	 */
	public NewLocationWizardPage2 getPage2() {
		return page2;
	}

	/**
	 * @return the page3
	 */
	public NewLocationWizardPage3 getPage3() {
		return page3;
	}

	/**
	 * @return the page4
	 */
	public NewLocationWizardPage4 getPage4() {
		return page4;
	}

	/**
	 * @return the primary
	 */
	public Boolean getPrimary() {
		return primary;
	}

	/**
	 * @return the xCoordinate
	 */
	public Double getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * @return the yCoordinate
	 */
	public Double getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * @return the zCoordinate
	 */
	public Double getzCoordinate() {
		return zCoordinate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		final IEventBroker eventBroker = context.get(IEventBroker.class);

		try {
			final LocationProvider lp = new LocationProvider();
			if (page3 == null) {
				return false;
			}

			lp.setFromDatePid(page3.getFromDatePid());
			lp.setToDatePid(page3.getFromDatePid());
			lp.setxCoordinate(BigDecimal.valueOf(xCoordinate));
			lp.setyCoordinate(BigDecimal.valueOf(yCoordinate));
			lp.setzCoordinate(BigDecimal.valueOf(zCoordinate));
			lp.setPrimaryLocation(primary);
			final int locationPid = lp.insert();
			LOGGER.info("Inserted location " + locationPid);

			final LocationNameProvider lnp = new LocationNameProvider();
			lnp.setLocationPid(locationPid);
			lnp.setFromDatePid(page1.getFromDatePid());
			lnp.setToDatePid(page1.getFromDatePid());
			lnp.setPrimaryLocationName(true);

			final String s = page1.getComboLocationNameStyle();
			lnp.setLocationNameStylePid(Integer.parseInt(s));

			lnp.setPrimaryLocationName(primary);
			lnp.setPreposition(page1.getTextPreposition().getText());
			final int locationNamePid = lnp.insert();
			LOGGER.info("Inserted location name " + locationNamePid);

			LocationNamePartProvider lnpp;
			final List<List<String>> stringList = getPage2().getStringList();

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

	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setxCoordinate(Double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setyCoordinate(Double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * @param zCoordinate the zCoordinate to set
	 */
	public void setzCoordinate(Double zCoordinate) {
		this.zCoordinate = zCoordinate;
	}

}
