package net.myerichsen.gen.mvp.parts;

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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.gen.mvp.Constants;
import net.myerichsen.gen.mvp.providers.EventTypeProvider;

/**
 * Display all data about an event type
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 11. dec. 2018
 */

public class EventTypeView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private IEventBroker eventBroker;

	private Text textId;
	private Text textLabel;
	private Table table;
	private TableViewer tableViewer;
	private Label lblEventTypeNames;
	private TableColumn tblclmnId;
	private TableViewerColumn tableViewerColumnID;
	private TableColumn tblclmnLabel;
	private TableViewerColumn tableViewerColumnLabel;
	private TableColumn tblclmnLanguage;
	private TableViewerColumn tableViewerColumnLanguage;

	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private EventTypeProvider provider;
	private TableColumn tblclmnIsoCode;
	private TableViewerColumn tableViewerColumn;

	/**
	 * Constructor
	 *
	 * @throws Exception
	 *
	 */
	public EventTypeView() throws Exception {
		provider = new EventTypeProvider();
	}

	/**
	 *
	 */
	protected void clear() {
		textId.setText("0");
		textLabel.setText("");
		table.removeAll();
	}

	/**
	 *
	 */
	protected void close() {
		final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
		final MPart part = (MPart) stacks.get(stacks.size() - 2).getSelectedElement();
		partService.hidePart(part, true);
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(2, false));

		Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("Event Type ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblLabel = new Label(parent, SWT.NONE);
		lblLabel.setText("Label");

		textLabel = new Text(parent, SWT.BORDER);
		textLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblEventTypeNames = new Label(parent, SWT.NONE);
		lblEventTypeNames.setText("Event Type Names");

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEventTypeNameView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.widthHint = 473;
		table.setLayoutData(gd_table);

		tableViewerColumnID = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnId = tableViewerColumnID.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		tableViewerColumnLabel = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnLabel = tableViewerColumnLabel.getColumn();
		tblclmnLabel.setWidth(100);
		tblclmnLabel.setText("Label");

		tableViewerColumnLanguage = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnLanguage = tableViewerColumnLanguage.getColumn();
		tblclmnLanguage.setWidth(100);
		tblclmnLanguage.setText("Language");

		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnIsoCode = tableViewerColumn.getColumn();
		tblclmnIsoCode.setWidth(100);
		tblclmnIsoCode.setText("ISO Code");

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
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
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE", "Event Type " + textId.getText() + " has been deleted");
			clear();
		} catch (Exception e) {
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
		int key = Integer.parseInt(textId.getText());
		get(key);
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			provider.get(key);
			textId.setText(Integer.toString(key));
			textLabel.setText(provider.getLabel());

			table.removeAll();
			TableItem item;

			List<List<String>> lls = provider.getNameList(key);

			for (List<String> list : lls) {
				item = new TableItem(table, SWT.NONE);

				for (int i = 0; i < list.size(); i++) {
					item.setText(i, list.get(i));
				}
			}

		} catch (Exception e) {
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
			provider.setLabel(textLabel.getText());
			provider.insert();
			eventBroker.post("MESSAGE", "Event Type " + textId.getText() + " has been inserted");
		} catch (Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}

	}

	/**
	 *
	 */
	protected void openEventTypeNameView() {
		//FIXME Create dialog

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
	private void subscribeEventTypePidUpdateTopic(@UIEventTopic(Constants.EVENT_TYPE_PID_UPDATE_TOPIC) int key) {
		get(key);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setLabel(textLabel.getText());
			provider.update();
			eventBroker.post("MESSAGE", "Event Type " + textId.getText() + " has been inserted");
		} catch (Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}

	}

}
