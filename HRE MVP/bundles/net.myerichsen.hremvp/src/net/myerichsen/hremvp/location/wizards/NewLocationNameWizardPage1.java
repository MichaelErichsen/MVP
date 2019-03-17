package net.myerichsen.hremvp.location.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
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

import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.project.providers.LocationNameStyleProvider;
import net.myerichsen.hremvp.providers.HDateProvider;
import net.myerichsen.hremvp.providers.HREComboLabelProvider;

/**
 * New location name wizard page 1
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 16. mar. 2019
 *
 */
public class NewLocationNameWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final IEclipseContext context;

	private ComboViewer comboViewerLocationNameStyles;
	private Text textFromDate;
	private Text textToDate;
	private Text textPreposition;
	private Button btnPrimaryLocationName;

	private int fromDatePid;
	private int toDatePid;
	private int locationNameStylePid;
	protected List<List<String>> styleList;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse Context
	 *
	 */
	public NewLocationNameWizardPage1(IEclipseContext context) {
		super("wizardPage");
		this.context = context;
		setTitle("Location Name");
		setDescription(
				"Add the name style, optionally time limitations for the name of the location, and a preposition for reporting use.\r\n");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets. Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		final Label lblLocationNameStyle = new Label(container, SWT.NONE);
		lblLocationNameStyle.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLocationNameStyle.setText("Location Name Style");

		comboViewerLocationNameStyles = new ComboViewer(container, SWT.NONE);
		final Combo comboLocationNameStyles = comboViewerLocationNameStyles
				.getCombo();
		comboLocationNameStyles.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int selectionIndex = comboLocationNameStyles
						.getSelectionIndex();
				locationNameStylePid = Integer
						.parseInt(styleList.get(selectionIndex).get(0));

				final NewLocationNameWizard wizard = (NewLocationNameWizard) getWizard();
				wizard.setLocationNameStylePid(locationNameStylePid);
				setPageComplete(true);
				wizard.addPage2();
				wizard.getContainer().updateButtons();
			}
		});
		comboLocationNameStyles.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboViewerLocationNameStyles
				.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerLocationNameStyles
				.setLabelProvider(new HREComboLabelProvider(2));
		comboLocationNameStyles.setToolTipText("Mandatory");

		final Composite compositeFrom = new Composite(container, SWT.BORDER);
		compositeFrom.setLayout(new GridLayout(2, false));
		compositeFrom.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblFromDate = new Label(compositeFrom, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(compositeFrom, SWT.BORDER);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textFromDate.setEditable(false);

		final Composite compositeFromButtons = new Composite(compositeFrom,
				SWT.NONE);
		compositeFromButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeFromButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewFrom = new Button(compositeFromButtons, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						fromDatePid = hdp.insert();
						textFromDate.setText(dialog.getDate().toString());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
					}
				}
			}
		});
		btnNewFrom.setText("New");

		final Button btnBrowseFrom = new Button(compositeFromButtons, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textFromDate.setText(hdp.getDate().toString());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseFrom.setText("Browse");

		final Button btnClearFrom = new Button(compositeFromButtons, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				textFromDate.setText("");
			}
		});
		btnClearFrom.setText("Clear");

		final Composite compositeTo = new Composite(container, SWT.BORDER);
		compositeTo.setLayout(new GridLayout(2, false));
		compositeTo.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblToDate = new Label(compositeTo, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(compositeTo, SWT.BORDER);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textToDate.setEditable(false);

		final Composite compositeToButtons = new Composite(compositeTo,
				SWT.NONE);
		compositeToButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeToButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnCopyFromTo = new Button(compositeToButtons, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText(textFromDate.getText());
			}
		});
		btnCopyFromTo.setText("Copy From");

		final Button btnNewTo = new Button(compositeToButtons, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textToDate.getShell(),
						context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						toDatePid = hdp.insert();
						textToDate.setText(dialog.getDate().toString());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
					}
				}
			}
		});
		btnNewTo.setText("New");

		final Button btnBrowseTo = new Button(compositeToButtons, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textToDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textToDate.setText(hdp.getDate().toString());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseTo.setText("Browse");

		final Button btnClearTo = new Button(compositeToButtons, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		btnClearTo.setText("Clear");

		new Label(container, SWT.NONE);

		btnPrimaryLocationName = new Button(container, SWT.CHECK);
		btnPrimaryLocationName.setSelection(false);
		btnPrimaryLocationName.setText("Primary Location Name");

		final Label lblPreposition = new Label(container, SWT.NONE);
		lblPreposition.setText("Preposition");

		textPreposition = new Text(container, SWT.BORDER);
		textPreposition.setToolTipText("Optional. Used for reporting");
		textPreposition.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		try {
			// Populate location name style combo box
			styleList = new LocationNameStyleProvider().getStringList();
			comboViewerLocationNameStyles.setInput(styleList);
			final int llsSize = styleList.size();
			final String g = store.getString("DEFAULTLOCATIONNAMESTYLE");
			int index = 0;

			for (int i = 0; i < llsSize; i++) {
				if (g.equals(styleList.get(i).get(1))) {
					index = i;
				}
			}
			comboLocationNameStyles.select(index);

		} catch (final Exception e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}

		setPageComplete(false);
	}

	/**
	 * @return the btnPrimaryLocationName
	 */
	public Button getBtnPrimaryLocationName() {
		return btnPrimaryLocationName;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getComboLocationNameStyle() {
		IStructuredSelection structuredSelection = comboViewerLocationNameStyles
				.getStructuredSelection();
		List<String> ls = (List<String>) structuredSelection.getFirstElement();
		return ls.get(0);
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @return the locationNameStylePid
	 */
	public int getLocationNameStylePid() {
		return locationNameStylePid;
	}

	/**
	 * @return the textFromDate
	 */
	public Text getTextFromDate() {
		return textFromDate;
	}

	/**
	 * @return the textPreposition
	 */
	public Text getTextPreposition() {
		return textPreposition;
	}

	/**
	 * @return the textToDate
	 */
	public Text getTextToDate() {
		return textToDate;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param locationNameStylePid the locationNameStylePid to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		this.locationNameStylePid = locationNameStylePid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}
}