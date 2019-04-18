package net.myerichsen.hremvp.project.parts;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.preference.IPreferenceStore;
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

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.project.providers.MvpLogProvider;

/**
 * Display the application log
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 18. apr. 2019
 */
public class LogViewer {
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final String logAddress;
	private Table table;
	private final MvpLogProvider provider;
	private TableViewer tableViewer;

	/**
	 * Constructor
	 *
	 */
	public LogViewer() {
		provider = new MvpLogProvider();
		logAddress = store.getString("LOGFILEPATH");
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
				tableViewer.setInput(logAddress);
				tableViewer.refresh();
				table.setTopIndex(table.getItemCount() - 1);
			}
		});
		mntmRefresh.setText("Refresh");

		tableViewer.setContentProvider(provider);
		tableViewer.setInput(logAddress);
	}

	/**
	 * @param key
	 */
	@Inject
	@Optional
	private void subscribeLogRefreshUpdateTopic(
			@UIEventTopic(Constants.LOG_REFRESH_UPDATE_TOPIC) int i) {
		LOGGER.log(Level.FINE, "Update topic received");
		tableViewer.setInput(logAddress);
		tableViewer.refresh();
		table.setTopIndex(table.getItemCount() - 1);
	}
}
