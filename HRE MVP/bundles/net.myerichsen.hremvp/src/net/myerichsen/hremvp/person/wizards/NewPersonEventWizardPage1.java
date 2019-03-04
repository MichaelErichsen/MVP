package net.myerichsen.hremvp.person.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

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
	private final IEclipseContext context;

	private Table table;

//	private final IEventBroker eventBroker;
	private List<List<String>> listOfLists;
	private Text textFromDatePid;
	private Text textFromDate;
	private Text textToDatePid;
	private Text textToDate;
	private Text textLocationPid;
	private Text textLocation;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonEventWizardPage1(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Events");
		setDescription(
				"Add an event. Optionally add a location. More locations and more persons can be added later.");
		this.context = context;
//		eventBroker = context.get(IEventBroker.class);
		listOfLists = new ArrayList<>();
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		Label lblIsoCode = new Label(container, SWT.NONE);
		lblIsoCode.setText("ISO Code");

		ComboViewer comboViewer_1 = new ComboViewer(container, SWT.NONE);
		Combo comboIsoCode = comboViewer_1.getCombo();
		comboIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblEventType_1 = new Label(container, SWT.NONE);
		lblEventType_1.setText("Event type");

		ComboViewer comboViewer = new ComboViewer(container, SWT.NONE);
		Combo comboEventType = comboViewer.getCombo();
		comboEventType.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite compositeFrom = new Composite(container, SWT.BORDER);
		compositeFrom.setLayout(new GridLayout(3, false));
		compositeFrom.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblFromDate = new Label(compositeFrom, SWT.NONE);
		lblFromDate.setBounds(0, 0, 55, 15);
		lblFromDate.setText("From Date");

		textFromDatePid = new Text(compositeFrom, SWT.BORDER);
		textFromDatePid.setEditable(false);
		textFromDatePid.setBounds(0, 0, 76, 21);

		textFromDate = new Text(compositeFrom, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite composite = new Composite(compositeFrom, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite.setBounds(0, 0, 64, 64);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnNew = new Button(composite, SWT.NONE);
		btnNew.setText("New");

		Button btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse");

		Button btnClear = new Button(composite, SWT.NONE);
		btnClear.setText("Clear");

		Composite compositeTo = new Composite(container, SWT.BORDER);
		compositeTo.setLayout(new GridLayout(3, false));
		compositeTo.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblToDate = new Label(compositeTo, SWT.NONE);
		lblToDate.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblToDate.setBounds(0, 0, 55, 15);
		lblToDate.setText("To Date");

		textToDatePid = new Text(compositeTo, SWT.BORDER);
		textToDatePid.setEditable(false);
		textToDatePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textToDate = new Text(compositeTo, SWT.BORDER);
		textToDate.setEditable(false);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite composite_1 = new Composite(compositeTo, SWT.NONE);
		composite_1.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite_1.setSize(137, 31);
		composite_1.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button btnCopyFromDate = new Button(composite_1, SWT.NONE);
		btnCopyFromDate.setText("Copy From Date");

		Button button = new Button(composite_1, SWT.NONE);
		button.setText("New");

		Button button_1 = new Button(composite_1, SWT.NONE);
		button_1.setText("Browse");

		Button button_2 = new Button(composite_1, SWT.NONE);
		button_2.setText("Clear");

		Composite compositeLocation = new Composite(container, SWT.BORDER);
		compositeLocation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		compositeLocation.setLayout(new GridLayout(3, false));

		Label lblLocation = new Label(compositeLocation, SWT.NONE);
		lblLocation.setBounds(0, 0, 55, 15);
		lblLocation.setText("Location");

		textLocationPid = new Text(compositeLocation, SWT.BORDER);
		textLocationPid.setEditable(false);
		textLocationPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textLocation = new Text(compositeLocation, SWT.BORDER);
		textLocation.setEditable(false);
		textLocation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite composite_2 = new Composite(compositeLocation, SWT.NONE);
		composite_2.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite_2.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button button_3 = new Button(composite_2, SWT.NONE);
		button_3.setText("New");

		Button button_4 = new Button(composite_2, SWT.NONE);
		button_4.setText("Browse");

		Button button_5 = new Button(composite_2, SWT.NONE);
		button_5.setText("Clear");

	}

	/**
	 *
	 */
	protected void deleteSelectedEvent() {
		final int selectionIndex = table.getSelectionIndex();
		table.remove(selectionIndex);
		listOfLists.remove(selectionIndex);
	}

	/**
	 * @return
	 */
	public int getEventPid() {
// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the listOfLists
	 */
	public List<List<String>> getListOfLists() {
		return listOfLists;
	}

	/**
	 * @param listOfLists the listOfLists to set
	 */
	public void setListOfLists(List<List<String>> listOfLists) {
		this.listOfLists = listOfLists;
	}
}
