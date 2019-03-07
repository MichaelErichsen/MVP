package net.myerichsen.hremvp.person.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.person.providers.SexProvider;

/**
 * Wizard to add a sex to a person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 3. mar. 2019
 *
 */
// FIXME Add from and to dates to insert and update
public class NewPersonSexWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewPersonSexWizardPage1 page1;
	private final int personPid;

	/**
	 * Constructor
	 *
	 * @param personPid
	 * @param context
	 */
	public NewPersonSexWizard(int personPid, IEclipseContext context) {
		setWindowTitle("Add a sex");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
		this.personPid = personPid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.
	 * IWizardPage)
	 */
	@Override
	public void addPages() {
		page1 = new NewPersonSexWizardPage1(context);
		addPage(page1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		final int sexTypePid = page1.getSexTypePid();

		if (sexTypePid > 0) {
			final SexProvider sexProvider = new SexProvider();
			sexProvider.setPersonPid(personPid);
			sexProvider.setSexTypePid(sexTypePid);
			sexProvider.setPrimarySex(page1.isPrimary());
			// FIXME Get pids if not 0
//			sexProvider.setFromDatePid(0);
//			sexProvider.setToDatePid(0);
			try {
				// FIXME SEVERE: Referential integrity constraint violation:
				// "SEX_TYPES_SEXES_FK: PUBLIC.SEXES FOREIGN KEY(SEX_TYPE_PID)
				// REFERENCES PUBLIC.SEX_TYPES(SEX_TYPE_PID) (4)"; SQL
				// statement:
				// INSERT INTO PUBLIC.SEXES( SEXES_PID, PERSON_PID,
				// SEX_TYPE_PID, PRIMARY_SEX, TABLE_ID, FROM_DATE_PID,
				// TO_DATE_PID) VALUES (?, ?, ?, ?, ?, ?, ?) [23506-197]

				final int sexPid = sexProvider.insert();

				LOGGER.info(
						"Inserted sex " + sexPid + " for person " + personPid);

				eventBroker.post("MESSAGE",
						"Inserted sex " + sexPid + " for person " + personPid);
				eventBroker.post(
						net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC,
						personPid);
				return true;
			} catch (Exception e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}

		}
		return false;
	}

}
