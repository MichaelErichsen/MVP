package net.myerichsen.hremvp.project.wizards;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.PersonNameMaps;
import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.PersonNameStyleProvider;

/**
 * Wizard to add a person name style
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 27. feb. 2019
 *
 */
public class NewPersonNameStyleWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewPersonNameStyleWizardPage1 page1;
	private NewPersonNameStyleWizardPage2 page2;
	private PersonNameStyleProvider provider;
	private String styleName;
	private String namePartCount;
	private String isoCode;

	/**
	 * Constructor
	 *
	 * @param personNameStylePid
	 * @param context
	 */
	public NewPersonNameStyleWizard(int personNameStylePid,
			IEclipseContext context) {
		setWindowTitle("Add a person name style");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addBackPages() {
		page2 = new NewPersonNameStyleWizardPage2(context);
		addPage(page2);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	@Override
	public void addPages() {
		page1 = new NewPersonNameStyleWizardPage1(context);
		addPage(page1);
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * @return the namePartCount
	 */
	public String getNamePartCount() {
		return namePartCount;
	}

	/**
	 * @return the styleName
	 */
	public String getStyleName() {
		return styleName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.IWizard#canFinish()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean performFinish() {
		if ((styleName.length() == 0) || (namePartCount.length() == 0)
				|| (Integer.parseInt(namePartCount) == 0)) {
			return false;
		}

		// Get next label pid
		try {
			DictionaryProvider dp = new DictionaryProvider();
			int labelPid = dp.getNextLabelPid();

			// Insert a person name style
			provider = new PersonNameStyleProvider();
			provider.setIsoCode(isoCode);
			provider.setLabelPid(labelPid);
			int personNameStylePid = provider.insert();
			LOGGER.info("Inserted person name style " + personNameStylePid);
			eventBroker.post("MESSAGE",
					"Inserted person name style " + personNameStylePid);

			// Create a dictionary row for the name
			dp = new DictionaryProvider();
			dp.setIsoCode(isoCode);
			dp.setLabel(styleName);
			dp.setLabelPid(labelPid);
			dp.setLabelType("PERSONNAME");
			dp.insert();
			LOGGER.info("Inserted person name style \"" + styleName
					+ "\" in dictionary");

			// Handle each table row in wizard page 2
			List<List<String>> input = (List<List<String>>) page2
					.getTableViewer().getInput();
			PersonNameMaps map;

			for (int i = 0; i < input.size(); i++) {
				// Create a dictionary row
				dp = new DictionaryProvider();
				labelPid = dp.getNextLabelPid();

				dp.setIsoCode(isoCode);
				dp.setLabel(input.get(i).get(1));
				dp.setLabelPid(labelPid);
				dp.setLabelType("PERSONNAMEMAP");
				int dictionaryPid = dp.insert();
				LOGGER.info("Inserted dictionary element " + dictionaryPid
						+ ", " + isoCode + ", \"" + input.get(i).get(1)
						+ "\", label pid " + labelPid);

				// Create a map row
				map = new PersonNameMaps();
				map.setLabelPid(labelPid);
				map.setNameStylePid(personNameStylePid);
				map.setPartNo(Integer.parseInt(input.get(i).get(0)));
				int nameMapPid = map.insert();
				LOGGER.info("Inserted map element " + nameMapPid + ", Part no. "
						+ input.get(i).get(0) + ", label pid " + labelPid);
			}

			eventBroker.post(
					net.myerichsen.hremvp.Constants.PERSON_NAME_STYLE_PID_UPDATE_TOPIC,
					personNameStylePid);

			return true;

		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	/**
	 * @param namePartCount the namePartCount to set
	 */
	public void setNamePartCount(String namePartCount) {
		this.namePartCount = namePartCount;
	}

	/**
	 * @param styleName the styleName to set
	 */
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

}
