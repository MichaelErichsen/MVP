package net.myerichsen.hremvp.providers;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;

/**
 * Default JFace table label provider
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 31. mar. 2019
 *
 */
public class HREColumnLabelProvider extends ColumnLabelProvider {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final int column;

	/**
	 * Constructor
	 *
	 * @param column
	 */
	public HREColumnLabelProvider(int column) {
		super();
		this.column = column;
	}

	/**
	 *
	 */
	public static void addEditingSupport(TableViewer tableViewer) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		@SuppressWarnings("unchecked")
		final List<String> list = (List<String>) element;
		return list.get(column);
	}

}
