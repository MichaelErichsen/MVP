package net.myerichsen.hremvp.person.parts;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
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
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.listeners.IntegerListener;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Display static data about a person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 25. jan. 2019
 */
// FIXME Displays twice
public class PersonView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Text textId;
	private Text textBirthDatePid;
	private Text textBirthDate;
	private Text textDeathDatePid;
	private Text textDeathDate;

	private final PersonProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public PersonView() throws SQLException {
		provider = new PersonProvider();
		LOGGER.info("C:tor");
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
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	@Inject
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(2, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		LOGGER.info("Creating controls");
		textId = new Text(parent, SWT.BORDER);
		textId.setToolTipText("Doubleclick to edit");
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textId.addListener(SWT.Verify, new IntegerListener());

		Composite composite_3 = new Composite(parent, SWT.BORDER);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		composite_3.setLayout(new GridLayout(3, false));

		final Label lblBirthDate = new Label(composite_3, SWT.NONE);
		lblBirthDate.setText("Birth Date");

		textBirthDatePid = new Text(composite_3, SWT.BORDER);
		textBirthDatePid.addListener(SWT.Verify, new IntegerListener());

		textBirthDate = new Text(composite_3, SWT.BORDER);
		textBirthDate.setEditable(false);

		Composite composite_1 = new Composite(composite_3, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite_1.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button birthDateUpdateButton = new Button(composite_1, SWT.NONE);
		birthDateUpdateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textBirthDatePid.getShell(), context);
				final int birthDatePid = Integer.parseInt(textBirthDatePid.getText());
				dialog.sethDatePid(birthDatePid);

				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setHdatePid(birthDatePid);
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						hdp.update();
						textBirthDatePid.setText(Integer.toString(birthDatePid));
						textBirthDate.setText(hdp.getDate().toString());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		birthDateUpdateButton.setText("Update");

		Button birthDateBrowseButton = new Button(composite_1, SWT.NONE);
		birthDateBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(textBirthDatePid.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textBirthDatePid.setText(Integer.toString(hdp.getHdatePid()));
						textBirthDate.setText(hdp.getDate().toString());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		birthDateBrowseButton.setText("Browse");

		Button birthDateClearButton = new Button(composite_1, SWT.NONE);
		birthDateClearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textBirthDatePid.setText("0");
				textBirthDate.setText("");
			}
		});
		birthDateClearButton.setText("Clear");

		Composite composite_4 = new Composite(parent, SWT.BORDER);
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		composite_4.setLayout(new GridLayout(3, false));

		final Label lblDeathDate = new Label(composite_4, SWT.NONE);
		lblDeathDate.setSize(58, 15);
		lblDeathDate.setText("Death Date");

		textDeathDatePid = new Text(composite_4, SWT.BORDER);
		textDeathDatePid.setSize(250, 21);
		textDeathDatePid.addListener(SWT.Verify, new IntegerListener());

		textDeathDate = new Text(composite_4, SWT.BORDER);
		textDeathDate.setSize(120, 21);
		textDeathDate.setEditable(false);

		Composite composite_2 = new Composite(composite_4, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite_2.setSize(151, 31);
		composite_2.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button deathDateUpdateButton = new Button(composite_2, SWT.NONE);
		deathDateUpdateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textDeathDatePid.getShell(), context);
				final int deathDatePid = Integer.parseInt(textDeathDatePid.getText());
				dialog.sethDatePid(deathDatePid);

				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setHdatePid(deathDatePid);
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						hdp.update();
						textDeathDatePid.setText(Integer.toString(deathDatePid));
						textDeathDate.setText(hdp.getDate().toString());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		deathDateUpdateButton.setText("Update");

		Button deathDateBrowseButton = new Button(composite_2, SWT.NONE);
		deathDateBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(textDeathDatePid.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textDeathDatePid.setText(Integer.toString(hdp.getHdatePid()));
						textDeathDate.setText(hdp.getDate().toString());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		deathDateBrowseButton.setText("Browse");

		Button deathDateClearButton = new Button(composite_2, SWT.NONE);
		deathDateClearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textDeathDatePid.setText("0");
				textDeathDate.setText("");
			}
		});
		deathDateClearButton.setText("Clear");

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button buttonSelect = new Button(composite, SWT.NONE);
		buttonSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				get();
				// FIXME Post events for buttons
			}
		});
		buttonSelect.setText("Select");

		Button buttonInsert = new Button(composite, SWT.NONE);
		buttonInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				insert();
			}
		});
		buttonInsert.setText("Insert");

		Button buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});
		buttonUpdate.setText("Update");

		Button buttonDelete = new Button(composite, SWT.NONE);
		buttonDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delete();
			}
		});
		buttonDelete.setText("Delete");

		Button buttonClear = new Button(composite, SWT.NONE);
		buttonClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear();
			}
		});
		buttonClear.setText("Clear");

//		get(1);
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
			eventBroker.post("MESSAGE", e.getMessage());
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
				final int birthDatePid = provider.getBirthDatePid();
				textBirthDatePid.setText(Integer.toString(birthDatePid));
				final Hdates birthDate = new Hdates();
				birthDate.get(birthDatePid);
				textBirthDate.setText(birthDate.getDate().toString());
			} catch (final Exception e) {
				LOGGER.info(e.getMessage());
				textBirthDate.setText("");
			}

			try {
				final int deathDatePid = provider.getDeathDatePid();
				textDeathDatePid.setText(Integer.toString(deathDatePid));
				final Hdates deathDate = new Hdates();
				deathDate.get(deathDatePid);
				textDeathDate.setText(deathDate.getDate().toString());
			} catch (final Exception e) {
				LOGGER.info(e.getMessage());
				textDeathDate.setText("");
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
	protected void insert() {
		try {
			provider.setBirthDatePid(Integer.parseInt(textBirthDatePid.getText()));
			provider.setDeathDatePid(Integer.parseInt(textDeathDatePid.getText()));
			provider.setPersonPid(Integer.parseInt(textId.getText()));
			provider.insert();
			eventBroker.post("MESSAGE", "Person " + textId.getText() + " has been inserted");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
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
			provider.setBirthDatePid(Integer.parseInt(textBirthDatePid.getText()));
			provider.setDeathDatePid(Integer.parseInt(textDeathDatePid.getText()));
			provider.setPersonPid(Integer.parseInt(textId.getText()));
			provider.update();
			eventBroker.post("MESSAGE", "Person " + textId.getText() + " has been updated");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
		}
	}

}
