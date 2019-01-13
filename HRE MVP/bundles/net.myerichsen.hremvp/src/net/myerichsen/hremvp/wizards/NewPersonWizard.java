package net.myerichsen.hremvp.wizards;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.person.providers.PersonProvider;

/**
 * Wizard to add a new person with sex, name, parents and events
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 13. jan. 2019
 *
 */
public class NewPersonWizard extends Wizard {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final IEclipseContext context;
	private NewPersonWizardPage1 page1;
	private NewPersonWizardPage2 page2;
	private NewPersonWizardPage3 page3;
	private NewPersonWizardPage4 page4;

	private int personNameStyle = 0;
	private String personName;

	@Inject
	private IEventBroker eventBroker;

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
		page3 = new NewPersonWizardPage3(context);
		addPage(page3);
		page4 = new NewPersonWizardPage4(context);
		addPage(page4);
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
	 * @return the page3
	 */
	public NewPersonWizardPage3 getPage() {
		return page3;
	}

	/**
	 * @return the page4
	 */
	public NewPersonWizardPage4 getPage4() {
		return page4;
	}

	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @return the personNameStyle
	 */
	public int getPersonNameStyle() {
		return personNameStyle;
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
			PersonProvider lp = new PersonProvider();
			lp.setBirthDatePid(page1.getBirthDatePid());
			lp.setDeathDatePid(page1.getDeathDatePid());
			int personPid = lp.insert();
			LOGGER.info("Inserted person " + personPid);

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
		} catch (Exception e) {
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
	 * @param personNameStyle the personNameStyle to set
	 */
	public void setPersonNameStyle(int personNameStyle) {
		this.personNameStyle = personNameStyle;
	}

}
