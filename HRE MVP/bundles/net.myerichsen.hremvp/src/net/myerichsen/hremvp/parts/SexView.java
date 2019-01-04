package net.myerichsen.hremvp.parts;

import java.sql.SQLException;
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
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
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
import net.myerichsen.hremvp.providers.SexProvider;

/**
 * Display all data for a sex
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 14. nov. 2018
 *
 */
@SuppressWarnings("restriction")
public class SexView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
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
	private Text textSexType;
	private Text textAbbreviation;
	private Text textLabel;
	private Text textLanguagePid;
	private Text textLanguageLabel;
	private Text textIsoCode;

	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private SexProvider provider;

	/**
	 * Constructor
	 *
	 */
	public SexView() {
		provider = new SexProvider();
	}

	/**
	 * 
	 */
	protected void clear() {
		textId.setText("0");
		textPersonPid.setText("0");
		textFromDate.setText("");
		textToDate.setText("");
		btnPrimarySex.setSelection(true);
		textSexType.setText("");
		textAbbreviation.setText("");
		textLabel.setText("");
		textLanguagePid.setText("0");
		textLanguageLabel.setText("");
		textIsoCode.setText("");
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
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblPerson = new Label(parent, SWT.NONE);
		lblPerson.setText("Person");

		textPersonPid = new Text(parent, SWT.BORDER);
		textPersonPid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblFromDate = new Label(parent, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(parent, SWT.BORDER);
		textFromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblToDate = new Label(parent, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(parent, SWT.BORDER);
		textToDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		btnPrimarySex = new Button(parent, SWT.CHECK);
		btnPrimarySex.setSelection(true);
		btnPrimarySex.setText("Primary Sex");

		Label lblSexType = new Label(parent, SWT.NONE);
		lblSexType.setText("Sex type\r\nDblClk to open");

		textSexType = new Text(parent, SWT.BORDER);
		textSexType.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openSexTypeView();
			}
		});
		textSexType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblAbbreviation = new Label(parent, SWT.NONE);
		lblAbbreviation.setText("Abbreviation");

		textAbbreviation = new Text(parent, SWT.BORDER);
		textAbbreviation.setEditable(false);
		textAbbreviation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblLabel = new Label(parent, SWT.NONE);
		lblLabel.setText("Label");

		textLabel = new Text(parent, SWT.BORDER);
		textLabel.setEditable(false);
		textLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblLanguageId = new Label(parent, SWT.NONE);
		lblLanguageId.setText("Language Id");

		textLanguagePid = new Text(parent, SWT.BORDER);
		textLanguagePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblLanguage = new Label(parent, SWT.NONE);
		lblLanguage.setText("Language");

		textLanguageLabel = new Text(parent, SWT.BORDER);
		textLanguageLabel.setEditable(false);
		textLanguageLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblIsoCode = new Label(parent, SWT.NONE);
		lblIsoCode.setText("ISO Code");

		textIsoCode = new Text(parent, SWT.BORDER);
		textIsoCode.setEditable(false);
		textIsoCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

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

//		get(1);
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE", "Sex " + textId.getText() + " has been deleted");
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
		get(Integer.parseInt(textId.getText()));
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			provider.get(key);

			textId.setText(Integer.toString(provider.getSexesPid()));
			textPersonPid.setText(Integer.toString(provider.getPersonPid()));
			try {
				textFromDate.setText(Integer.toString(provider.getFromDatePid()));
			} catch (Exception e) {
				textFromDate.setText("");
			}
			try {
				textToDate.setText(Integer.toString(provider.getToDatePid()));
			} catch (Exception e) {
				textToDate.setText("");
			}
			btnPrimarySex.setSelection(provider.isPrimarySex());
			textSexType.setText(Integer.toString(provider.getSexTypePid()));
			textAbbreviation.setText(provider.getAbbreviation());
			textLabel.setText(provider.getSexTypeLabel());
			textLanguagePid.setText(Integer.toString(provider.getLanguagePid()));
			textLanguageLabel.setText(provider.getLanguageLabel());
			textIsoCode.setText(provider.getIsocode());

			eventBroker.post("MESSAGE", "Name " + textId.getText() + " has been fetched");
		} catch (final SQLException | MvpException e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	private void insert() {
		// TODO insert() missing
		// try {
		// provider = new NameProvider();
		// provider.setNames(new Names());
		// provider.setNamePid(Integer.parseInt(textId.getText()));
		// provider.setPersonPid(Integer.parseInt(textPersonPid.getText()));
		// provider.setNameType(Integer.parseInt(textNameType.getText()));
		//
		// provider.setFromdate(dateTimeFrom.getYear() + "-" + dateTimeFrom.getMonth() +
		// "-" + dateTimeFrom.getDay());
		// Calendar calendar = Calendar.getInstance();
		// dateTimeFrom.setDate(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DATE));
		// dateTimeTo.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DATE));
		// table.removeAll();
		// provider.insert();
		// } catch (final SQLException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 *
	 */
	protected void openSexTypeView() {
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.opensextypeview", null);
		handlerService.executeHandler(command);

		int sexTypePid = Integer.parseInt(textSexType.getText());

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
	private void subscribeKeyUpdateTopic(@UIEventTopic(Constants.SEX_PID_UPDATE_TOPIC) int key) throws MvpException {
		get(key);
	}

	/**
	 *
	 */
	private void update() {
		// try {
		// provider = new Names();
		// provider.setNamePid(Integer.parseInt(textId.getText()));
		// provider.setPersonPid(Integer.parseInt(textPersonPid.getText()));
		// provider.setNameType(Integer.parseInt(textNameType.getText()));
		// provider.update();
		// } catch (final SQLException e) {
		// e.printStackTrace();
		// }
	}

}
