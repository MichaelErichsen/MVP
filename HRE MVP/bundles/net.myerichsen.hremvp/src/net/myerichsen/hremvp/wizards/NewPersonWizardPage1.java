package net.myerichsen.hremvp.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
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
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Base person data wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 12. jan. 2019
 *
 */
public class NewPersonWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Text textBirthDate;
	private Text textBirthDateSort;
	private Text textBirthOriginal;
	private Text textBirthSurety;
	private Button btnNewBirth;
	private Button btnBrowseBirth;
	private Button btnClearBirth;

	private Text textDeathDate;
	private Text textDeathDateSort;
	private Text textDeathOriginal;
	private Text textDeathSurety;
	private Button btnNewDeath;
	private Button btnBrowseDeath;
	private Button btnClearDeath;

	private int BirthDatePid;
	private int DeathDatePid;

	/**
	 * Constructor
	 *
	 * @param context
	 *
	 */
	public NewPersonWizardPage1(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Person");
		setDescription("Create a new person by entering static data for it.");
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.
	 * Composite)
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
		textBirthDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textBirthDateSort = new Text(container, SWT.BORDER);
		textBirthDateSort.setEditable(false);
		textBirthDateSort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		textBirthOriginal = new Text(container, SWT.BORDER);
		textBirthOriginal.setEditable(false);
		textBirthOriginal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textBirthSurety = new Text(container, SWT.BORDER);
		textBirthSurety.setEditable(false);
		textBirthSurety.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Composite compositeFrom = new Composite(container, SWT.NONE);
		compositeFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnNewBirth = new Button(compositeFrom, SWT.NONE);
		btnNewBirth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textBirthDate.getShell(), context);
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
		});
		btnNewBirth.setText("New");

		btnBrowseBirth = new Button(compositeFrom, SWT.NONE);
		btnBrowseBirth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(textBirthDate.getShell(), context);
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
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseBirth.setText("Browse");

		btnClearBirth = new Button(compositeFrom, SWT.NONE);
		btnClearBirth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textBirthDate.setText("");
				textBirthDateSort.setText("");
				textBirthOriginal.setText("");
				textBirthSurety.setText("");
			}
		});
		btnClearBirth.setText("Clear");

		final Label lblDeathDate = new Label(container, SWT.NONE);
		lblDeathDate.setText("Death Date");

		textDeathDate = new Text(container, SWT.BORDER);
		textDeathDate.setEditable(false);
		textDeathDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textDeathDateSort = new Text(container, SWT.BORDER);
		textDeathDateSort.setEditable(false);
		textDeathDateSort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		textDeathOriginal = new Text(container, SWT.BORDER);
		textDeathOriginal.setEditable(false);
		textDeathOriginal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textDeathSurety = new Text(container, SWT.BORDER);
		textDeathSurety.setEditable(false);
		textDeathSurety.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Composite compositeTo = new Composite(container, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		btnNewDeath = new Button(compositeTo, SWT.NONE);
		btnNewDeath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textDeathDate.getShell(), context);
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
		});
		btnNewDeath.setText("New");

		btnBrowseDeath = new Button(compositeTo, SWT.NONE);
		btnBrowseDeath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(textDeathDate.getShell(), context);
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
		});
		btnBrowseDeath.setText("Browse");

		btnClearDeath = new Button(compositeTo, SWT.NONE);
		btnClearDeath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textDeathDate.setText("");
				textDeathDateSort.setText("");
				textDeathOriginal.setText("");
				textDeathSurety.setText("");
			}
		});
		btnClearDeath.setText("Clear");
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
}
