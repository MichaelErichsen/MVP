package net.myerichsen.hremvp.person.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
 * @version 28. mar. 2019
 *
 */
// FIXME Populate combo boxes
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

	private Text textMotherPersonPid;
	private Text textMotherName;
	private Text textMotherBirthDate;
	private Text textMotherDeathDate;

	private Text textChildPersonPid;
	private Text textChildName;
	private Text textChildBirthDate;
	private Text textChildDeathDate;

	private Text textPartnerPersonPid;
	private Text textPartnerName;
	private Text textPartnerBirthDate;
	private Text textPartnerDeathDate;
	private Text textPartnershipStartDate;

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
	private Text textPartnershipEndDate;

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
		container.setLayout(new GridLayout(1, false));

		Composite compositeFather = new Composite(container, SWT.BORDER);
		compositeFather.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeFather.setLayout(new GridLayout(3, false));

		final Label lblFather = new Label(compositeFather, SWT.NONE);
		lblFather.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblFather.setText("Father");

		textFatherPersonPid = new Text(compositeFather, SWT.BORDER);
		textFatherPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeFatherButtons = new Composite(compositeFather,
				SWT.NONE);
		compositeFatherButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateFather = new Button(compositeFatherButtons,
				SWT.NONE);
		btnUpdateFather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateFather();
			}
		});
		btnUpdateFather.setText("Update");

		final Button btnBrowseFather = new Button(compositeFatherButtons,
				SWT.NONE);
		btnBrowseFather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseFather();
			}
		});
		btnBrowseFather.setText("Browse");

		final Button btnClearFather = new Button(compositeFatherButtons,
				SWT.NONE);
		btnClearFather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearFather();
			}
		});
		btnClearFather.setText("Clear");

		textFatherName = new Text(compositeFather, SWT.BORDER);
		textFatherName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textFatherName.setEditable(false);

		textFatherBirthDate = new Text(compositeFather, SWT.BORDER);
		textFatherBirthDate.setEditable(false);

		textFatherDeathDate = new Text(compositeFather, SWT.BORDER);
		textFatherDeathDate.setEditable(false);

		final Label lblFatherRole = new Label(compositeFather, SWT.NONE);
		lblFatherRole.setText("Father role");

		ComboViewer comboViewerFatherRole = new ComboViewer(compositeFather,
				SWT.NONE);
		Combo comboFatherRole = comboViewerFatherRole.getCombo();
		comboFatherRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Composite compositeMother = new Composite(container, SWT.NONE);
		compositeMother.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeMother.setLayout(new GridLayout(3, false));

		final Label lblMother = new Label(compositeMother, SWT.NONE);
		lblMother.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblMother.setText("Mother");

		textMotherPersonPid = new Text(compositeMother, SWT.BORDER);
		textMotherPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeMotherButtons = new Composite(compositeMother,
				SWT.NONE);
		compositeMotherButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateMother = new Button(compositeMotherButtons,
				SWT.NONE);
		btnUpdateMother.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateMother();
			}
		});
		btnUpdateMother.setText("Update");

		final Button btnBrowseMother = new Button(compositeMotherButtons,
				SWT.NONE);
		btnBrowseMother.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseMother();
			}
		});
		btnBrowseMother.setText("Browse");

		final Button btnClearMother = new Button(compositeMotherButtons,
				SWT.NONE);
		btnClearMother.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearMother();
			}
		});
		btnClearMother.setText("Clear");

		textMotherName = new Text(compositeMother, SWT.BORDER);
		textMotherName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textMotherName.setEditable(false);

		textMotherBirthDate = new Text(compositeMother, SWT.BORDER);
		textMotherBirthDate.setEditable(false);

		textMotherDeathDate = new Text(compositeMother, SWT.BORDER);
		textMotherDeathDate.setEditable(false);

		final Label lblMotherRole = new Label(compositeMother, SWT.NONE);
		lblMotherRole.setText("Mother role");

		ComboViewer comboViewerMotherRole = new ComboViewer(compositeMother,
				SWT.NONE);
		Combo comboMotherRole = comboViewerMotherRole.getCombo();
		comboMotherRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Composite compositeChild = new Composite(container, SWT.NONE);
		compositeChild.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeChild.setLayout(new GridLayout(3, false));

		final Label lblChild = new Label(compositeChild, SWT.NONE);
		lblChild.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblChild.setText("Child");

		textChildPersonPid = new Text(compositeChild, SWT.BORDER);
		textChildPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeChildButtons = new Composite(compositeChild,
				SWT.NONE);
		compositeChildButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateChild = new Button(compositeChildButtons,
				SWT.NONE);
		btnUpdateChild.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateChild();
			}
		});
		btnUpdateChild.setText("Update");

		final Button btnBrowseChild = new Button(compositeChildButtons,
				SWT.NONE);
		btnBrowseChild.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseChild();
			}
		});
		btnBrowseChild.setText("Browse");

		final Button btnClearChild = new Button(compositeChildButtons,
				SWT.NONE);
		btnClearChild.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearChild();
			}
		});
		btnClearChild.setText("Clear");

		textChildName = new Text(compositeChild, SWT.BORDER);
		textChildName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textChildName.setEditable(false);

		textChildBirthDate = new Text(compositeChild, SWT.BORDER);
		textChildBirthDate.setEditable(false);

		textChildDeathDate = new Text(compositeChild, SWT.BORDER);
		textChildDeathDate.setEditable(false);

		final Label lblChildRole = new Label(compositeChild, SWT.NONE);
		lblChildRole.setText("Child role");

		ComboViewer comboViewerChildRole = new ComboViewer(compositeChild,
				SWT.NONE);
		Combo comboChildRole = comboViewerChildRole.getCombo();
		comboChildRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Composite compositePartner = new Composite(container, SWT.BORDER);
		compositePartner.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositePartner.setLayout(new GridLayout(3, false));

		final Label lblPartner = new Label(compositePartner, SWT.NONE);
		lblPartner
				.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPartner.setText("Partner");

		textPartnerPersonPid = new Text(compositePartner, SWT.BORDER);

		final Composite compositePartnerStartButtons = new Composite(
				compositePartner, SWT.NONE);
		compositePartnerStartButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdatePartnerStart = new Button(
				compositePartnerStartButtons, SWT.NONE);
		btnUpdatePartnerStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updatePartner();
			}
		});
		btnUpdatePartnerStart.setText("Update");

		final Button btnBrowsePartner = new Button(compositePartnerStartButtons,
				SWT.NONE);
		btnBrowsePartner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browsePartner();
			}
		});
		btnBrowsePartner.setText("Browse");

		final Button btnClearPartner = new Button(compositePartnerStartButtons,
				SWT.NONE);
		btnClearPartner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearPartner();
			}
		});
		btnClearPartner.setText("Clear");

		textPartnerName = new Text(compositePartner, SWT.BORDER);
		textPartnerName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textPartnerName.setEditable(false);

		textPartnerBirthDate = new Text(compositePartner, SWT.BORDER);
		textPartnerBirthDate.setEditable(false);

		textPartnerDeathDate = new Text(compositePartner, SWT.BORDER);
		textPartnerDeathDate.setEditable(false);

		final Label lblStartOfPartnership = new Label(compositePartner,
				SWT.NONE);
		lblStartOfPartnership.setText("Start of partnership");

		textPartnershipStartDate = new Text(compositePartner, SWT.BORDER);
		textPartnershipStartDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textPartnershipStartDate.setEditable(false);

		final Composite compositePartnerStart = new Composite(compositePartner,
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

		final Label lblEndOfPartnership = new Label(compositePartner, SWT.NONE);
		lblEndOfPartnership.setText("End of partnership");

		textPartnershipEndDate = new Text(compositePartner, SWT.BORDER);
		textPartnershipEndDate.setEditable(false);
		textPartnershipEndDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositePartnerEndButtons = new Composite(
				compositePartner, SWT.NONE);
		compositePartnerEndButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonNewPartnerEnd = new Button(
				compositePartnerEndButtons, SWT.NONE);
		buttonNewPartnerEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				newPartnerEnd();
			}
		});
		buttonNewPartnerEnd.setText("New");

		final Button buttonBrowsePartnerEnd = new Button(
				compositePartnerEndButtons, SWT.NONE);
		buttonBrowsePartnerEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browsePartnerEnd();
			}
		});
		buttonBrowsePartnerEnd.setText("Browse");

		final Button buttonClearBrowserEnd = new Button(
				compositePartnerEndButtons, SWT.NONE);
		buttonClearBrowserEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearPartnerEnd();
			}
		});
		buttonClearBrowserEnd.setText("Clear");

		Label lblPartnerRole = new Label(compositePartner, SWT.NONE);
		lblPartnerRole.setText("Partner role");

		ComboViewer comboViewerPartnerRole = new ComboViewer(compositePartner,
				SWT.NONE);
		Combo comboPartnerRole = comboViewerPartnerRole.getCombo();
		comboPartnerRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

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
		} catch (final Exception e) {
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
		} catch (final Exception e) {
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
		} catch (final Exception e) {
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
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}
}
