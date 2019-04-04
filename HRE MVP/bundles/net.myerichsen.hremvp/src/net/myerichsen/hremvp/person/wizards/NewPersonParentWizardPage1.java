package net.myerichsen.hremvp.person.wizards;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import net.myerichsen.hremvp.person.dialogs.PersonNavigatorDialog;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Person Parent wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 17. feb. 2019
 *
 */
public class NewPersonParentWizardPage1 extends WizardPage {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private Shell parentShell;

	private Text textParentPersonPid;
	private Text textParentName;
	private Text textParentBirthDate;
	private Text textParentDeathDate;
	private Text textParentRole;

	private int parentPid = 0;

	private String parentRole = "";

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonParentWizardPage1(IEclipseContext context) {
		super("New Person Parent Wizard Page 1");
		setTitle("Parent");
		setDescription("Add a parent to the person.");
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 *
	 */
	protected void browseParent() {
		final PersonNavigatorDialog dialog = new PersonNavigatorDialog(
				parentShell, context);
		if (dialog.open() == Window.OK) {
			try {
				parentPid = dialog.getPersonPid();
				textParentPersonPid.setText(Integer.toString(parentPid));
				textParentName.setText(dialog.getPersonName());
				textParentBirthDate.setText(dialog.getBirthDate());
				textParentDeathDate.setText(dialog.getDeathDate());
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
				eventBroker.post("MESSAGE", e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	protected void clearParent() {
		parentPid = 0;
		textParentPersonPid.setText("");
		textParentName.setText("");
		textParentBirthDate.setText("");
		textParentDeathDate.setText("");
	}

	@Override
	public void createControl(Composite parent) {
		parentShell = parent.getShell();
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(3, false));

		final Label lblParent = new Label(container, SWT.NONE);
		lblParent.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblParent.setText("Parent");

		textParentPersonPid = new Text(container, SWT.BORDER);
		textParentPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeParent = new Composite(container, SWT.NONE);
		compositeParent.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateParent = new Button(compositeParent, SWT.NONE);
		btnUpdateParent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateParent();
			}
		});
		btnUpdateParent.setText("Update");

		final Button btnBrowseParent = new Button(compositeParent, SWT.NONE);
		btnBrowseParent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseParent();
			}
		});
		btnBrowseParent.setText("Browse");

		final Button btnClearParent = new Button(compositeParent, SWT.NONE);
		btnClearParent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearParent();
			}
		});
		btnClearParent.setText("Clear");

		textParentName = new Text(container, SWT.BORDER);
		textParentName.setEditable(false);
		textParentName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textParentBirthDate = new Text(container, SWT.BORDER);
		textParentBirthDate.setEditable(false);
		textParentBirthDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textParentDeathDate = new Text(container, SWT.BORDER);
		textParentDeathDate.setEditable(false);
		textParentDeathDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		final Label lblParentRole = new Label(container, SWT.NONE);
		lblParentRole.setText("Parent role");

		textParentRole = new Text(container, SWT.BORDER);
		textParentRole.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				parentRole = textParentRole.getText();
			}
		});
		textParentRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

	}

	/**
	 * @return the parentPid
	 */
	public int getParentPid() {
		return parentPid;
	}

	/**
	 * @return the parentRole
	 */
	public String getParentRole() {
		return parentRole;
	}

	/**
	 *
	 */
	protected void updateParent() {
		parentPid = Integer.parseInt(textParentPersonPid.getText());
		PersonProvider provider;

		try {
			final HDateProvider dateProvider = new HDateProvider();
			provider = new PersonProvider();
			provider.get(parentPid);
			textParentName.setText(provider.getPrimaryName());
			final int birthDatePid = provider.getBirthDatePid();
			if (birthDatePid > 0) {
				dateProvider.get(birthDatePid);
				textParentBirthDate.setText(dateProvider.getDate().toString());
			} else {
				textParentBirthDate.setText("");
			}
			final int deathDatePid = provider.getDeathDatePid();
			if (deathDatePid > 0) {
				dateProvider.get(deathDatePid);
				textParentDeathDate.setText(dateProvider.getDate().toString());
			} else {
				textParentDeathDate.setText("");
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}
}
