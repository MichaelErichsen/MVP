package net.myerichsen.hremvp.event.wizards;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.location.providers.LocationNamePartProvider;
import net.myerichsen.hremvp.location.providers.LocationNameProvider;
import net.myerichsen.hremvp.location.providers.LocationProvider;
import net.myerichsen.hremvp.location.wizards.NewLocationWizardPage1;
import net.myerichsen.hremvp.location.wizards.NewLocationWizardPage2;
import net.myerichsen.hremvp.location.wizards.NewLocationWizardPage3;
import net.myerichsen.hremvp.location.wizards.NewLocationWizardPage4;

/**
 * Wizard to add a new location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. jan. 2019
 *
 */
// FIXME Create
public class NewEventWizard extends Wizard {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final IEclipseContext context;
	private NewLocationWizardPage1 page1;
	private NewLocationWizardPage2 page2;
	private NewLocationWizardPage3 page3;
	private NewLocationWizardPage4 page4;
	private int locationNameStyle = 0;
	private String locationName;

	private IEventBroker eventBroker;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse Context
	 *
	 */
	public NewEventWizard(IEclipseContext context) {
		setWindowTitle("New Location");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 *
	 */
	public void addPage3() {
		page3 = new NewLocationWizardPage3(context);
		addPage(page3);
	}

	/**
	 *
	 */
	public void addPage4() {
		page4 = new NewLocationWizardPage4();
		addPage(page4);
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
		page2 = new NewLocationWizardPage2(context);
		addPage(page2);
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
	public int getLocationNameStyle() {
		return locationNameStyle;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			final LocationProvider lp = new LocationProvider();
			lp.setFromDatePid(page1.getFromDatePid());
			lp.setToDatePid(page1.getFromDatePid());
			lp.setxCoordinate(new BigDecimal(page1.getTextXCoordinate().getText()));
			lp.setyCoordinate(new BigDecimal(page1.getTextYCoordinate().getText()));
			lp.setzCoordinate(new BigDecimal(page1.getTextZCoordinate().getText()));
			lp.setPrimaryLocation(page1.getBtnCheckButtonPrimary().getSelection());
			final int locationPid = lp.insert();
			LOGGER.info("Inserted location " + locationPid);

			final LocationNameProvider lnp = new LocationNameProvider();
			lnp.setLocationPid(locationPid);
			lnp.setFromDatePid(page2.getFromDatePid());
			lnp.setToDatePid(page2.getFromDatePid());
			lnp.setPrimaryLocationName(true);

			final String s = page2.getComboLocationNameStyles().getText();
			final String[] sa = s.split(",");
			lnp.setLocationNameStylePid(Integer.parseInt(sa[0]));

			lnp.setPrimaryLocationName(page2.getBtnPrimaryLocationName().getSelection());
			lnp.setPreposition(page2.getTextPreposition().getText());
			final int locationNamePid = lnp.insert();
			LOGGER.info("Inserted location name " + locationNamePid);

			final List<Label> labelList = page3.getLabelList();
			final List<Text> textList = page3.getTextList();

			for (int i = 0; i < labelList.size(); i++) {
				final LocationNamePartProvider lnpp = new LocationNamePartProvider();
				lnpp.setLocationNamePid(locationNamePid);
				lnpp.setPartNo(i + 1);
				lnpp.setLabel(textList.get(i).getText());
				final int locationNamePartPid = lnpp.insert();
				LOGGER.info("Inserted location name part " + locationNamePartPid);
			}

			eventBroker.post("MESSAGE", locationName + " inserted in the database as no. " + locationPid);
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
	public void setLocationNameStyle(int locationNameStyle) {
		this.locationNameStyle = locationNameStyle;
	}

}
