package net.myerichsen.hremvp.person.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.person.providers.ParentProvider;
import net.myerichsen.hremvp.person.providers.PartnerProvider;
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
			PersonNameProvider personNameProvider = new PersonNameProvider();
			personNameProvider.setPersonPid(personPid);
			personNameProvider.setNameStylePid(page2.getPersonNameStylePid());
			personNameProvider.setFromDatePid(page2.getFromDatePid());
			personNameProvider.setToDatePid(page2.getToDatePid());
			personNameProvider.setPrimaryName(true);
			int namePid = personNameProvider.insert();
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
					LOGGER.info("Inserted name part" + namePartPid + " for person " + personPid);
				}
			}

			// Page 4
			// Primary father, mother and partner
//			private int ParentPid;
//			private int Child;
//			private int Parent;
//			private String ParentRole;
//			private boolean PrimaryParent;
//			private int LanguagePid;

//			private int PartnerPid;
//			private int Partner1;
//			private int Partner2;
//			private boolean PrimaryPartner;
//			private String Role;
//			private int FromDatePid;
//			private int ToDatePid;

			// Create father
			ParentProvider parentProvider = new ParentProvider();

			page4.getFatherPid();
			page4.getMotherPid();

			PartnerProvider partnerProvider = new PartnerProvider();
			page4.getPartnerPid();

			// Page 5
			// Events

//			private int EventPid;
//			private int FromDatePid;
//			private int ToDatePid;
//			private int EventNamePid;

			// Get all new events
			// Create person/personEvent objects for each

//			List<Integer> eventPidList = page5.getEventPidList();
//
//			for (Integer eventPid : eventPidList) {
//				EventProvider ep = new EventProvider();
//				ep.setEventNamePid(eventNamePid);
//				ep.setFromDatePid(i);
//				ep.setToDatePid(todate);
//				ep.insert();
//			}

//			PersonNameProvider lnp = new PersonNameProvider();
//			lnp.setPersonPid(personPid);
//			lnp.setFromDatePid(page2.getFromDatePid());
//			lnp.setToDatePid(page2.getFromDatePid());
//			lnp.setPrimaryPersonName(true);
//
//			String s = page2.getComboPersonNameStyles().getText();
//			String[] sa = s.split(",");
//			lnp.setPersonNameStylePid(Integer.parseInt(sa[0]));
//
//			lnp.setPrimaryPersonName(page2.getBtnPrimaryPersonName().getSelection());
//			lnp.setPreposition(page2.getTextPreposition().getText());
//			int personNamePid = lnp.insert();
//			LOGGER.info("Inserted person name " + personNamePid);
//
//			List<Label> labelList = page3.getLabelList();
//			List<Text> textList = page3.getTextList();
//
//			for (int i = 0; i < labelList.size(); i++) {
//				PersonNamePartProvider lnpp = new PersonNamePartProvider();
//				lnpp.setPersonNamePid(personNamePid);
//				lnpp.setPartNo(i + 1);
//				lnpp.setLabel(textList.get(i).getText());
//				int personNamePartPid = lnpp.insert();
//				LOGGER.info("Inserted person name part " + personNamePartPid);
//			}
//
//			eventBroker.post("MESSAGE", personName + " inserted in the database as no. " + personPid);
			return true;
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
//			eventBroker.post("MESSAGE", e.getMessage());
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
