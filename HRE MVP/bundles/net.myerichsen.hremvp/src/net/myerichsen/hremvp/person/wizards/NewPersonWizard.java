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
 * Wizard to add a new person with sex, name, parents, paprtner and events
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 24. jan. 2019
 *
 */
public class NewPersonWizard extends Wizard {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private NewPersonWizardPage1 page1;
	private NewPersonWizardPage2 page2;
	private NewPersonWizardPage3 page3;
	private NewPersonWizardPage4 page4;
	private NewPersonWizardPage5 page5;

	private String personName;
	private int personNameStylePid;
	private int personPid;
	private final IEventBroker eventBroker;

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

			// Create father
			if (page4.getFatherPid() != 0) {
				parentProvider = new ParentProvider();
				parentProvider.setChild(personPid);
				parentProvider.setParent(page4.getFatherPid());
				// TODO Get role
				parentProvider.setParentRole("Father");
				parentProvider.setPrimaryParent(true);
				// TODO Get language pid
				parentProvider.setLanguagePid(1);
				parentProvider.insert();
				LOGGER.info("Inserted father pid " + page4.getFatherPid());
			}

			// Create mother
			if (page4.getMotherPid() != 0) {
				parentProvider = new ParentProvider();
				parentProvider.setChild(personPid);
				parentProvider.setParent(page4.getMotherPid());
				// TODO Get role
				parentProvider.setParentRole("Mother");
				parentProvider.setPrimaryParent(true);
				// TODO Get language pid
				parentProvider.setLanguagePid(1);
				parentProvider.insert();
				LOGGER.info("Inserted mother pid " + page4.getMotherPid());
			}

			// Create partner
			if (page4.getPartnerPid() != 0) {
				final PartnerProvider partnerProvider = new PartnerProvider();
				partnerProvider.setPartner1(personPid);
				partnerProvider.setPartner2(page4.getPartnerPid());
				partnerProvider.setPrimaryPartner(true);
				// TODO Get role, from and to dates
				partnerProvider.setRole("Role");
				partnerProvider.setFromDatePid(0);
				partnerProvider.setToDatePid(0);
				partnerProvider.insert();
				LOGGER.info("Inserted partner pid " + page4.getPartnerPid());
			}

			// Create child
			// TODO Create child

			// Page 5
			// Events
			final List<List<String>> listOfLists = page5.getListOfLists();

			for (final List<String> list : listOfLists) {

				// Create an Event
				// Returns Namelabel role from to
				final EventProvider ep = new EventProvider();
				// FIXME Returns label, not pid
				ep.setEventNamePid(Integer.parseInt(list.get(0)));
				// FIXME Get pids
				ep.setFromDatePid(Integer.parseInt(list.get(2)));
				ep.setToDatePid(Integer.parseInt(list.get(3)));
				final int eventPid = ep.insert();
				LOGGER.info("Inserted event pid " + eventPid);

				// Create a person-personEvent to link them together
				final PersonEventProvider pep = new PersonEventProvider();
				pep.setEventPid(eventPid);
				pep.setPersonPid(personPid);
				pep.setPrimaryEvent(true);
				pep.setPrimaryPerson(true);
				pep.setRole(list.get(1));
				final int personEventPid = pep.insert();
				LOGGER.info("Inserted person-event pid " + personEventPid);

			}

			eventBroker.post("MESSAGE", personName + " inserted in the database as no. " + personPid);
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
