package net.myerichsen.hremvp.handlers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 16. feb. 2019
 *
 */
public class HreUpdateHandler {
	private static final Logger LOGGER = Logger.getLogger("global");
	private final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	/**
	 * @param provisioningJob
	 * @param shell
	 * @param sync
	 * @param workbench
	 * @param monitor
	 */
	private void configureProvisioningJob(ProvisioningJob provisioningJob,
			final Shell shell, final UISynchronize sync,
			final IWorkbench workbench, IProgressMonitor monitor) {
		LOGGER.info("Configure Provisioning job");
		final SubMonitor subMonitor = SubMonitor.convert(monitor, 25);
		subMonitor.beginTask("Configure Provisioning Job", 25);

		provisioningJob.addJobChangeListener(new JobChangeAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.core.runtime.jobs.JobChangeAdapter#done(org.eclipse.
			 * core.runtime.jobs.IJobChangeEvent)
			 */
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					sync.syncExec(new Runnable() {
						/*
						 * (non-Javadoc)
						 * 
						 * @see java.lang.Runnable#run()
						 */
						@Override
						public void run() {
							final boolean restart = MessageDialog.openQuestion(
									shell, "Updates installed, restart?",
									"Updates for HRE have been installed from "
											+ store.getString("UPDATESITE")
											+ ". Do you want to restart?");
							if (restart) {
								workbench.restart();
							}
						}
					});
				} else {
					sync.syncExec(new Runnable() {
						/*
						 * (non-Javadoc)
						 * 
						 * @see java.lang.Runnable#run()
						 */
						@Override
						public void run() {
							MessageDialog.openWarning(shell, "Update failed",
									"Update of HRE from "
											+ store.getString("UPDATESITE")
											+ " have failed");
						}
					});
				}
				super.done(event);
			}
		});
		subMonitor.worked(25);
	}

	/**
	 * @param operation
	 * @param monitor
	 * @return
	 */
	private UpdateOperation configureUpdate(UpdateOperation operation,
			IProgressMonitor monitor) {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, 25);
		subMonitor.beginTask("Configure Update", 25);
		LOGGER.info("Configure Update");

		URI uri = null;
		try {
			uri = new URI(store.getString("UPDATESITE"));
		} catch (final URISyntaxException e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line "
					+ e.getStackTrace()[0].getLineNumber());
			return null;
		}

		operation.getProvisioningContext()
				.setArtifactRepositories(new URI[] { uri });
		operation.getProvisioningContext()
				.setMetadataRepositories(new URI[] { uri });
		subMonitor.worked(25);
		return operation;
	}

	/**
	 * @param agent
	 * @param shell
	 * @param sync
	 * @param workbench
	 */
	@Execute
	public void execute(final IProvisioningAgent agent, final Shell shell,
			final UISynchronize sync, final IWorkbench workbench) {
		LOGGER.info("Repository location: " + store.getString("UPDATESITE"));
		try {
			final ProgressMonitorDialog dialog = new ProgressMonitorDialog(
					shell);
			dialog.run(true, true, new IRunnableWithProgress() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.operation.IRunnableWithProgress#run(org.
				 * eclipse.core.runtime.IProgressMonitor)
				 */
				@Override
				public void run(IProgressMonitor monitor) {

					final SubMonitor subMonitor = SubMonitor.convert(monitor,
							100);

					LOGGER.info("Check for updates");

					final ProvisioningSession session = new ProvisioningSession(
							agent);
					final UpdateOperation operation = new UpdateOperation(
							session);
					configureUpdate(operation, subMonitor.split(25, 0));

					final IStatus status = resolveModal(monitor, operation,
							subMonitor.split(25, 0));

					if (status.getCode() == 10000) {
						showMessage(shell, sync);
						return;
					}

					final ProvisioningJob provisioningJob = getProvisioningJob(
							monitor, operation, subMonitor.split(25, 0));

					if (provisioningJob == null) {
						LOGGER.severe(
								"Trying to update from the Eclipse IDE? This won't work!");
						return;
					}
					configureProvisioningJob(provisioningJob, shell, sync,
							workbench, subMonitor.split(25, 0));

					provisioningJob.schedule();
				}
			});
		} catch (final InvocationTargetException e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
		} catch (final InterruptedException e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
		}
	}

	/**
	 * @param monitor
	 * @param operation
	 * @param monitor2
	 * @return
	 */
	private ProvisioningJob getProvisioningJob(IProgressMonitor monitor,
			UpdateOperation operation, IProgressMonitor monitor2) {
		final SubMonitor subMonitor = SubMonitor.convert(monitor2, 25);
		subMonitor.beginTask("Get Provisioning Job", 25);
		LOGGER.info("Get Provisioning Job");
		final ProvisioningJob provisioningJob = operation
				.getProvisioningJob(monitor);
		// FIXME job.getProvisioningPlan() != null
		subMonitor.worked(25);
		return provisioningJob;
	}

	/**
	 * @param monitor
	 * @param operation
	 * @param monitor2
	 * @return
	 */
	private IStatus resolveModal(IProgressMonitor monitor,
			UpdateOperation operation, IProgressMonitor monitor2) {
		final SubMonitor subMonitor = SubMonitor.convert(monitor2, 25);
		subMonitor.beginTask("Resolve Modal Operation", 25);
		LOGGER.info("Resolve Modal Operation");
		final IStatus status = operation.resolveModal(monitor);
		subMonitor.worked(25);
		return status;
	}

	/**
	 * @param parent
	 * @param sync
	 */
	private void showMessage(final Shell parent, UISynchronize sync) {
		LOGGER.info("Show message");
		sync.syncExec(new Runnable() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				MessageDialog.openWarning(parent, "No update",
						"No updates for HRE have been found at "
								+ store.getString("UPDATESITE"));
			}
		});
	}
}
