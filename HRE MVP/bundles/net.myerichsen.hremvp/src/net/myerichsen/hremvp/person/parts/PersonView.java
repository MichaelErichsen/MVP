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
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 6. feb. 2019
 *
 */
public class PersonView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Text textId;
	private Text textBirthDatePid;
	private Text textDeathDatePid;
	private Text textBirthDate;
	private Text textDeathDate;

	private final PersonProvider provider;
	private int personPid;
	private int birthDatePid;
	private int deathDatePid;

	public PersonView() throws SQLException {
		provider = new PersonProvider();
	}

	/**
	 * @param context
	 */
	private void browseBirthDate(IEclipseContext context) {
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

	/**
	 * @param context
	 */
	private void browseDeathDate(IEclipseContext context) {
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

	/**
	 *
	 */
	protected void clear() {
		textId.setText("");
		textBirthDatePid.setText("");
		textBirthDate.setText("");
		textDeathDatePid.setText("");
		textDeathDate.setText("");
	}

	/**
	 *
	 */
	private void clearBirthDate() {
		textBirthDatePid.setText("0");
		textBirthDate.setText("");
	}

	/**
	 *
	 */
	private void clearDeathDate() {
		textDeathDatePid.setText("0");
		textDeathDate.setText("");
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(2, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeBirthDate = new Composite(parent, SWT.BORDER);
		compositeBirthDate.setLayout(new GridLayout(3, false));
		compositeBirthDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblBirthDate = new Label(compositeBirthDate, SWT.NONE);
		lblBirthDate.setText("Birth Date");

		textBirthDatePid = new Text(compositeBirthDate, SWT.BORDER);
		textBirthDatePid.setEditable(false);
		textBirthDatePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textBirthDate = new Text(compositeBirthDate, SWT.BORDER);
		textBirthDate.setEditable(false);
		textBirthDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeBirthButtons = new Composite(compositeBirthDate, SWT.NONE);
		compositeBirthButtons.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		compositeBirthButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button birthDateNewButton = new Button(compositeBirthButtons, SWT.NONE);
		birthDateNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewBirthDate(context);
			}
		});
		birthDateNewButton.setText("New");

		final Button birthDateBrowseButton = new Button(compositeBirthButtons, SWT.NONE);
		birthDateBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseBirthDate(context);
			}
		});
		birthDateBrowseButton.setText("Browse");

		final Button birthDateClearButton = new Button(compositeBirthButtons, SWT.NONE);
		birthDateClearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearBirthDate();
			}
		});
		birthDateClearButton.setText("Clear");

		final Composite compositeDeathDate = new Composite(parent, SWT.BORDER);
		compositeDeathDate.setLayout(new GridLayout(3, false));
		compositeDeathDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblDeathDate = new Label(compositeDeathDate, SWT.NONE);
		lblDeathDate.setText("Death Date");

		textDeathDatePid = new Text(compositeDeathDate, SWT.BORDER);
		textDeathDatePid.setEditable(false);
		textDeathDatePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textDeathDate = new Text(compositeDeathDate, SWT.BORDER);
		textDeathDate.setEditable(false);
		textDeathDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeDeathButtons = new Composite(compositeDeathDate, SWT.NONE);
		compositeDeathButtons.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		compositeDeathButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button deathDateNewButton = new Button(compositeDeathButtons, SWT.NONE);
		deathDateNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewDeathDate(context);
			}
		});
		deathDateNewButton.setText("New");

		final Button deathDateBrowseButton = new Button(compositeDeathButtons, SWT.NONE);
		deathDateBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseDeathDate(context);
			}
		});
		deathDateBrowseButton.setText("Browse");

		final Button deathDateClearButton = new Button(compositeDeathButtons, SWT.NONE);
		deathDateClearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearDeathDate();
			}
		});
		deathDateClearButton.setText("Clear");

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonSelect = new Button(composite, SWT.NONE);
		buttonSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				get();
			}
		});
		buttonSelect.setText("Select");

		final Button buttonInsert = new Button(composite, SWT.NONE);
		buttonInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				insert();
			}
		});
		buttonInsert.setText("Insert");

		final Button buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});
		buttonUpdate.setText("Update");

		final Button buttonDelete = new Button(composite, SWT.NONE);
		buttonDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delete();
			}
		});
		buttonDelete.setText("Delete");

		final Button buttonClear = new Button(composite, SWT.NONE);
		buttonClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear();
			}
		});
		buttonClear.setText("Clear");

		personPid = 1;
		get(personPid);
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE", "Person" + textId.getText() + " has been deleted");
			clear();
			eventBroker.post(net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC, personPid);
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 *
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
				birthDatePid = provider.getBirthDatePid();
				textBirthDatePid.setText(Integer.toString(birthDatePid));
				final Hdates birthDate = new Hdates();
				birthDate.get(birthDatePid);
				textBirthDate.setText(birthDate.getDate().toString());
			} catch (final Exception e) {
				LOGGER.info(e.getMessage());
				textBirthDate.setText("");
			}

			try {
				deathDatePid = provider.getDeathDatePid();
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
		}
	}

	/**
	 *
	 */
	private void getNewBirthDate(IEclipseContext context) {
		final DateDialog dialog = new DateDialog(textBirthDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getLocalDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				birthDatePid = hdp.insert();
				textBirthDatePid.setText(Integer.toString(birthDatePid));
				textBirthDate.setText(dialog.getLocalDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void getNewDeathDate(IEclipseContext context) {
		final DateDialog dialog = new DateDialog(textDeathDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getLocalDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				deathDatePid = hdp.insert();
				textDeathDatePid.setText(Integer.toString(deathDatePid));
				textDeathDate.setText(dialog.getLocalDate().toString());
			} catch (final Exception e1) {
				LOGGER.severe(e1.getMessage());
			}
		}
	}

	/**
	 *
	 */
	protected void insert() {
		try {
			provider.setBirthDatePid(Integer.parseInt(textBirthDatePid.getText()));
			provider.setDeathDatePid(Integer.parseInt(textDeathDatePid.getText()));
			personPid = Integer.parseInt(textId.getText());
			provider.setPersonPid(personPid);
			provider.insert();
			eventBroker.post("MESSAGE", "Person " + textId.getText() + " has been inserted");
			eventBroker.post(net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC, personPid);
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 *
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
		this.personPid = personPid;
		get(personPid);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setBirthDatePid(Integer.parseInt(textBirthDatePid.getText()));
			provider.setDeathDatePid(Integer.parseInt(textDeathDatePid.getText()));
			personPid = Integer.parseInt(textId.getText());
			provider.setPersonPid(personPid);
			provider.update();
			eventBroker.post("MESSAGE", "Person " + textId.getText() + " has been updated");
			eventBroker.post(net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC, personPid);
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}
}