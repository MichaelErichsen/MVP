package net.myerichsen.hremvp.person.parts;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
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
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.listeners.NumericVerifyListener;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.person.providers.SexProvider;

/**
 * Display all data for a sex
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 27. mar. 2019
 *
 */
@SuppressWarnings("restriction")
public class PersonSexView {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private Text textId;
	private Text textPersonPid;
	private Text textFromDate;
	private Text textToDate;
	private Button btnPrimarySex;
	private Text textSexTypePid;
	private Text textAbbreviation;
	private Text textLabel;
	private Text textIsoCode;

	private SexProvider sexesProvider;
	private PersonProvider provider;
	private int sexPid;

	/**
	 * Constructor
	 *
	 */
	public PersonSexView() {
		sexesProvider = new SexProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setEditable(false);
		textId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblPerson = new Label(parent, SWT.NONE);
		lblPerson.setText("Person");

		textPersonPid = new Text(parent, SWT.BORDER);
		textPersonPid.setEditable(false);
		textPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblFromDate = new Label(parent, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(parent, SWT.BORDER);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblToDate = new Label(parent, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(parent, SWT.BORDER);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		btnPrimarySex = new Button(parent, SWT.CHECK);
		btnPrimarySex.setSelection(true);
		btnPrimarySex.setText("Primary Sex");

		final Label lblSexType = new Label(parent, SWT.NONE);
		lblSexType.setText("Sex type id\r\nDblClk to open");

		textSexTypePid = new Text(parent, SWT.BORDER);
		textSexTypePid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openSexTypeView();
			}
		});
		textSexTypePid.addVerifyListener(new NumericVerifyListener());
		textSexTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblAbbreviation = new Label(parent, SWT.NONE);
		lblAbbreviation.setText("Abbreviation");

		textAbbreviation = new Text(parent, SWT.BORDER);
		textAbbreviation.setEditable(false);
		textAbbreviation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblLabel = new Label(parent, SWT.NONE);
		lblLabel.setText("Label");

		textLabel = new Text(parent, SWT.BORDER);
		textLabel.setEditable(false);
		textLabel.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblIsoCode = new Label(parent, SWT.NONE);
		lblIsoCode.setText("ISO Code");

		textIsoCode = new Text(parent, SWT.BORDER);
		textIsoCode.setEditable(false);
		textIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});
		buttonUpdate.setText("Update");
	}

	/**
	 * The object is not needed anymore, but not yet destroyed
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		sexPid = key;

		try {
			sexesProvider.get(key);
			provider = new PersonProvider();
			final List<String> sexesList = provider.getSexesList(key).get(0);

			textId.setText(sexesList.get(0));
			textPersonPid.setText(sexesList.get(1));
			textFromDate.setText(sexesList.get(5));
			textToDate.setText(sexesList.get(6));
			btnPrimarySex.setSelection(
					sexesList.get(4).equals("true") ? true : false);
			textSexTypePid.setText(sexesList.get(2));
			textAbbreviation.setText(sexesProvider.getAbbreviation());
			textLabel.setText(sexesList.get(3));
			textIsoCode.setText(sexesProvider.getIsocode());

			eventBroker.post("MESSAGE",
					"Name " + textId.getText() + " has been fetched");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void openSexTypeView() {
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.opensextypeview", null);
		handlerService.executeHandler(command);

		final int sexTypePid = Integer.parseInt(textSexTypePid.getText());

		LOGGER.info("Setting sex type pid: " + sexTypePid);
		eventBroker.post(Constants.SEX_TYPE_PID_UPDATE_TOPIC, sexTypePid);
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 * @throws MvpException
	 */
	@Inject
	@Optional
	private void subscribeKeyUpdateTopic(
			@UIEventTopic(Constants.SEX_PID_UPDATE_TOPIC) int key)
			throws MvpException {
		get(key);
	}

	/**
	 *
	 */
	private void update() {
		try {
			sexesProvider = new SexProvider();
			sexesProvider.get(sexPid);
			sexesProvider.setFromDatePid(0);
			sexesProvider.setToDatePid(0);
			sexesProvider.setPrimarySex(btnPrimarySex.getSelection());
			sexesProvider
					.setSexTypePid(Integer.parseInt(textSexTypePid.getText()));
			sexesProvider.update();
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
		sexesProvider.setFromDatePid(0);
	}

}
