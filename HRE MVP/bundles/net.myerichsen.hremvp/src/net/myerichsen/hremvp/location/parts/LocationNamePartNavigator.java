package net.myerichsen.hremvp.location.parts;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreTypeLabelEditingSupport;
import net.myerichsen.hremvp.location.providers.LocationNamePartProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Maintain all parts of a location name
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 12. mar. 2019
 */
public class LocationNamePartNavigator {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private LocationNamePartProvider provider;
	private TableViewer tableViewer;
	private int locationNamePartPid = 0;

	@Inject
	private IEventBroker eventBroker;

	/**
	 * Constructor
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 *
	 */
	public LocationNamePartNavigator() throws Exception {
		provider = new LocationNamePartProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 * @throws Exception
	 */
	@PostConstruct
	public void createControls(Composite parent) throws Exception {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnPartNo = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPartNo = tableViewerColumnPartNo.getColumn();
		tblclmnPartNo.setWidth(100);
		tblclmnPartNo.setText("Part no.");
		tableViewerColumnPartNo.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnMap = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabelFromMap = tableViewerColumnMap
				.getColumn();
		tblclmnLabelFromMap.setWidth(100);
		tblclmnLabelFromMap.setText("Label from Map");
		tableViewerColumnMap.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnPart = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnValueFromPart = tableViewerColumnPart
				.getColumn();
		tblclmnValueFromPart.setWidth(100);
		tblclmnValueFromPart.setText("Value from Part");
		tableViewerColumnPart.setLabelProvider(new HREColumnLabelProvider(3));
		tableViewerColumnPart.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 3));

		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
				tableViewer, new FocusCellOwnerDrawHighlighter(tableViewer));
		ColumnViewerEditorActivationStrategy editorActivationStrategy = new ColumnViewerEditorActivationStrategy(
				tableViewer) {
			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED
								&& event.keyCode == SWT.CR)
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
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

					int itemCount = tableViewer.getTable().getItemCount();
					int selectionIndex = tableViewer.getTable()
							.getSelectionIndex();
					if (selectionIndex < itemCount - 1) {
						e.doit = false;

					} else {
						e.doit = true;
					}

				}
			}

		});

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLocationNamePart();
			}
		});
		buttonUpdate.setText("Update");

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(provider.getStringList(locationNamePartPid));
	}

	/**
	 * The object is not needed anymore, but not yet destroyed
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribeLocationNamePidUpdateTopic(
			@UIEventTopic(Constants.LOCATION_NAME_PID_UPDATE_TOPIC) int locationNamePartPid) {
		LOGGER.fine("Received location name id " + locationNamePartPid);

		if (locationNamePartPid == 0) {
			return;
		}

		this.locationNamePartPid = locationNamePartPid;

		try {
			provider.get(locationNamePartPid);
			tableViewer.setInput(provider.getStringList(locationNamePartPid));
			tableViewer.refresh();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	private void updateLocationNamePart() {
		String string;

		try {
			List<List<String>> locationNamePartList = provider
					.getStringList(locationNamePartPid);
			List<List<String>> input = (List<List<String>>) tableViewer
					.getInput();

			for (int i = 0; i < input.size(); i++) {
				string = input.get(i).get(0);
				if (string.equals(locationNamePartList.get(i).get(0))) {
					if (input.get(i).get(3).equals(
							locationNamePartList.get(i).get(3)) == false) {
						provider = new LocationNamePartProvider();
						provider.get(Integer.parseInt(string));
						provider.setLabel(input.get(i).get(3));
						provider.update();
					}
				}
//				for (final List<String> existingElement : stringList) {
//					if (input.get(i).get(2).equals(existingElement.get(0))) {
//						if ((input.get(i).get(3)
//								.equals(existingElement.get(1)) == false)) {
//							dp = new DictionaryProvider();
//							dp.setDictionaryPid(
//									Integer.parseInt(existingElement.get(2)));
//							dp.setIsoCode(input.get(i).get(2));
//							dp.setLabel(input.get(i).get(3));
//							dp.setLabelPid(provider.getLabelPid());
//							dp.setLabelType("SEX");
//							dp.update();
//							LOGGER.fine("Updated dictionary element "
//									+ input.get(i).get(0) + ", "
//									+ input.get(i).get(1) + ", "
//									+ input.get(i).get(2) + ", "
//									+ input.get(i).get(3));
//						}
//						break;
//					}
//				}
				eventBroker.post("MESSAGE", "Location name "
						+ locationNamePartPid + " has been updated");
			}
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}
