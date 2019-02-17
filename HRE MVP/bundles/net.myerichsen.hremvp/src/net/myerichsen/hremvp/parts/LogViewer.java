package net.myerichsen.hremvp.parts;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.providers.MvpLogProvider;

/**
 * Display the application log
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 15. feb. 2019
 */
public class LogViewer {
	/**
	 *
	 */
	private static final String LOG_ADDRESS = "C:\\Program Files\\HRE\\eclipse\\.mvp-log.0.0.txt";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Table table;
	private final MvpLogProvider provider;
	private TableViewer tableViewer;

	/**
	 * Constructor
	 *
	 */
	public LogViewer() {
		provider = new MvpLogProvider();
	}

	/**
	 * Create contents of the view part.
	 *
	 * @param parent The composite parent
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final Menu menu = new Menu(table);
		table.setMenu(menu);

		final MenuItem mntmRefresh = new MenuItem(menu, SWT.NONE);
		mntmRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableViewer.setInput(LOG_ADDRESS);
				tableViewer.refresh();
				table.setTopIndex(table.getItemCount() - 1);
			}
		});
		mntmRefresh.setText("Refresh");

		tableViewer.setContentProvider(provider);
		tableViewer.setInput(LOG_ADDRESS);
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
	 * @param key
	 */
	@Inject
	@Optional
	private void subscribeLogRefreshUpdateTopic(
			@UIEventTopic(Constants.LOG_REFRESH_UPDATE_TOPIC) int i) {
		LOGGER.fine("Update topic received");
		tableViewer.setInput(LOG_ADDRESS);
		tableViewer.refresh();
		table.setTopIndex(table.getItemCount() - 1);
	}
}
