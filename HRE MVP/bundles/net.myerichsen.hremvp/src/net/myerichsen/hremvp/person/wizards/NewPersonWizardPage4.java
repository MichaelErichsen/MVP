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
 * Person parents, partner and child wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 16. mar. 2019
 *
 */
public class NewPersonWizardPage4 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private Shell parentShell;

	private Text textFatherPersonPid;
	private Text textFatherName;
	private Text textFatherBirthDate;
	private Text textFatherDeathDate;
	private Text textFatherRole;

	private Text textMotherPersonPid;
	private Text textMotherName;
	private Text textMotherBirthDate;
	private Text textMotherDeathDate;
	private Text textMotherRole;

	private Text textChildPersonPid;
	private Text textChildName;
	private Text textChildBirthDate;
	private Text textChildDeathDate;
	private Text textChildRole;

	private Text textPartnerPersonPid;
	private Text textPartnerName;
	private Text textPartnerBirthDate;
	private Text textPartnerDeathDate;
	private Text textPartnerRole;
	private Text textPartnershipStartDate;
	private Text textPartnershipEndDate;

	private int fatherPid = 0;
	private int motherPid = 0;
	private int partnerPid = 0;
	private int childPid = 0;
	private String fatherRole = "";
	private String motherRole = "";
	private String childRole = "";
	private String partnerRole = "";
	private int partnerFromDatePid = 0;
	private int partnerToDatePid = 0;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonWizardPage4(IEclipseContext context) {
		super("New Person Wizard Page 4");
		setTitle("Person Primary Parents and Partner");
		setDescription(
				"Add primary parents, partner and child for the new person. More parents and partners can be added later.");
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 *
	 */
	protected void browseChild() {
		final PersonNavigatorDialog dialog = new PersonNavigatorDialog(
				parentShell, context);
		if (dialog.open() == Window.OK) {
			try {
				childPid = dialog.getPersonPid();
				textChildPersonPid.setText(Integer.toString(childPid));
				textChildName.setText(dialog.getPersonName());
				textChildBirthDate.setText(dialog.getBirthDate());
				textChildDeathDate.setText(dialog.getDeathDate());
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	protected void browseFather() {
		final PersonNavigatorDialog dialog = new PersonNavigatorDialog(
				parentShell, context);
		if (dialog.open() == Window.OK) {
			try {
				fatherPid = dialog.getPersonPid();
				textFatherPersonPid.setText(Integer.toString(fatherPid));
				textFatherName.setText(dialog.getPersonName());
				textFatherBirthDate.setText(dialog.getBirthDate());
				textFatherDeathDate.setText(dialog.getDeathDate());
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	protected void browseMother() {
		final PersonNavigatorDialog dialog = new PersonNavigatorDialog(
				parentShell, context);
		if (dialog.open() == Window.OK) {
			try {
				motherPid = dialog.getPersonPid();
				textMotherPersonPid.setText(Integer.toString(motherPid));
				textMotherName.setText(dialog.getPersonName());
				textMotherBirthDate.setText(dialog.getBirthDate());
				textMotherDeathDate.setText(dialog.getDeathDate());
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
			}
		}
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
				final HDateProvider hdp = new HDateProvider();
				hdp.get(partnerToDatePid);
				textPartnershipEndDate.setText(hdp.getDate().toString());
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
				final HDateProvider hdp = new HDateProvider();
				hdp.get(partnerFromDatePid);
				textPartnershipStartDate.setText(hdp.getDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 *
	 */
	protected void clearChild() {
		childPid = 0;
		textChildPersonPid.setText("");
		textChildName.setText("");
		textChildBirthDate.setText("");
		textChildDeathDate.setText("");
	}

	/**
	 *
	 */
	protected void clearFather() {
		fatherPid = 0;
		textFatherPersonPid.setText("");
		textFatherName.setText("");
		textFatherBirthDate.setText("");
		textFatherDeathDate.setText("");
	}

	/**
	 *
	 */
	protected void clearMother() {
		motherPid = 0;
		textMotherPersonPid.setText("");
		textMotherName.setText("");
		textMotherBirthDate.setText("");
		textMotherDeathDate.setText("");
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

		final Label lblFather = new Label(container, SWT.NONE);
		lblFather.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblFather.setText("Father");

		textFatherPersonPid = new Text(container, SWT.BORDER);
		textFatherPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFather = new Composite(container, SWT.NONE);
		compositeFather.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateFather = new Button(compositeFather, SWT.NONE);
		btnUpdateFather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateFather();
			}
		});
		btnUpdateFather.setText("Update");

		final Button btnBrowseFather = new Button(compositeFather, SWT.NONE);
		btnBrowseFather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseFather();
			}
		});
		btnBrowseFather.setText("Browse");

		final Button btnClearFather = new Button(compositeFather, SWT.NONE);
		btnClearFather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearFather();
			}
		});
		btnClearFather.setText("Clear");

		textFatherName = new Text(container, SWT.BORDER);
		textFatherName.setEditable(false);
		textFatherName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textFatherBirthDate = new Text(container, SWT.BORDER);
		textFatherBirthDate.setEditable(false);
		textFatherBirthDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textFatherDeathDate = new Text(container, SWT.BORDER);
		textFatherDeathDate.setEditable(false);
		textFatherDeathDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Label lblFatherRole = new Label(container, SWT.NONE);
		lblFatherRole.setText("Father role");

		textFatherRole = new Text(container, SWT.BORDER);
		textFatherRole.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				fatherRole = textFatherRole.getText();
			}
		});
		textFatherRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Label lblMother = new Label(container, SWT.NONE);
		lblMother.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblMother.setText("Mother");

		textMotherPersonPid = new Text(container, SWT.BORDER);
		textMotherPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeMother = new Composite(container, SWT.NONE);
		compositeMother.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateMother = new Button(compositeMother, SWT.NONE);
		btnUpdateMother.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateMother();
			}
		});
		btnUpdateMother.setText("Update");

		final Button btnBrowseMother = new Button(compositeMother, SWT.NONE);
		btnBrowseMother.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseMother();
			}
		});
		btnBrowseMother.setText("Browse");

		final Button btnClearMother = new Button(compositeMother, SWT.NONE);
		btnClearMother.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearMother();
			}
		});
		btnClearMother.setText("Clear");

		textMotherName = new Text(container, SWT.BORDER);
		textMotherName.setEditable(false);
		textMotherName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textMotherBirthDate = new Text(container, SWT.BORDER);
		textMotherBirthDate.setEditable(false);
		textMotherBirthDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textMotherDeathDate = new Text(container, SWT.BORDER);
		textMotherDeathDate.setEditable(false);
		textMotherDeathDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Label lblMotherRole = new Label(container, SWT.NONE);
		lblMotherRole.setText("Mother role");

		textMotherRole = new Text(container, SWT.BORDER);
		textMotherRole.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				motherRole = textMotherRole.getText();
			}
		});
		textMotherRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblChild = new Label(container, SWT.NONE);
		lblChild.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblChild.setText("Child");

		textChildPersonPid = new Text(container, SWT.BORDER);
		textChildPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeChild = new Composite(container, SWT.NONE);
		compositeChild.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateChild = new Button(compositeChild, SWT.NONE);
		btnUpdateChild.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateChild();
			}
		});
		btnUpdateChild.setText("Update");

		final Button btnBrowseChild = new Button(compositeChild, SWT.NONE);
		btnBrowseChild.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseChild();
			}
		});
		btnBrowseChild.setText("Browse");

		final Button btnClearChild = new Button(compositeChild, SWT.NONE);
		btnClearChild.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearChild();
			}
		});
		btnClearChild.setText("Clear");

		textChildName = new Text(container, SWT.BORDER);
		textChildName.setEditable(false);
		textChildName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textChildBirthDate = new Text(container, SWT.BORDER);
		textChildBirthDate.setEditable(false);
		textChildBirthDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textChildDeathDate = new Text(container, SWT.BORDER);
		textChildDeathDate.setEditable(false);
		textChildDeathDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		final Label lblChildRole = new Label(container, SWT.NONE);
		lblChildRole.setText("Child role");

		textChildRole = new Text(container, SWT.BORDER);
		textChildRole.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				childRole = textChildRole.getText();
			}
		});
		textChildRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

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
	 * @return the childPid
	 */
	public int getChildPid() {
		return childPid;
	}

	/**
	 * @return the childRole
	 */
	public String getChildRole() {
		return childRole;
	}

	/**
	 * @return the fatherPid
	 */
	public int getFatherPid() {
		return fatherPid;
	}

	/**
	 * @return the fatherRole
	 */
	public String getFatherRole() {
		return fatherRole;
	}

	/**
	 * @return the motherPid
	 */
	public int getMotherPid() {
		return motherPid;
	}

	/**
	 * @return the motherRole
	 */
	public String getMotherRole() {
		return motherRole;
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
	protected void updateChild() {
		childPid = Integer.parseInt(textChildPersonPid.getText());
		PersonProvider provider;

		try {
			final HDateProvider dateProvider = new HDateProvider();
			provider = new PersonProvider();
			provider.get(childPid);
			textChildName.setText(provider.getPrimaryName());
			dateProvider.get(provider.getBirthDatePid());
			textChildBirthDate.setText(dateProvider.getDate().toString());
			dateProvider.get(provider.getDeathDatePid());
			textChildDeathDate.setText(dateProvider.getDate().toString());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void updateFather() {
		fatherPid = Integer.parseInt(textFatherPersonPid.getText());
		PersonProvider provider;

		try {
			final HDateProvider dateProvider = new HDateProvider();
			provider = new PersonProvider();
			provider.get(fatherPid);
			textFatherName.setText(provider.getPrimaryName());
			dateProvider.get(provider.getBirthDatePid());
			textFatherBirthDate.setText(dateProvider.getDate().toString());
			dateProvider.get(provider.getDeathDatePid());
			textFatherDeathDate.setText(dateProvider.getDate().toString());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void updateMother() {
		motherPid = Integer.parseInt(textMotherPersonPid.getText());
		PersonProvider provider;

		try {
			final HDateProvider dateProvider = new HDateProvider();
			provider = new PersonProvider();
			provider.get(motherPid);
			textMotherName.setText(provider.getPrimaryName());
			dateProvider.get(provider.getBirthDatePid());
			textMotherBirthDate.setText(dateProvider.getDate().toString());
			dateProvider.get(provider.getDeathDatePid());
			textMotherDeathDate.setText(dateProvider.getDate().toString());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
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
			dateProvider.get(provider.getBirthDatePid());
			textPartnerBirthDate.setText(dateProvider.getDate().toString());
			dateProvider.get(provider.getDeathDatePid());
			textPartnerDeathDate.setText(dateProvider.getDate().toString());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}
}
