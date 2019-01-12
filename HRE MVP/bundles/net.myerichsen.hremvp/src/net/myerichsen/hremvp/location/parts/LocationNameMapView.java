package net.myerichsen.hremvp.location.parts;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.listeners.NumericVerifyListener;
import net.myerichsen.hremvp.location.providers.LocationNameMapProvider;

/**
 * Display all data about a location name map
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. sep. 2018
 *
 */
public class LocationNameMapView {
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
	private Text textLocationStyleId;
	private Text textLocationStyle;
	private Text textPartNo;
	private Text textLabel;
	private final LocationNameMapProvider provider;
	private Group group;
	private Button btnRadioButtonPre;
	private Button btnRadioButtonPost;
	private Button btnRadioButtonNone;
	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 */
	public LocationNameMapView() throws SQLException {
		provider = new LocationNameMapProvider();
	}

	/**
	 *
	 */
	protected void clear() {
		textId.setText("0");
		textLocationStyleId.setText("0");
		textLocationStyle.setText("");
		textPartNo.setText("0");
		textLabel.setText("");
		btnRadioButtonNone.setSelection(true);
		btnRadioButtonPre.setSelection(false);
		btnRadioButtonPost.setSelection(false);
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
	 * @param parent The parent composite.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(3, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textId.addVerifyListener(new NumericVerifyListener());

		new Label(parent, SWT.NONE);

		final Label lblLocationStyle = new Label(parent, SWT.NONE);
		lblLocationStyle.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblLocationStyle.setText("Style");

		textLocationStyleId = new Text(parent, SWT.BORDER);
		textLocationStyleId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textLocationStyleId.addVerifyListener(new NumericVerifyListener());

		textLocationStyle = new Text(parent, SWT.BORDER);
		textLocationStyle.setEditable(false);
		textLocationStyle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblPartNo = new Label(parent, SWT.NONE);
		lblPartNo.setText("Part no.");

		textPartNo = new Text(parent, SWT.BORDER);
		textPartNo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textPartNo.addVerifyListener(new NumericVerifyListener());

		new Label(parent, SWT.NONE);

		final Label lblLabel = new Label(parent, SWT.NONE);
		lblLabel.setText("Label");

		textLabel = new Text(parent, SWT.BORDER);
		textLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblLabelReportPosition = new Label(parent, SWT.NONE);
		lblLabelReportPosition.setText("Label Report Position");

		group = new Group(parent, SWT.NONE);
		group.setLayout(new RowLayout(SWT.HORIZONTAL));
		group.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		btnRadioButtonPre = new Button(group, SWT.RADIO);
		btnRadioButtonPre.setSelection(true);
		btnRadioButtonPre.setText("Prefix");

		btnRadioButtonPost = new Button(group, SWT.RADIO);
		btnRadioButtonPost.setText("Postfix");

		btnRadioButtonNone = new Button(group, SWT.RADIO);
		btnRadioButtonNone.setText("None");

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
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
	 * @throws MvpException
	 * @throws NumberFormatException
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE", "Location Name Map " + textId.getText() + " has been deleted");
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
		get(Integer.parseInt(textId.getText()));
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			provider.get(key);

			textId.setText(Integer.toString(provider.getLocationNameMapPid()));
			textLocationStyleId.setText(Integer.toString(provider.getLocationNameStylePid()));
			textLocationStyle.setText(provider.getStyleLabel());
			textPartNo.setText(Integer.toString(provider.getPartNo()));
			textLabel.setText(provider.getLabel());

			LOGGER.info("Label position: " + provider.getLabelPosition());

			if (provider.getLabelPosition().trim().equals("PRE")) {
				btnRadioButtonNone.setSelection(false);
				btnRadioButtonPre.setSelection(true);
				btnRadioButtonPost.setSelection(false);
			} else if (provider.getLabelPosition().equals("POST")) {
				btnRadioButtonNone.setSelection(false);
				btnRadioButtonPre.setSelection(false);
				btnRadioButtonPost.setSelection(true);
			} else {
				btnRadioButtonNone.setSelection(true);
				btnRadioButtonPre.setSelection(false);
				btnRadioButtonPost.setSelection(false);
			}

			eventBroker.post("MESSAGE", "Location Name Map " + textId.getText() + " has been fetched");
		} catch (final Exception e) {
			clear();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 *
	 */
	protected void insert() {
		try {
			provider.setLabel(textLabel.getText());
			provider.setLocationNameMapPid(Integer.parseInt(textId.getText()));
			provider.setLocationNameStylePid(Integer.parseInt(textLocationStyleId.getText()));
			provider.setPartNo(Integer.parseInt(textPartNo.getText()));

			if (btnRadioButtonNone.getSelection()) {
				provider.setLabelPosition("NONE");
			} else if (btnRadioButtonPre.getSelection()) {
				provider.setLabelPosition("PRE ");
			} else if (btnRadioButtonPost.getSelection()) {
				provider.setLabelPosition("POST");
			}

			provider.insert();
			eventBroker.post("MESSAGE", "Location Name Map " + textId.getText() + " has been inserted");
		} catch (final SQLException e) {
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
	 * @param locationNameMapPid
	 * @throws SQLException
	 */
	@Inject
	@Optional
	private void subscribeLocationNameMapUpdateTopic(
			@UIEventTopic(Constants.LOCATION_NAME_MAP_PID_UPDATE_TOPIC) int locationNameMapPid) throws SQLException {
		get(locationNameMapPid);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setLabel(textLabel.getText());
			provider.setLocationNameMapPid(Integer.parseInt(textId.getText()));
			provider.setLocationNameStylePid(Integer.parseInt(textLocationStyleId.getText()));
			provider.setPartNo(Integer.parseInt(textPartNo.getText()));

			if (btnRadioButtonNone.getSelection()) {
				provider.setLabelPosition("NONE");
			} else if (btnRadioButtonPre.getSelection()) {
				provider.setLabelPosition("PRE ");
			} else if (btnRadioButtonPost.getSelection()) {
				provider.setLabelPosition("POST");
			}

			provider.update();
			eventBroker.post("MESSAGE", "Location Name Map " + textId.getText() + " has been updated");
		} catch (final SQLException e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}
}
