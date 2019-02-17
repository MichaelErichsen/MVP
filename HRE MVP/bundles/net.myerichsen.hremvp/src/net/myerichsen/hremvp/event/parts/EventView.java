package net.myerichsen.hremvp.event.parts;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.event.providers.EventProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Display all data about an personEvent
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. dec. 2018
 */
public class EventView {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private IEventBroker eventBroker;

	private ScrolledComposite scrolledComposite;
	private Composite composite_1;

	private Text textId;
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
	private Button btnNewFrom;
	private Button btnBrowseFrom;
	private Button btnClearFrom;

	private Text textToDatePid;
	private Text textToDate;
	private Text textToOriginal;
	private Button btnCopyFromTo;
	private Button btnNewTo;
	private Button btnBrowseTo;
	private Button btnClearTo;

	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private final EventProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public EventView() throws SQLException {
		provider = new EventProvider();
	}

	/**
	 *
	 */
	protected void clear() {
		textId.setText("");
		textEventNamePid.setText("");
		textEventName.setText("");
		textLanguagePid.setText("");
		textIsoCode.setText("");
		textLanguage.setText("");
		textEventNamePid.setText("");
		textEventType.setText("");
		textFromDatePid.setText("");
		textFromDate.setText("");
		textFromOriginal.setText("");
		textToDatePid.setText("");
		textToDate.setText("");
		textToOriginal.setText("");
	}

	/**
	 *
	 */
	protected void close() {
		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		final MPart part = (MPart) stacks.get(stacks.size() - 2)
				.getSelectedElement();
		partService.hidePart(part, true);
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(1, false));

		scrolledComposite = new ScrolledComposite(parent,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		composite_1.setLayout(new GridLayout(3, false));

		final Label lblId = new Label(composite_1, SWT.NONE);
		lblId.setText("Event ID");

		textId = new Text(composite_1, SWT.BORDER);
		textId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite_1, SWT.NONE);

		final Label lblLabel = new Label(composite_1, SWT.NONE);
		lblLabel.setText("Event Name");

		textEventNamePid = new Text(composite_1, SWT.BORDER);
		textEventNamePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textEventName = new Text(composite_1, SWT.BORDER);
		textEventName.setEditable(false);
		textEventName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblLanguage = new Label(composite_1, SWT.NONE);
		lblLanguage.setText("Language");

		textLanguagePid = new Text(composite_1, SWT.BORDER);
		textLanguagePid.setEditable(false);
		textLanguagePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textIsoCode = new Text(composite_1, SWT.BORDER);
		textIsoCode.setEditable(false);
		textIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_1, SWT.NONE);

		textLanguage = new Text(composite_1, SWT.BORDER);
		textLanguage.setEditable(false);
		textLanguage.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblEventType = new Label(composite_1, SWT.NONE);
		lblEventType.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEventType.setText("Event Type");

		textEventTypePid = new Text(composite_1, SWT.BORDER);
		textEventTypePid.setEditable(false);
		textEventTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textEventType = new Text(composite_1, SWT.BORDER);
		textEventType.setEditable(false);
		textEventType.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblFromDate = new Label(composite_1, SWT.NONE);
		lblFromDate.setText("From Date ID");

		textFromDatePid = new Text(composite_1, SWT.BORDER);
		textFromDatePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeFrom = new Composite(composite_1, SWT.NONE);
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnNewFrom = new Button(compositeFrom, SWT.NONE);
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

		btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
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

		btnClearFrom = new Button(compositeFrom, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textFromDate.setText("");
				textFromOriginal.setText("");
			}
		});
		btnClearFrom.setText("Clear");

		textFromDate = new Text(composite_1, SWT.BORDER);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textFromDate.setEditable(false);

		textFromOriginal = new Text(composite_1, SWT.BORDER);
		textFromOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		textFromOriginal.setEditable(false);

		final Label lblToDate = new Label(composite_1, SWT.NONE);
		lblToDate.setText("To Date ID");

		textToDatePid = new Text(composite_1, SWT.BORDER);
		textToDatePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeTo = new Composite(composite_1, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnCopyFromTo = new Button(compositeTo, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText(textFromDate.getText());
				textToOriginal.setText(textFromOriginal.getText());
			}
		});
		btnCopyFromTo.setText("Copy From");

		btnNewTo = new Button(compositeTo, SWT.NONE);
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

		btnClearTo = new Button(compositeTo, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText("");
				textToOriginal.setText("");
			}
		});
		btnClearTo.setText("Clear");

		textToDate = new Text(composite_1, SWT.BORDER);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textToDate.setEditable(false);

		textToOriginal = new Text(composite_1, SWT.BORDER);
		textToOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		textToOriginal.setEditable(false);

		scrolledComposite.setContent(composite_1);
		scrolledComposite
				.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		buttonSelect = new Button(composite, SWT.NONE);
		buttonSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				get();
			}
		});
		buttonSelect.setText("Select");

		buttonInsert = new Button(composite, SWT.NONE);
		buttonInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				insert();
			}
		});
		buttonInsert.setText("Insert");

		buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});
		buttonUpdate.setText("Update");

		buttonDelete = new Button(composite, SWT.NONE);
		buttonDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delete();
			}
		});
		buttonDelete.setText("Delete");

		buttonClear = new Button(composite, SWT.NONE);
		buttonClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear();
			}
		});
		buttonClear.setText("Clear");

		buttonClose = new Button(composite, SWT.NONE);
		buttonClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonClose.setText("Close");

//		get(1);
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE",
					"Event " + textId.getText() + " has been deleted");
			clear();
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * The object is not needed anymore, but not yet destroyed
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	private void get() {
		final int key = Integer.parseInt(textId.getText());
		get(key);
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {

			provider.get(key);
			textId.setText(Integer.toString(provider.getEventPid()));
			textEventNamePid
					.setText(Integer.toString(provider.getEventNamePid()));
			textEventName.setText(provider.getEventName());
			textFromDatePid
					.setText(Integer.toString(provider.getFromDatePid()));
			textFromDate.setText(provider.getFromDateLabel());
			textFromOriginal.setText(provider.getFromDateOriginal());
			textToDatePid.setText(Integer.toString(provider.getToDatePid()));
			textToDate.setText(provider.getToDateLabel());
			textToOriginal.setText(provider.getToDateOriginal());
			textLanguagePid
					.setText(Integer.toString(provider.getLanguagePid()));
			textIsoCode.setText(provider.getIsoCode());
			textLanguage.setText(provider.getLanguage());
			textEventTypePid
					.setText(Integer.toString(provider.getEventTypePid()));
			textEventType.setText(provider.getEventType());

			eventBroker.post("MESSAGE",
					"Event " + textId.getText() + " has been fetched");

		} catch (final Exception e) {
			clear();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void insert() {
		try {
			provider.setFromDatePid(Integer.parseInt(textFromDate.getText()));
			provider.setEventPid(Integer.parseInt(textId.getText()));
			provider.setToDatePid(Integer.parseInt(textToDate.getText()));
			provider.insert();
			eventBroker.post("MESSAGE",
					"Event " + textId.getText() + " has been inserted");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}

	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 */
	@Inject
	@Optional
	private void subscribeLocationPidUpdateTopic(
			@UIEventTopic(Constants.EVENT_PID_UPDATE_TOPIC) int key) {
		get(key);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setFromDatePid(Integer.parseInt(textFromDate.getText()));
			provider.setEventPid(Integer.parseInt(textId.getText()));
			provider.setToDatePid(Integer.parseInt(textToDate.getText()));
			provider.update();
			eventBroker.post("MESSAGE",
					"Event Name " + textId.getText() + " has been inserted");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}

	}

}
