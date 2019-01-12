package net.myerichsen.hremvp.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

/**
 * Wizard to add a new person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 12. jan. 2019
 *
 */
public class NewPersonWizard extends Wizard {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final IEclipseContext context;
	private NewPersonWizardPage1 page1;

	private int personNameStyle = 0;
	private String personName;

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
	}

	/**
	 * @return the page1
	 */
	public NewPersonWizardPage1 getPage1() {
		return page1;
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

//		try {
//			PersonProvider lp = new PersonProvider();
//			lp.setFromDatePid(page1.getFromDatePid());
//			lp.setToDatePid(page1.getFromDatePid());
//			lp.setxCoordinate(new BigDecimal(page1.getTextXCoordinate().getText()));
//			lp.setyCoordinate(new BigDecimal(page1.getTextYCoordinate().getText()));
//			lp.setzCoordinate(new BigDecimal(page1.getTextZCoordinate().getText()));
//			lp.setPrimaryPerson(page1.getBtnCheckButtonPrimary().getSelection());
//			int personPid = lp.insert();
//			LOGGER.info("Inserted person " + personPid);

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
//			return true;
//		} catch (Exception e) {
//			LOGGER.severe(e.getMessage());
//			eventBroker.post("MESSAGE", e.getMessage());
//			e.printStackTrace();
//		}
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
