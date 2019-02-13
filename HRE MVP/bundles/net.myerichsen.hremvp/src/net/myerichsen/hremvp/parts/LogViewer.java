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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.providers.MvpLogProvider;

/**
 * Display the application log
 *
 * @version 2018-09-12
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 *
 */
public class LogViewer {
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Table tableLog;
	private MvpLogProvider provider;
	private TableViewer tableViewerLog;

	/**
	 * Create contents of the view part.
	 *
	 * @param parent The composite parent
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		tableViewerLog = new TableViewer(parent,
				SWT.BORDER | SWT.FULL_SELECTION);
		tableLog = tableViewerLog.getTable();
		tableLog.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		provider = new MvpLogProvider();
		tableViewerLog.setContentProvider(provider);
		LOGGER.fine("Created contentprovider");
		tableViewerLog
				.setInput("C:\\Program Files\\HRE\\eclipse\\.mvp-log.0.0.txt");
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
	private void subscribeKeyUpdateTopic(
			@UIEventTopic(Constants.LOG_REFRESH_UPDATE_TOPIC) int i) {
		LOGGER.fine("Update topic received");
		tableLog.removeAll();
		tableViewerLog.setContentProvider(provider);
		tableViewerLog
				.setInput("C:\\Program Files\\HRE\\eclipse\\.mvp-log.0.0.txt");
		tableLog.setTopIndex(tableLog.getItemCount() - 1);
	}

}
