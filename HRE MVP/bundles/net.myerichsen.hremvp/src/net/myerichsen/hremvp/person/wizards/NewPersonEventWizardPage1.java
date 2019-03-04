package net.myerichsen.hremvp.person.wizards;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.LanguageProvider;
import net.myerichsen.hremvp.providers.HREComboLabelProvider;

/**
 * Person events wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 4. mar. 2019
 *
 */
public class NewPersonEventWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	// private final IEventBroker eventBroker;

	private int languagePid;
	private int eventTypePid;
	private String eventLabel;
	private int fromDatePid;
	private String fromDate;
	private int toDatePid;
	private String toDate;
	private int locationPid;
	private String location;
	private List<List<String>> languageList;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonEventWizardPage1(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Events");
		setDescription(
				"Add an event. Optionally add a location. More locations and more persons can be added later.\r\n");
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		final Label lblLanguage = new Label(container, SWT.NONE);
		lblLanguage.setText("ISO Code");

		final ComboViewer comboViewerLanguage = new ComboViewer(container,
				SWT.NONE);
		final Combo comboLanguage = comboViewerLanguage.getCombo();
		comboLanguage.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = comboLanguage.getSelectionIndex();
				languagePid = Integer
						.parseInt(languageList.get(selectionIndex).get(0));
			}
		});
		comboLanguage.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		comboViewerLanguage
				.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerLanguage.setLabelProvider(new HREComboLabelProvider());

		final Label lblEventType = new Label(container, SWT.NONE);
		lblEventType.setText("Event type");

		final ComboViewer comboViewerEventType = new ComboViewer(container,
				SWT.NONE);
		final Combo comboEventType = comboViewerEventType.getCombo();
		comboEventType.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventLabel = comboEventType.getText();
			}
		});
		comboEventType.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFrom = new Composite(container, SWT.BORDER);
		compositeFrom.setLayout(new GridLayout(3, false));
		compositeFrom.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblFromDate = new Label(compositeFrom, SWT.NONE);
		lblFromDate.setBounds(0, 0, 55, 15);
		lblFromDate.setText("From Date");

		final Text textFromDatePid = new Text(compositeFrom, SWT.BORDER);
		textFromDatePid.setEditable(false);
		textFromDatePid.setBounds(0, 0, 76, 21);

		final Text textFromDate = new Text(compositeFrom, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFromButtons = new Composite(compositeFrom,
				SWT.NONE);
		compositeFromButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		compositeFromButtons.setBounds(0, 0, 64, 64);
		compositeFromButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewFrom = new Button(compositeFromButtons, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		btnNewFrom.setText("New");

		final Button btnBrowseFrom = new Button(compositeFromButtons, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		btnBrowseFrom.setText("Browse");

		final Button btnClearFrom = new Button(compositeFromButtons, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		btnClearFrom.setText("Clear");

		final Composite compositeTo = new Composite(container, SWT.BORDER);
		compositeTo.setLayout(new GridLayout(3, false));
		compositeTo.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblToDate = new Label(compositeTo, SWT.NONE);
		lblToDate.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblToDate.setBounds(0, 0, 55, 15);
		lblToDate.setText("To Date");

		final Text textToDatePid = new Text(compositeTo, SWT.BORDER);
		textToDatePid.setEditable(false);
		textToDatePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Text textToDate = new Text(compositeTo, SWT.BORDER);
		textToDate.setEditable(false);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeToButtons = new Composite(compositeTo,
				SWT.NONE);
		compositeToButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		compositeToButtons.setSize(137, 31);
		compositeToButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnCopyFromDate = new Button(compositeToButtons, SWT.NONE);
		btnCopyFromDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		btnCopyFromDate.setText("Copy From Date");

		final Button buttonNewTo = new Button(compositeToButtons, SWT.NONE);
		buttonNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		buttonNewTo.setText("New");

		final Button buttonBrowseTo = new Button(compositeToButtons, SWT.NONE);
		buttonBrowseTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		buttonBrowseTo.setText("Browse");

		final Button buttonToClear = new Button(compositeToButtons, SWT.NONE);
		buttonToClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		buttonToClear.setText("Clear");

		final Composite compositeLocation = new Composite(container,
				SWT.BORDER);
		compositeLocation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		compositeLocation.setLayout(new GridLayout(3, false));

		final Label lblLocation = new Label(compositeLocation, SWT.NONE);
		lblLocation.setBounds(0, 0, 55, 15);
		lblLocation.setText("Location");

		final Text textLocationPid = new Text(compositeLocation, SWT.BORDER);
		textLocationPid.setEditable(false);
		textLocationPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Text textLocation = new Text(compositeLocation, SWT.BORDER);
		textLocation.setEditable(false);
		textLocation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeLocationButtons = new Composite(
				compositeLocation, SWT.NONE);
		compositeLocationButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		compositeLocationButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonNewLocation = new Button(compositeLocationButtons,
				SWT.NONE);
		buttonNewLocation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		buttonNewLocation.setText("New");

		final Button buttonBrowseLocation = new Button(compositeLocationButtons,
				SWT.NONE);
		buttonBrowseLocation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		buttonBrowseLocation.setText("Browse");

		final Button buttonClearLocation = new Button(compositeLocationButtons,
				SWT.NONE);
		buttonClearLocation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		buttonClearLocation.setText("Clear");

		try {
			// Populate language combo box
			languageList = new LanguageProvider().get();
			comboViewerLanguage.setInput(languageList);
			final int llsSize = languageList.size();
			final String g = store.getString("GUILANGUAGE");
			int index = 0;

			for (int i = 0; i < llsSize; i++) {
				if (g.equals(languageList.get(i).get(1))) {
					index = i;
				}
			}
			comboLanguage.select(index);
		} catch (SQLException | MvpException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}

	}

	/**
	 * @return the eventLabel
	 */
	public String getEventLabel() {
		return eventLabel;
	}

	/**
	 * @return the eventTypePid
	 */
	public int getEventTypePid() {
		return eventTypePid;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return languagePid;
	}

	/**
	 * @return
	 */
	public List<List<String>> getListOfLists() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the locationPid
	 */
	public int getLocationPid() {
		return locationPid;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

}
