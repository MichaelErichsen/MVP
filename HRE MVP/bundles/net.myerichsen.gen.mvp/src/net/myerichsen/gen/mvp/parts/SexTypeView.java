package net.myerichsen.gen.mvp.parts;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.gen.mvp.Constants;
import net.myerichsen.gen.mvp.MvpException;
import net.myerichsen.gen.mvp.providers.SexTypeProvider;

/**
 * Display all data for a sex type
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 5. okt. 2018
 *
 */
public class SexTypeView {
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

	private SexTypeProvider provider;

	/**
	 * Constructor
	 *
	 */
	public SexTypeView() {
		provider = new SexTypeProvider();
	}

	/**
	 * 
	 */
	protected void clear() {
		textId.setText("0");
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

		Label lblAbbreviation = new Label(parent, SWT.NONE);
		lblAbbreviation.setText("Abbreviation");

		textAbbreviation = new Text(parent, SWT.BORDER);
		textAbbreviation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblLabel = new Label(parent, SWT.NONE);
		lblLabel.setText("Label");

		textLabel = new Text(parent, SWT.BORDER);
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
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE", "Sex Type " + textId.getText() + " has been deleted");
			clear();
		} catch (Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
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
			textId.setText(Integer.toString(provider.getSexTypePid()));
			textAbbreviation.setText(provider.getAbbreviation());
			textLabel.setText(provider.getLabel());
			textLanguagePid.setText(Integer.toString(provider.getLanguagePid()));
			textLanguageLabel.setText(provider.getLanguageLabel());
			textIsoCode.setText(provider.getIsoCode());

			eventBroker.post("MESSAGE", "Sex Type " + textId.getText() + " has been fetched");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			clear();
		}
	}

	/**
	 *
	 */
	private void insert() {
		try {
			provider = new SexTypeProvider();
			provider.setSexTypePid(Integer.parseInt(textId.getText()));
			provider.setAbbreviation(textAbbreviation.getText());
			provider.setLabel(textLabel.getText());
			provider.setLanguagePid(Integer.parseInt(textLanguagePid.getText()));
			provider.insert();
			eventBroker.post("MESSAGE", "Sex Type " + textId.getText() + " has been inserted");
		} catch (Exception e) {
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
	 * @throws MvpException
	 */
	@Inject
	@Optional
	private void subscribeKeyUpdateTopic(@UIEventTopic(Constants.SEX_TYPE_PID_UPDATE_TOPIC) int key)
			throws MvpException {
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
