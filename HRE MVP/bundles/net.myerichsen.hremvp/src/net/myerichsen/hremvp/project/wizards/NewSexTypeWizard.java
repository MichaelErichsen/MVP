package net.myerichsen.hremvp.project.wizards;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.SexTypeProvider;

/**
 * Wizard to add a sex type
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 23. feb. 2019
 *
 */
public class NewSexTypeWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewSexTypeWizardPage1 page1;
	private SexTypeProvider provider;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewSexTypeWizard(IEclipseContext context) {
		setWindowTitle("Add a sex type");
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
		page1 = new NewSexTypeWizardPage1(context);
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
				provider = new SexTypeProvider();
				provider.setAbbreviation(abbreviation);

				final int sexTypePid = provider.insert();
				LOGGER.info("Inserted sex type " + sexTypePid);
				eventBroker.post("MESSAGE", "Inserted sex type " + sexTypePid);

				final int labelPid = provider.getLabelPid();

				final List<List<String>> input = (List<List<String>>) page1
						.getTableViewer().getInput();

				for (int i = 0; i < input.size(); i++) {
					dp = new DictionaryProvider();
					dp.setIsoCode(input.get(i).get(2));
					dp.setLabel(input.get(i).get(3));
					dp.setLabelPid(labelPid);
					final int dictionaryPid = dp.insert();
					LOGGER.info("Inserted dictionary element " + dictionaryPid
							+ ", " + input.get(i).get(2) + ", "
							+ input.get(i).get(3));
				}

				eventBroker.post(
						net.myerichsen.hremvp.Constants.SEX_TYPE_PID_UPDATE_TOPIC,
						sexTypePid);
				return true;
			} catch (SQLException | MvpException e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}

		}

		return false;
	}

}
