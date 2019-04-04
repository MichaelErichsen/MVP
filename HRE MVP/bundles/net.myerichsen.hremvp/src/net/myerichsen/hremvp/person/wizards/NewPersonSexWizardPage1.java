package net.myerichsen.hremvp.person.wizards;

import java.util.logging.Level;
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
 * Person sex wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 16. mar. 2019
 *
 */
public class NewPersonSexWizardPage1 extends WizardPage {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private Text textSexTypePid;
	private Text textSex;
	private int sexTypePid;
	private Button btnCheckButtonPrimary;
	private Text textFromDate;
	private Text textToDate;
	private int fromDatePid;
	private int toDatePid;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonSexWizardPage1(IEclipseContext context) {
		super("New Person Sex Wizard Page 1");
		setTitle("Sex");
		setDescription("Add a sex for the person.");
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 *
	 */
	private void browseFromDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(
				textFromDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textFromDate.setText(hdp.getDate().toString());
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
			LOGGER.log(Level.SEVERE, e.toString(), e);
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	private void browseToDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(
				textToDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textToDate.setText(hdp.getDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void clearFromDate() {
		textFromDate.setText("");
	}

	/**
	 *
	 */
	protected void clearSex() {
		textSexTypePid.setText("");
		textSex.setText("");
	}

	/**
	 *
	 */
	private void clearToDate() {
		textToDate.setText("");
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		final Label lblSex = new Label(container, SWT.NONE);
		lblSex.setText("Sex");

		textSexTypePid = new Text(container, SWT.BORDER);
		textSexTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textSexTypePid.setToolTipText("More sexes can be added later");
		textSexTypePid.setEditable(false);
		textSexTypePid.setToolTipText("Sex must be selected to continue");

		textSex = new Text(container, SWT.BORDER);
		textSex.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textSex.setEditable(false);
		new Label(container, SWT.NONE);

		final Composite compositeSex = new Composite(container, SWT.NONE);
		compositeSex.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeSex.setLayout(new RowLayout(SWT.HORIZONTAL));

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
		new Label(container, SWT.NONE);

		btnCheckButtonPrimary = new Button(container, SWT.CHECK);
		btnCheckButtonPrimary.setText("Primary");
		new Label(container, SWT.NONE);

		final Label lblFromDate = new Label(container, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(container, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFrom = new Composite(container, SWT.NONE);
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewFrom = new Button(compositeFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewFromDate();
			}
		});
		btnNewFrom.setText("New");

		final Button btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseFromDates();
			}
		});
		btnBrowseFrom.setText("Browse");

		final Button btnClearFrom = new Button(compositeFrom, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearFromDate();
			}
		});
		btnClearFrom.setText("Clear");

		final Label lblToDate = new Label(container, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(container, SWT.BORDER);
		textToDate.setEditable(false);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeTo = new Composite(container, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewTo = new Button(compositeTo, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewToDate();
			}
		});
		btnNewTo.setText("New");

		final Button btnBrowseTo = new Button(compositeTo, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseToDates();
			}
		});
		btnBrowseTo.setText("Browse");

		final Button btnClearTo = new Button(compositeTo, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearToDate();
			}
		});
		btnClearTo.setText("Clear");

		setPageComplete(false);
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 *
	 */
	private void getNewFromDate() {
		final DateDialog dialog = new DateDialog(textFromDate.getShell(),
				context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				setFromDatePid(hdp.insert());
				textFromDate.setText(dialog.getDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void getNewToDate() {
		final DateDialog dialog = new DateDialog(textToDate.getShell(),
				context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				setToDatePid(hdp.insert());
				textToDate.setText(dialog.getDate().toString());
			} catch (final Exception e1) {
				LOGGER.log(Level.SEVERE, e1.toString(), e1);
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
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * @return the btnCheckButtonPrimary
	 */
	public boolean isPrimary() {
		return btnCheckButtonPrimary.getSelection();
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param sexPid The persistent id of the sex type
	 */
	public void setSexTypePid(int sexPid) {
		sexTypePid = sexPid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}

}
