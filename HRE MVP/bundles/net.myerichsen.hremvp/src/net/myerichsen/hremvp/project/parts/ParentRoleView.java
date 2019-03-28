package net.myerichsen.hremvp.project.parts;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreTypeLabelEditingSupport;
import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.ParentRoleProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display a Parent Role with all language labels
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 28. mar. 2019
 *
 */

public class ParentRoleView {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Text textParentRolePid;
	private Text textLabelPid;
	private Text textAbbreviation;
	private TableViewer tableViewer;
	private final ParentRoleProvider provider;
	private DictionaryProvider dp;
	private int ParentRolePid = 0;
	private int labelPid = 0;
	private Text textParentTypePid;

	/**
	 * Constructor
	 *
	 */
	public ParentRoleView() {
		provider = new ParentRoleProvider();
	}

	/**
	 *
	 */
	private void addEditingSupport() {
		final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
				tableViewer, new FocusCellOwnerDrawHighlighter(tableViewer));
		final ColumnViewerEditorActivationStrategy editorActivationStrategy = new ColumnViewerEditorActivationStrategy(
				tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				return (event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL)
						|| (event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION)
						|| ((event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED)
								&& (event.keyCode == SWT.CR))
						|| (event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC);
			}
		};

		TableViewerEditor.create(tableViewer, focusCellManager,
				editorActivationStrategy,
				ColumnViewerEditor.TABBING_HORIZONTAL
						| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
						| ColumnViewerEditor.TABBING_VERTICAL
						| ColumnViewerEditor.KEYBOARD_ACTIVATION);

		tableViewer.getTable().addTraverseListener(new TraverseListener() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse.
			 * swt.events.TraverseEvent)
			 */
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.keyCode == SWT.TAB) {
					LOGGER.fine("Traversed " + e.keyCode);

					final int itemCount = tableViewer.getTable().getItemCount();
					final int selectionIndex = tableViewer.getTable()
							.getSelectionIndex();
					if (selectionIndex < (itemCount - 1)) {
						e.doit = false;

					} else {
						e.doit = true;
					}

				}
			}

		});
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(4, false));

		final Label lblParentRoleId = new Label(parent, SWT.NONE);
		lblParentRoleId.setText("Parent Role id");

		textParentRolePid = new Text(parent, SWT.BORDER);
		textParentRolePid.setEditable(false);
		textParentRolePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblLabelPid = new Label(parent, SWT.NONE);
		lblLabelPid.setText("Label id");

		textLabelPid = new Text(parent, SWT.BORDER);
		textLabelPid.setEditable(false);
		textLabelPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblParentTypeId = new Label(parent, SWT.NONE);
		lblParentTypeId.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblParentTypeId.setText("Parent Type id");

		textParentTypePid = new Text(parent, SWT.BORDER);
		textParentTypePid.setEditable(false);
		textParentTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		final Label lblAbbreviation = new Label(parent, SWT.NONE);
		lblAbbreviation.setText("Abbreviation");

		textAbbreviation = new Text(parent, SWT.BORDER);
		textAbbreviation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(80);
		tblclmnId.setText("Parent Role Id");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabelId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnILabelId = tableViewerColumnLabelId
				.getColumn();
		tblclmnILabelId.setWidth(80);
		tblclmnILabelId.setText("Dictionary label Id");
		tableViewerColumnLabelId
				.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnIsoCode = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnIsoCode = tableViewerColumnIsoCode.getColumn();
		tblclmnIsoCode.setWidth(80);
		tblclmnIsoCode.setText("ISO Code");
		tableViewerColumnIsoCode
				.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabel = tableViewerColumnLabel.getColumn();
		tblclmnLabel.setWidth(394);
		tblclmnLabel.setText("Parent label");
		tableViewerColumnLabel.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 3));
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(3));

		addEditingSupport();

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));

		final Button btnUpdate = new Button(composite, SWT.NONE);
		btnUpdate.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				updateParentRole();
			}
		});
		btnUpdate.setText("Update");

		try {
			provider.get();
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
			tableViewer.setInput(provider.getStringList(labelPid));
		} catch (final Exception e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
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
	@Focus
	public void setFocus() {
	}

	/**
	 * @param ls A list of Parent role pid, Parent type pid, dictionary pid
	 *           and abbreviation
	 *
	 */
	@Inject
	@Optional
	private void subscribeLabelPidUpdateTopic(
			@UIEventTopic(Constants.LABEL_PID_UPDATE_TOPIC) List<String> ls) {

		try {
			provider.get();
			final String ParentRolePidString = ls.get(0);
			textParentRolePid.setText(ParentRolePidString);
			final String ParentTypePidString = ls.get(1);
			textParentTypePid.setText(ParentTypePidString);
			ParentRolePid = Integer.parseInt(ParentRolePidString);
			textLabelPid.setText(ls.get(2));
			textAbbreviation.setText(ls.get(3));

			provider.get(ParentRolePid);
			labelPid = provider.getLabelPid();
			tableViewer.setInput(provider.getStringList(labelPid));
			tableViewer.refresh();
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	protected void updateParentRole() {
		if (textAbbreviation.getText().isEmpty()) {
			eventBroker.post("MESSAGE", "Abbreviation must not be empty");
			textAbbreviation.setFocus();
			return;
		}

		try {
			final List<List<String>> ParentRoleList = provider
					.getStringList(labelPid);

			ParentRolePid = Integer.parseInt(ParentRoleList.get(0).get(0));
			labelPid = Integer.parseInt(ParentRoleList.get(0).get(1));

			provider.get(ParentRolePid);
			provider.setAbbreviation(textAbbreviation.getText());
			provider.update();
			LOGGER.info("Parent pid " + ParentRolePid + " has been updated");

			dp = new DictionaryProvider();
			final List<List<String>> stringList = dp.getStringList(labelPid);

			final List<List<String>> input = (List<List<String>>) tableViewer
					.getInput();

			for (int i = 0; i < input.size(); i++) {
				for (final List<String> existingElement : stringList) {
					LOGGER.info(input.get(i).get(2) + ", " + input.get(i).get(3)
							+ " - " + existingElement.get(0) + ", "
							+ existingElement.get(1) + ", "
							+ existingElement.get(2));

					if (input.get(i).get(2).equals(existingElement.get(0))) {
						if ((input.get(i).get(3)
								.equals(existingElement.get(1)) == false)) {
							dp = new DictionaryProvider();
							dp.setDictionaryPid(
									Integer.parseInt(existingElement.get(2)));
							dp.setIsoCode(input.get(i).get(2));
							dp.setLabel(input.get(i).get(3));
							dp.setLabelPid(provider.getLabelPid());
							dp.setLabelType("ParentROLE");
							dp.update();
							LOGGER.info("Updated dictionary element "
									+ input.get(i).get(0) + ", "
									+ input.get(i).get(1) + ", "
									+ input.get(i).get(2) + ", "
									+ input.get(i).get(3));
						}
						break;
					}
				}
			}
			eventBroker.post("MESSAGE",
					"Parent Role " + ParentRolePid + " has been updated");
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}
