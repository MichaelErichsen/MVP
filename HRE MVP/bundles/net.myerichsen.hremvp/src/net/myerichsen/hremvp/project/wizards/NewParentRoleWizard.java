package net.myerichsen.hremvp.project.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.ParentRoleProvider;

/**
 * Wizard to add an Parent role
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 28. mar. 2019
 *
 */
public class NewParentRoleWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewParentRoleWizardPage1 page1;
	private ParentRoleProvider provider;

	/**
	 * Constructor
	 *
	 * @param ParentRolePid
	 *
	 * @param context
	 */
	public NewParentRoleWizard(int ParentRolePid, IEclipseContext context) {
		setWindowTitle("Add an Parent role");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.
	 * IWizardPage)
	 */
	@Override
	public void addPages() {
		page1 = new NewParentRoleWizardPage1(context);
		addPage(page1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean performFinish() {
		DictionaryProvider dp;
		final String abbreviation = page1.getTextAbbreviation().getText();

		if (abbreviation.equals("") == false) {
			try {
				provider = new ParentRoleProvider();
				provider.setAbbreviation(abbreviation);

				final int ParentRolePid = provider.insert();
				LOGGER.info("Inserted Parent role " + ParentRolePid);
				eventBroker.post("MESSAGE",
						"Inserted Parent role " + ParentRolePid);

				final int labelPid = provider.getLabelPid();

				final List<List<String>> input = (List<List<String>>) page1
						.getTableViewer().getInput();

				for (int i = 0; i < input.size(); i++) {
					dp = new DictionaryProvider();
					dp.setIsoCode(input.get(i).get(2));
					dp.setLabel(input.get(i).get(3));
					dp.setLabelPid(labelPid);
					dp.setLabelType("ParentROLE");
					final int dictionaryPid = dp.insert();
					LOGGER.info("Inserted dictionary element " + dictionaryPid
							+ ", " + input.get(i).get(2) + ", "
							+ input.get(i).get(3));
				}

				eventBroker.post(
						net.myerichsen.hremvp.Constants.PARENT_ROLE_PID_UPDATE_TOPIC,
						ParentRolePid);
				return true;
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}

		}

		return false;
	}

	/**
	 * @param parseInt
	 */
	public void setParentRolePid(int parseInt) {
		// TODO Auto-generated method stub

	}

}