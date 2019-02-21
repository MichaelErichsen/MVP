package net.myerichsen.hremvp.project.wizards;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

/**
 * Wizard to add a sex type
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 20. feb. 2019
 *
 */
public class NewSexTypeWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
//	private final IEventBroker eventBroker;

	private NewSexTypeWizardPage1 page1;
//	private LanguageProvider provider;
//	private int labelPid;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewSexTypeWizard(IEclipseContext context) {
		setWindowTitle("Add a sex type");
		setForcePreviousAndNextButtons(true);
		this.context = context;
//		eventBroker = context.get(IEventBroker.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.
	 * IWizardPage)
	 */
	@Override
	public void addPages() {
		try {
			page1 = new NewSexTypeWizardPage1(context);
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
		addPage(page1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
