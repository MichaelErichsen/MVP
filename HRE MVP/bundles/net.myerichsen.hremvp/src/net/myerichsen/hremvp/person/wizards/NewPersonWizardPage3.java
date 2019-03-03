package net.myerichsen.hremvp.person.wizards;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.PersonNameMapProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. feb. 2019
 *
 */

public class NewPersonWizardPage3 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int personNameStylePid;
	private PersonNameMapProvider provider;
	private List<Text> textFieldList;
	private final IEventBroker eventBroker;

	/**
	 *
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonWizardPage3(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Name Parts");
		setDescription("Enter each part of the name");
		eventBroker = context.get(IEventBroker.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets. Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		try {
			final NewPersonWizard wizard = (NewPersonWizard) getWizard();
			personNameStylePid = wizard.getPersonNameStylePid();

			provider = new PersonNameMapProvider();
			List<List<String>> stringList = provider
					.getStringList(personNameStylePid);
			textFieldList = new ArrayList<>();

			for (int i = 0; i < stringList.size(); i++) {
				final Label lblNewLabel = new Label(container, SWT.NONE);
				lblNewLabel.setText(stringList.get(i).get(3));

				final Text text = new Text(container, SWT.BORDER);
				text.setLayoutData(
						new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				textFieldList.add(text);
			}
		} catch (final SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @return the PersonNameParts
	 */
	public List<String> getPersonNameParts() {
		final List<String> PersonNameParts = new ArrayList<>();

		for (final Text text : textFieldList) {
			PersonNameParts.add(text.getText());
		}

		return PersonNameParts;
	}

	/**
	 * @return the personNameStylePid
	 */
	public int getPersonNameStylePid() {
		return personNameStylePid;
	}

	/**
	 * @param personNameStylePid the personNameStylePid to set
	 */
	public void setPersonNameStylePid(int personNameStylePid) {
		this.personNameStylePid = personNameStylePid;
	}

}
