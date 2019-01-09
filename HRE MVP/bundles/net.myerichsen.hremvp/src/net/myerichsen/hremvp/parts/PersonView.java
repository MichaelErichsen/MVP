package net.myerichsen.hremvp.parts;

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
import net.myerichsen.hremvp.dbmodels.Hdates;
import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.listeners.IntegerListener;
import net.myerichsen.hremvp.providers.HDateProvider;
import net.myerichsen.hremvp.providers.PersonProvider;

/**
 * Display all data about a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 9. jan. 2019
 */
public class PersonView {
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
	private Text textBirthDatePid;
	private Text textBirthDate;
	private Text textDeathDatePid;
	private Text textDeathDate;

	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private final PersonProvider provider;
	private Button birthDateUpdateButton;
	private Button deathDateUpdateButton;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public PersonView() throws SQLException {
		provider = new PersonProvider();
	}

	/**
	 *
	 */
	private void clear() {
		textId.setText("");
		textBirthDatePid.setText("");
		textBirthDate.setText("");
		textDeathDatePid.setText("");
		textDeathDate.setText("");

	}

	/**
	 *
	 */
	private void close() {
		// FIXME Find actual part to close
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
	@Inject
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(4, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textId.addListener(SWT.Verify, new IntegerListener());

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		final Label lblBirthDate = new Label(parent, SWT.NONE);
		lblBirthDate.setText("Birth Date");

		textBirthDatePid = new Text(parent, SWT.BORDER);
		textBirthDatePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textBirthDatePid.addListener(SWT.Verify, new IntegerListener());

		textBirthDate = new Text(parent, SWT.BORDER);
		textBirthDate.setEditable(false);
		textBirthDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		birthDateUpdateButton = new Button(parent, SWT.NONE);
		birthDateUpdateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DateDialog dialog = new DateDialog(textBirthDatePid.getShell(), context);
				int birthDatePid = Integer.parseInt(textBirthDatePid.getText());
				dialog.sethDatePid(birthDatePid);

				if (dialog.open() == Window.OK) {
					try {
						HDateProvider hdp = new HDateProvider();
						hdp.setHdatePid(birthDatePid);
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						hdp.update();
						textBirthDatePid.setText(Integer.toString(birthDatePid));
						textBirthDate.setText(hdp.getDate().toString());
					} catch (Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		birthDateUpdateButton.setText("Update");

		final Label lblDeathDate = new Label(parent, SWT.NONE);
		lblDeathDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDeathDate.setText("Death Date");

		textDeathDatePid = new Text(parent, SWT.BORDER);
		textDeathDatePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textDeathDatePid.addListener(SWT.Verify, new IntegerListener());

		textDeathDate = new Text(parent, SWT.BORDER);
		textDeathDate.setEditable(false);
		textDeathDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		deathDateUpdateButton = new Button(parent, SWT.NONE);
		deathDateUpdateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DateDialog dialog = new DateDialog(textDeathDatePid.getShell(), context);
				int deathDatePid = Integer.parseInt(textDeathDatePid.getText());
				dialog.sethDatePid(deathDatePid);

				if (dialog.open() == Window.OK) {
					try {
						HDateProvider hdp = new HDateProvider();
						hdp.setHdatePid(deathDatePid);
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						hdp.update();
						textDeathDatePid.setText(Integer.toString(deathDatePid));
						textDeathDate.setText(hdp.getDate().toString());
					} catch (Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		deathDateUpdateButton.setText("Update");

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		buttonSelect = new Button(composite, SWT.NONE);
		buttonSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				get();
				// FIXME Post events for buttons
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
		new Label(parent, SWT.NONE);

		get(1);
	}

	/**
	 * 
	 */
	protected void insert() {
		// TODO Auto-generated method stub

	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE", "Person" + textId.getText() + " has been deleted");
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
	 * @param personPid
	 */
	private void get(int personPid) {
		try {
			provider.get(personPid);

			textId.setText(Integer.toString(personPid));
			try {
				int birthDatePid = provider.getBirthDatePid();
				textBirthDatePid.setText(Integer.toString(birthDatePid));
				Hdates birthDate = new Hdates();
				birthDate.get(birthDatePid);
				textBirthDate.setText(birthDate.getDate().toString());
			} catch (final Exception e) {
				LOGGER.info(e.getMessage());
				textBirthDate.setText("");
			}

			try {
				int deathDatePid = provider.getDeathDatePid();
				textDeathDatePid.setText(Integer.toString(deathDatePid));
				Hdates deathDate = new Hdates();
				deathDate.get(deathDatePid);
				textDeathDate.setText(deathDate.getDate().toString());
			} catch (final Exception e) {
				LOGGER.info(e.getMessage());
				textDeathDate.setText("");
			}
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param personPid
	 * @throws SQLException
	 */
	@Inject
	@Optional
	private void subscribeKeyUpdateTopic(@UIEventTopic(Constants.PERSON_PID_UPDATE_TOPIC) int personPid)
			throws SQLException {
		LOGGER.info("Receiving person pid " + personPid);
		get(personPid);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setBirthDatePid(Integer.parseInt(textBirthDate.getText()));
			provider.setDeathDatePid(Integer.parseInt(textDeathDate.getText()));
			provider.setPersonPid(Integer.parseInt(textId.getText()));
			provider.update();
			eventBroker.post("MESSAGE", "Person " + textId.getText() + " has been updated");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

}
