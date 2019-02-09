package net.myerichsen.hremvp.person.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.event.providers.EventProvider;
import net.myerichsen.hremvp.person.providers.ParentProvider;
import net.myerichsen.hremvp.person.providers.PartnerProvider;
import net.myerichsen.hremvp.person.providers.PersonEventProvider;
import net.myerichsen.hremvp.person.providers.PersonNamePartProvider;
import net.myerichsen.hremvp.person.providers.PersonNameProvider;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.person.providers.SexProvider;

/**
 * Wizard to add a new person with sex, name, parents, partner and events
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 9. feb. 2019
 *
 */
public class NewPersonWizard extends Wizard {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewPersonWizardPage1 page1;
	private NewPersonWizardPage2 page2;
	private NewPersonWizardPage3 page3;
	private NewPersonWizardPage4 page4;
	private NewPersonWizardPage5 page5;

	private String personName;
	private int personNameStylePid;
	private int personPid;
	private int languagePid;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse Context
	 *
	 */
	public NewPersonWizard(IEclipseContext context) {
		setWindowTitle("New Person");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 *
	 */
	public void addBackPages() {
		page3 = new NewPersonWizardPage3(context);
		addPage(page3);
		page4 = new NewPersonWizardPage4(context);
		addPage(page4);
		page5 = new NewPersonWizardPage5(context);
		addPage(page5);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page1 = new NewPersonWizardPage1(context);
		addPage(page1);
		page2 = new NewPersonWizardPage2(context);
		addPage(page2);
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return languagePid;
	}

	/**
	 * @return the page3
	 */
	public NewPersonWizardPage3 getPage() {
		return page3;
	}

	/**
	 * @return the page1
	 */
	public NewPersonWizardPage1 getPage1() {
		return page1;
	}

	/**
	 * @return the page2
	 */
	public NewPersonWizardPage2 getPage2() {
		return page2;
	}

	/**
	 * @return the page4
	 */
	public NewPersonWizardPage4 getPage4() {
		return page4;
	}

	/**
	 * @return the page5
	 */
	public NewPersonWizardPage5 getPage5() {
		return page5;
	}

	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @return the personNameStylePid
	 */
	public int getPersonNameStylePid() {
		return personNameStylePid;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * /* (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		context.get(IEventBroker.class);

		try {
			// Page 1
			// Birth date, death date and sex
			// Create a new person
			final PersonProvider personProvider = new PersonProvider();
			personProvider.setBirthDatePid(page1.getBirthDatePid());
			personProvider.setDeathDatePid(page1.getDeathDatePid());
			personPid = personProvider.insert();
			LOGGER.info("Inserted person " + personPid);

			// Create a sex for the person
			final SexProvider sexProvider = new SexProvider();
			sexProvider.setPersonPid(personPid);
			sexProvider.setSexTypePid(page1.getSexTypePid());
			sexProvider.setPrimarySex(true);
			final int sexPid = sexProvider.insert();
			LOGGER.info("Inserted sex " + sexPid + " for person " + personPid);

			// Page 2
			// Name validity dates
			// Create a new name
			final PersonNameProvider personNameProvider = new PersonNameProvider();
			personNameProvider.setPersonPid(personPid);
			personNameProvider.setNameStylePid(page2.getPersonNameStylePid());
			personNameProvider.setFromDatePid(page2.getFromDatePid());
			personNameProvider.setToDatePid(page2.getToDatePid());
			personNameProvider.setPrimaryName(true);
			final int namePid = personNameProvider.insert();
			LOGGER.info("Inserted name " + namePid + " for person " + personPid);

			// Page 3
			// Name parts
			PersonNamePartProvider personNamePartProvider;
			final List<String> nameParts = page3.getNameParts();
			String string;
			int namePartPid;

			// Create each name part
			for (int i = 0; i < nameParts.size(); i++) {
				string = nameParts.get(i);

				if (string != null) {
					personNamePartProvider = new PersonNamePartProvider();
					personNamePartProvider.setNamePid(namePid);
					personNamePartProvider.setLabel(string);
					personNamePartProvider.setPartNo(i);
					namePartPid = personNamePartProvider.insert();
					LOGGER.info("Inserted name part " + namePartPid + " for person " + personPid);
				}
			}

			// Page 4
			// Primary father, mother, child and partner
			ParentProvider parentProvider;
			int parentPid;

			// Create father
			if (page4.getFatherPid() != 0) {
				parentProvider = new ParentProvider();
				parentProvider.setChild(personPid);
				parentProvider.setParent(page4.getFatherPid());
				parentProvider.setParentRole(page4.getFatherRole());
				parentProvider.setPrimaryParent(true);
				parentProvider.setLanguagePid(languagePid);
				parentPid = parentProvider.insert();
				LOGGER.info("Inserted father pid " + parentPid);
			}

			// Create mother
			if (page4.getMotherPid() != 0) {
				parentProvider = new ParentProvider();
				parentProvider.setChild(personPid);
				parentProvider.setParent(page4.getMotherPid());
				parentProvider.setParentRole(page4.getMotherRole());
				parentProvider.setPrimaryParent(true);
				parentProvider.setLanguagePid(languagePid);
				parentPid = parentProvider.insert();
				LOGGER.info("Inserted mother pid " + parentPid);
			}

			// Create child
			if (page4.getChildPid() != 0) {
				parentProvider = new ParentProvider();
				parentProvider.setParent(personPid);
				parentProvider.setChild(page4.getChildPid());
				parentProvider.setParentRole(page4.getChildRole());
				parentProvider.setPrimaryParent(true);
				parentProvider.setLanguagePid(languagePid);
				parentPid = parentProvider.insert();
				LOGGER.info("Inserted child pid " + parentPid);
			}

			// Create partner
			if (page4.getPartnerPid() != 0) {
				final PartnerProvider partnerProvider = new PartnerProvider();
				partnerProvider.setPartner1(personPid);
				partnerProvider.setPartner2(page4.getPartnerPid());
				partnerProvider.setPrimaryPartner(true);
				partnerProvider.setRole(page4.getPartnerRole());
				partnerProvider.setFromDatePid(page4.getPartnerFromDatePid());
				partnerProvider.setToDatePid(page4.getPartnerToDatePid());
				partnerProvider.insert();
				LOGGER.info("Inserted partner pid " + page4.getPartnerPid());
			}

			// Page 5
			// Events
			final List<List<String>> listOfLists = page5.getListOfLists();

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

			}

			eventBroker.post("MESSAGE",
					personProvider.getPrimaryName() + " inserted in the database as no. " + personPid);
			eventBroker.post(net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC, personPid);
			eventBroker.post(net.myerichsen.hremvp.Constants.NAME_PID_UPDATE_TOPIC, namePid);
// FIXME Does not refresh person navigator
			return true;
		} catch (

		final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		this.languagePid = languagePid;
	}

	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * @param personNameStylePid the personNameStylePid to set
	 */
	public void setPersonNameStylePid(int personNameStylePid) {
		this.personNameStylePid = personNameStylePid;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

}
