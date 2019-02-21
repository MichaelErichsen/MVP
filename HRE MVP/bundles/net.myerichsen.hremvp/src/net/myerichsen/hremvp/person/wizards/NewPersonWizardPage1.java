package net.myerichsen.hremvp.person.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.person.dialogs.SexTypeNavigatorDialog;
import net.myerichsen.hremvp.project.providers.SexTypeProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Person static data wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. feb. 2019
 *
 */
/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 14. feb. 2019
 *
 */
public class NewPersonWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Text textBirthDate;
	private Text textBirthDateSort;
	private Text textBirthOriginal;
	private Text textBirthSurety;

	private Text textDeathDate;
	private Text textDeathDateSort;
	private Text textDeathOriginal;
	private Text textDeathSurety;

	private Text textSexTypePid;
	private Text textSex;

	private int BirthDatePid;
	private int DeathDatePid;
	private int sexTypePid;
	private final IEventBroker eventBroker;

	/**
	 * Constructor
	 *
	 * @param context
	 *
	 */
	public NewPersonWizardPage1(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Person");
		setDescription(
				"Create a new person by entering static data for it. Dates are optional, but sex is mandatory.");
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 *
	 */
	private void browseBirthDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(
				textBirthDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textBirthDate.setText(hdp.getDate().toString());
				textBirthDateSort.setText(hdp.getSortDate().toString());
				textBirthOriginal.setText(hdp.getOriginalText());
				textBirthSurety.setText(hdp.getSurety());
			} catch (final Exception e1) {
				LOGGER.severe(e1.getMessage());
//				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void browseDeathDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(
				textDeathDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textDeathDate.setText(hdp.getDate().toString());
				textDeathDateSort.setText(hdp.getSortDate().toString());
				textDeathOriginal.setText(hdp.getOriginalText());
				textDeathSurety.setText(hdp.getSurety());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	protected void browseSexTypes() {
		try {
			final SexTypeNavigatorDialog dialog = new SexTypeNavigatorDialog(
					textSexTypePid.getShell(), context);
			if (dialog.open() == Window.OK) {

				sexTypePid = dialog.getSexTypePid();
				textSexTypePid.setText(Integer.toString(sexTypePid));

				final SexTypeProvider provider = new SexTypeProvider();
				provider.get(sexTypePid);
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	private void clearBirthDate() {
		textBirthDate.setText("");
		textBirthDateSort.setText("");
		textBirthOriginal.setText("");
		textBirthSurety.setText("");
	}

	/**
	 *
	 */
	private void clearDeathDate() {
		textDeathDate.setText("");
		textDeathDateSort.setText("");
		textDeathOriginal.setText("");
		textDeathSurety.setText("");
	}

	/**
	 *
	 */
	protected void clearSex() {
		textSexTypePid.setText("");
		textSex.setText("");
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
		container.setLayout(new GridLayout(3, false));

		final Label lblBirthDate = new Label(container, SWT.NONE);
		lblBirthDate.setText("Birth Date");

		textBirthDate = new Text(container, SWT.BORDER);
		textBirthDate.setEditable(false);
		textBirthDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textBirthDateSort = new Text(container, SWT.BORDER);
		textBirthDateSort.setEditable(false);
		textBirthDateSort.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		textBirthOriginal = new Text(container, SWT.BORDER);
		textBirthOriginal.setEditable(false);
		textBirthOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textBirthSurety = new Text(container, SWT.BORDER);
		textBirthSurety.setEditable(false);
		textBirthSurety.setLayoutData(
				new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Composite compositeBirth = new Composite(container, SWT.NONE);
		compositeBirth.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeBirth.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewBirth = new Button(compositeBirth, SWT.NONE);
		btnNewBirth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewBirthDate();
			}
		});
		btnNewBirth.setText("New");

		final Button btnBrowseBirth = new Button(compositeBirth, SWT.NONE);
		btnBrowseBirth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseBirthDates();
			}
		});
		btnBrowseBirth.setText("Browse");

		final Button btnClearBirth = new Button(compositeBirth, SWT.NONE);
		btnClearBirth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearBirthDate();
			}
		});
		btnClearBirth.setText("Clear");

		final Label lblDeathDate = new Label(container, SWT.NONE);
		lblDeathDate.setText("Death Date");

		textDeathDate = new Text(container, SWT.BORDER);
		textDeathDate.setEditable(false);
		textDeathDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textDeathDateSort = new Text(container, SWT.BORDER);
		textDeathDateSort.setEditable(false);
		textDeathDateSort.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		textDeathOriginal = new Text(container, SWT.BORDER);
		textDeathOriginal.setEditable(false);
		textDeathOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textDeathSurety = new Text(container, SWT.BORDER);
		textDeathSurety.setEditable(false);
		textDeathSurety.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Composite compositeDeath = new Composite(container, SWT.NONE);
		compositeDeath.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeDeath.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		final Button btnNewDeath = new Button(compositeDeath, SWT.NONE);
		btnNewDeath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewDeathDate();
			}
		});
		btnNewDeath.setText("New");

		final Button btnBrowseDeath = new Button(compositeDeath, SWT.NONE);
		btnBrowseDeath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseDeathDates();
			}
		});
		btnBrowseDeath.setText("Browse");

		final Button btnClearDeath = new Button(compositeDeath, SWT.NONE);
		btnClearDeath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearDeathDate();
			}
		});
		btnClearDeath.setText("Clear");

		final Label lblSex = new Label(container, SWT.NONE);
		lblSex.setText("Sex");

		textSexTypePid = new Text(container, SWT.BORDER);
		textSexTypePid.setToolTipText("More sexes can be added later");
		textSexTypePid.setEditable(false);
		textSexTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textSexTypePid.setToolTipText("Sex must be selected to continue");

		textSex = new Text(container, SWT.BORDER);
		textSex.setEditable(false);
		textSex.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Composite compositeSex = new Composite(container, SWT.NONE);
		compositeSex.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeSex.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		final Button btnBrowseSexes = new Button(compositeSex, SWT.NONE);
		btnBrowseSexes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseSexTypes();
				setPageComplete(true);
			}
		});
		btnBrowseSexes.setText("Browse");

		final Button btnClearSex = new Button(compositeSex, SWT.NONE);
		btnClearSex.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearSex();
				setPageComplete(false);
			}
		});
		btnClearSex.setText("Clear");

		setPageComplete(false);
	}

	/**
	 * @return the BirthDatePid
	 */
	public int getBirthDatePid() {
		return BirthDatePid;
	}

	/**
	 * @return the DeathDatePid
	 */
	public int getDeathDatePid() {
		return DeathDatePid;
	}

	/**
	 *
	 */
	private void getNewBirthDate() {
		final DateDialog dialog = new DateDialog(textBirthDate.getShell(),
				context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getLocalDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				BirthDatePid = hdp.insert();
				textBirthDate.setText(dialog.getLocalDate().toString());
				if (textBirthDateSort.getText().length() == 0) {
					textBirthDateSort.setText(dialog.getSortDate().toString());
				}
				textBirthOriginal.setText(dialog.getOriginal());
				textBirthSurety.setText(dialog.getSurety());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void getNewDeathDate() {
		final DateDialog dialog = new DateDialog(textDeathDate.getShell(),
				context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getLocalDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				DeathDatePid = hdp.insert();
				textDeathDate.setText(dialog.getLocalDate().toString());
				textDeathDateSort.setText(dialog.getSortDate().toString());
				textDeathOriginal.setText(dialog.getOriginal());
				textDeathSurety.setText(dialog.getSurety());
			} catch (final Exception e1) {
				LOGGER.severe(e1.getMessage());
			}
		}
	}

	/**
	 * @return the sexTypePid
	 */
	public int getSexTypePid() {
		return sexTypePid;
	}

	/**
	 * @param BirthDatePid the BirthDatePid to set
	 */
	public void setBirthDatePid(int BirthDatePid) {
		this.BirthDatePid = BirthDatePid;
	}

	/**
	 * @param DeathDatePid the DeathDatePid to set
	 */
	public void setDeathDatePid(int DeathDatePid) {
		this.DeathDatePid = DeathDatePid;
	}

	/**
	 * @param sexPid The persistent id of the sex type
	 */
	public void setSexTypePid(int sexPid) {
		sexTypePid = sexPid;
	}
}
