package net.myerichsen.hremvp.person.wizards;

import java.util.ArrayList;
import java.util.List;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Person events wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 18. feb. 2019
 *
 */
public class NewPersonEventWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Table table;

//	private final IEventBroker eventBroker;
	private List<List<String>> listOfLists;
	private Text textEventNamePid;
	private Text textEventName;
	private Text textLanguagePid;
	private Text textIsoCode;
	private Text textLanguage;
	private Text textEventTypePid;
	private Text textEventType;
	private Text textFromDatePid;
	private Text textFromDate;
	private Text textFromOriginal;
	private Text textToDatePid;
	private Button btnBrowseTo;
	private Text textToDate;
	private Text textToOriginal;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonEventWizardPage1(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Events");
		setDescription("Add an event.");
		this.context = context;
//		eventBroker = context.get(IEventBroker.class);
		listOfLists = new ArrayList<>();
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		final Label lblLabel = new Label(container, SWT.NONE);
		lblLabel.setText("Event Name");

		textEventNamePid = new Text(container, SWT.BORDER);
		textEventNamePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textEventName = new Text(container, SWT.BORDER);
		textEventName.setEditable(false);
		textEventName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblLanguage = new Label(container, SWT.NONE);
		lblLanguage.setText("Language");

		textLanguagePid = new Text(container, SWT.BORDER);
		textLanguagePid.setEditable(false);
		textLanguagePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textIsoCode = new Text(container, SWT.BORDER);
		textIsoCode.setEditable(false);
		textIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		textLanguage = new Text(container, SWT.BORDER);
		textLanguage.setEditable(false);
		textLanguage.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblEventType = new Label(container, SWT.NONE);
		lblEventType.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEventType.setText("Event Type");

		textEventTypePid = new Text(container, SWT.BORDER);
		textEventTypePid.setEditable(false);
		textEventTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textEventType = new Text(container, SWT.BORDER);
		textEventType.setEditable(false);
		textEventType.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblFromDate = new Label(container, SWT.NONE);
		lblFromDate.setText("From Date ID");

		textFromDatePid = new Text(container, SWT.BORDER);
		textFromDatePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeFrom = new Composite(container, SWT.NONE);
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewFrom = new Button(compositeFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						hdp.insert();
						textFromDate.setText(dialog.getLocalDate().toString());
						textFromOriginal.setText(dialog.getOriginal());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewFrom.setText("New");

		final Button btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textFromDate.setText(hdp.getDate().toString());
						textFromOriginal.setText(hdp.getOriginalText());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseFrom.setText("Browse");

		final Button btnClearFrom = new Button(compositeFrom, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textFromDate.setText("");
				textFromOriginal.setText("");
			}
		});
		btnClearFrom.setText("Clear");

		textFromDate = new Text(container, SWT.BORDER);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textFromDate.setEditable(false);
		new Label(container, SWT.NONE);

		textFromOriginal = new Text(container, SWT.BORDER);
		textFromOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		textFromOriginal.setEditable(false);

		final Label lblToDate = new Label(container, SWT.NONE);
		lblToDate.setText("To Date ID");

		textToDatePid = new Text(container, SWT.BORDER);
		textToDatePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeTo = new Composite(container, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnCopyFromTo = new Button(compositeTo, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText(textFromDate.getText());
				textToOriginal.setText(textFromOriginal.getText());
			}
		});
		btnCopyFromTo.setText("Copy From");

		final Button btnNewTo = new Button(compositeTo, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textToDate.getShell(),
						context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						hdp.insert();
						textToDate.setText(dialog.getLocalDate().toString());
						textToOriginal.setText(dialog.getOriginal());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewTo.setText("New");

		btnBrowseTo = new Button(compositeTo, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textToDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textToDate.setText(hdp.getDate().toString());
						textToOriginal.setText(hdp.getOriginalText());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseTo.setText("Browse");

		final Button btnClearTo = new Button(compositeTo, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText("");
				textToOriginal.setText("");
			}
		});
		btnClearTo.setText("Clear");

		textToDate = new Text(container, SWT.BORDER);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textToDate.setEditable(false);

		textToOriginal = new Text(container, SWT.BORDER);
		textToOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		textToOriginal.setEditable(false);

	}

	/**
	 *
	 */
	protected void deleteSelectedEvent() {
		final int selectionIndex = table.getSelectionIndex();
		table.remove(selectionIndex);
		listOfLists.remove(selectionIndex);
	}

	/**
	 * @return
	 */
	public int getEventPid() {
// FIXME
		return 0;
	}

	/**
	 * @return the listOfLists
	 */
	public List<List<String>> getListOfLists() {
		return listOfLists;
	}

	/**
	 * @param listOfLists the listOfLists to set
	 */
	public void setListOfLists(List<List<String>> listOfLists) {
		this.listOfLists = listOfLists;
	}
}
