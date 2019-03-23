package net.myerichsen.hremvp.person.wizards;

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

import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.person.dialogs.PersonNavigatorDialog;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Person partner wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 16. mar. 2019
 *
 */
public class NewPersonPartnerWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private Shell parentShell;

	private Text textPartnerPersonPid;
	private Text textPartnerName;
	private Text textPartnerBirthDate;
	private Text textPartnerDeathDate;
	private Text textPartnerRole;
	private Text textPartnershipStartDate;
	private Text textPartnershipEndDate;

	private int partnerPid = 0;
	private String partnerRole = "";
	private int partnerFromDatePid = 0;
	private int partnerToDatePid = 0;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonPartnerWizardPage1(IEclipseContext context) {
		super("New Person Partner Wizard Page 1");
		setTitle("Partner");
		setDescription("Add a partner for the person.");
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 *
	 */
	protected void browsePartner() {
		final PersonNavigatorDialog dialog = new PersonNavigatorDialog(
				parentShell, context);
		if (dialog.open() == Window.OK) {
			try {
				partnerPid = dialog.getPersonPid();
				textPartnerPersonPid.setText(Integer.toString(partnerPid));
				textPartnerName.setText(dialog.getPersonName());
				textPartnerBirthDate.setText(dialog.getBirthDate());
				textPartnerDeathDate.setText(dialog.getDeathDate());
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	protected void browsePartnerEnd() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(
				textPartnershipEndDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				partnerToDatePid = dialog.getHdatePid();

				if (partnerToDatePid > 0) {
					final HDateProvider hdp = new HDateProvider();
					hdp.get(partnerToDatePid);
					textPartnershipEndDate.setText(hdp.getDate().toString());
				} else {
					textPartnershipEndDate.setText("");
				}

			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 *
	 */
	protected void browsePartnerStart() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(
				textPartnershipStartDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				partnerFromDatePid = dialog.getHdatePid();

				if (partnerFromDatePid > 0) {
					final HDateProvider hdp = new HDateProvider();
					hdp.get(partnerFromDatePid);
					textPartnershipStartDate.setText(hdp.getDate().toString());
				} else {
					textPartnershipStartDate.setText("");
				}

			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 *
	 */
	protected void clearPartner() {
		partnerPid = 0;
		textPartnerPersonPid.setText("");
		textPartnerName.setText("");
		textPartnerBirthDate.setText("");
		textPartnerDeathDate.setText("");
	}

	/**
	 *
	 */
	protected void clearPartnerEnd() {
		textPartnershipEndDate.setText("");
		partnerToDatePid = 0;
	}

	/**
	 *
	 */
	protected void clearPartnerStart() {
		textPartnershipStartDate.setText("");
		partnerFromDatePid = 0;
	}

	@Override
	public void createControl(Composite parent) {
		parentShell = parent.getShell();
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(3, false));

		final Label lblPartner = new Label(container, SWT.NONE);
		lblPartner
				.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPartner.setText("Partner");

		textPartnerPersonPid = new Text(container, SWT.BORDER);
		textPartnerPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositePartner = new Composite(container, SWT.NONE);
		compositePartner.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdatePartner = new Button(compositePartner, SWT.NONE);
		btnUpdatePartner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updatePartner();
			}
		});
		btnUpdatePartner.setText("Update");

		final Button btnBrowsePartner = new Button(compositePartner, SWT.NONE);
		btnBrowsePartner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browsePartner();
			}
		});
		btnBrowsePartner.setText("Browse");

		final Button btnClearPartner = new Button(compositePartner, SWT.NONE);
		btnClearPartner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearPartner();
			}
		});
		btnClearPartner.setText("Clear");

		textPartnerName = new Text(container, SWT.BORDER);
		textPartnerName.setEditable(false);
		textPartnerName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textPartnerBirthDate = new Text(container, SWT.BORDER);
		textPartnerBirthDate.setEditable(false);
		textPartnerBirthDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textPartnerDeathDate = new Text(container, SWT.BORDER);
		textPartnerDeathDate.setEditable(false);
		textPartnerDeathDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Label lblPartnerRole = new Label(container, SWT.NONE);
		lblPartnerRole.setText("Partner role");

		textPartnerRole = new Text(container, SWT.BORDER);
		textPartnerRole.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				partnerRole = textPartnerRole.getText();
			}
		});
		textPartnerRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblStartOfPartnership = new Label(container, SWT.NONE);
		lblStartOfPartnership.setText("Start of partnership");

		textPartnershipStartDate = new Text(container, SWT.BORDER);
		textPartnershipStartDate.setEditable(false);
		textPartnershipStartDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositePartnerStart = new Composite(container,
				SWT.NONE);
		compositePartnerStart.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonNewPartnerStart = new Button(compositePartnerStart,
				SWT.NONE);
		buttonNewPartnerStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				newPartnerStart();
			}
		});
		buttonNewPartnerStart.setText("New");

		final Button buttonBrowsePartnerStart = new Button(
				compositePartnerStart, SWT.NONE);
		buttonBrowsePartnerStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browsePartnerStart();
			}
		});
		buttonBrowsePartnerStart.setText("Browse");

		final Button buttonClearPartnerStart = new Button(compositePartnerStart,
				SWT.NONE);
		buttonClearPartnerStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearPartnerStart();
			}
		});
		buttonClearPartnerStart.setText("Clear");

		final Label lblEndOfPartnership = new Label(container, SWT.NONE);
		lblEndOfPartnership.setText("End of partnership");

		textPartnershipEndDate = new Text(container, SWT.BORDER);
		textPartnershipEndDate.setEditable(false);
		textPartnershipEndDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonNewPartnerEnd = new Button(composite_1, SWT.NONE);
		buttonNewPartnerEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				newPartnerEnd();
			}
		});
		buttonNewPartnerEnd.setText("New");

		final Button buttonBrowsePartnerEnd = new Button(composite_1, SWT.NONE);
		buttonBrowsePartnerEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browsePartnerEnd();
			}
		});
		buttonBrowsePartnerEnd.setText("Browse");

		final Button buttonClearBrowserEnd = new Button(composite_1, SWT.NONE);
		buttonClearBrowserEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearPartnerEnd();
			}
		});
		buttonClearBrowserEnd.setText("Clear");

	}

	/**
	 * @return the partnerFromDatePid
	 */
	public int getPartnerFromDatePid() {
		return partnerFromDatePid;
	}

	/**
	 * @return the partnerPid
	 */
	public int getPartnerPid() {
		return partnerPid;
	}

	/**
	 * @return the partnerRole
	 */
	public String getPartnerRole() {
		return partnerRole;
	}

	/**
	 * @return the partnerToDatePid
	 */
	public int getPartnerToDatePid() {
		return partnerToDatePid;
	}

	/**
	 *
	 */
	protected void newPartnerEnd() {
		final DateDialog dialog = new DateDialog(
				textPartnershipEndDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				partnerToDatePid = hdp.insert();
				textPartnershipEndDate.setText(dialog.getDate().toString());
			} catch (final Exception e1) {
				LOGGER.severe(e1.getMessage());
			}
		}

	}

	/**
	 *
	 */
	protected void newPartnerStart() {
		final DateDialog dialog = new DateDialog(
				textPartnershipStartDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				partnerFromDatePid = hdp.insert();
				textPartnershipStartDate.setText(dialog.getDate().toString());
			} catch (final Exception e1) {
				LOGGER.severe(e1.getMessage());
			}
		}

	}

	/**
	 *
	 */
	protected void updatePartner() {
		partnerPid = Integer.parseInt(textPartnerPersonPid.getText());
		PersonProvider provider;

		try {
			final HDateProvider dateProvider = new HDateProvider();
			provider = new PersonProvider();
			provider.get(partnerPid);
			textPartnerName.setText(provider.getPrimaryName());
			final int birthDatePid = provider.getBirthDatePid();
			if (birthDatePid > 0) {
				dateProvider.get(birthDatePid);
				textPartnerBirthDate.setText(dateProvider.getDate().toString());
			} else {
				textPartnerBirthDate.setText("");
			}
			final int deathDatePid = provider.getDeathDatePid();
			if (deathDatePid > 0) {
				dateProvider.get(deathDatePid);
				textPartnerDeathDate.setText(dateProvider.getDate().toString());
			} else {
				textPartnerDeathDate.setText("");
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}
}
