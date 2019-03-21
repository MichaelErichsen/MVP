package net.myerichsen.hremvp.project.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.EventRoleProvider;

/**
 * Wizard to add a event Role
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 21. mar. 2019
 *
 */
public class NewEventRoleWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewEventRoleWizardPage1 page1;
	private EventRoleProvider provider;

	/**
	 * Constructor
	 *
	 * @param eventRolePid
	 *
	 * @param context
	 */
	public NewEventRoleWizard(int eventRolePid, IEclipseContext context) {
		setWindowTitle("Add an event Role");
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
		page1 = new NewEventRoleWizardPage1(context);
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
				provider = new EventRoleProvider();
				provider.setAbbreviation(abbreviation);

				final int eventRolePid = provider.insert();
				LOGGER.info("Inserted event role " + eventRolePid);
				eventBroker.post("MESSAGE",
						"Inserted event role " + eventRolePid);

				final int labelPid = provider.getLabelPid();

				final List<List<String>> input = (List<List<String>>) page1
						.getTableViewer().getInput();

				for (int i = 0; i < input.size(); i++) {
					dp = new DictionaryProvider();
					dp.setIsoCode(input.get(i).get(2));
					dp.setLabel(input.get(i).get(3));
					dp.setLabelPid(labelPid);
					dp.setLabelType("EVENTROLE");
					final int dictionaryPid = dp.insert();
					LOGGER.info("Inserted dictionary element " + dictionaryPid
							+ ", " + input.get(i).get(2) + ", "
							+ input.get(i).get(3));
				}

				eventBroker.post(
						net.myerichsen.hremvp.Constants.EVENT_ROLE_PID_UPDATE_TOPIC,
						eventRolePid);
				return true;
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}

		}

		return false;
	}

}
