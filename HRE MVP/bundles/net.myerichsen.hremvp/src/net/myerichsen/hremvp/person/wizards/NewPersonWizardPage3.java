package net.myerichsen.hremvp.person.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.HreTypeLabelEditingSupport;
import net.myerichsen.hremvp.project.providers.PersonNameMapProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 12. apr. 2019
 *
 */
public class NewPersonWizardPage3 extends WizardPage {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private NewPersonWizard wizard;

	private final PersonNameMapProvider provider;
	private List<List<String>> lls;

	/**
	 * Constructor
	 *
	 */
	public NewPersonWizardPage3() {
		super("wizardPage");
		setTitle("Person Name Parts");
		setDescription("Enter each part of the name");
		provider = new PersonNameMapProvider();
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
		container.setLayout(new GridLayout(1, false));

		TableViewer tableViewer = new TableViewer(container,
				SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.
			 * events.FocusEvent)
			 */
			@Override
			public void focusLost(FocusEvent e) {
				LOGGER.log(Level.INFO, "Focus lost");
				final TableItem[] tableItems = table.getItems();
				Boolean found = false;
				final List<String> stringList = new ArrayList<>();

				for (int i = 0; i < tableItems.length; i++) {
					final String text = tableItems[i].getText(2);
					LOGGER.log(Level.INFO, "Table item {0}: {1}, {2}, {3}",
							new Object[] { i, tableItems[i].getText(0),
									tableItems[i].getText(1),
									tableItems[i].getText(2) });
					stringList.add(text);

					LOGGER.log(Level.INFO, "Stringlist {0}: {1}",
							new Object[] { i, text });

					if (text.length() > 0) {
						lls.get(i).set(4, text);
						found = true;
					}

					LOGGER.log(Level.INFO, "LLS {0}: {1}, {2}, {3}, {4}, {5}",
							new Object[] { i, lls.get(i).get(0),
									lls.get(i).get(1), lls.get(i).get(2),
									lls.get(i).get(3), lls.get(i).get(4) });
				}

				if (found) {
					wizard.setPersonNamePartList(stringList);
					setPageComplete(true);
				}

			}
		});

		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("Part no.");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabel = tableViewerColumnLabel.getColumn();
		tblclmnLabel.setWidth(100);
		tblclmnLabel.setText("Label");
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(3));

		final TableViewerColumn tableViewerColumnValue = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnValue = tableViewerColumnValue.getColumn();
		tblclmnValue.setWidth(300);
		tblclmnValue.setText("Value");
		tableViewerColumnValue.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 5));
		tableViewerColumnValue.setLabelProvider(new HREColumnLabelProvider(5));

		HREColumnLabelProvider.addEditingSupport(tableViewer);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			wizard = (NewPersonWizard) getWizard();
			final int personNameStylePid = wizard.getPersonNameStylePid();
			lls = provider.getStringList(personNameStylePid);
			tableViewer.setInput(lls);
			setErrorMessage(null);
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, e1.toString(), e1);
			setErrorMessage(e1.getMessage());
		}
		setPageComplete(false);
	}
}
