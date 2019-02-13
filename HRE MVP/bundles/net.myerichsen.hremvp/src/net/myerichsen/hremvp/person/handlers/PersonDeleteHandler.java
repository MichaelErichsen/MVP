package net.myerichsen.hremvp.person.handlers;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.person.parts.PersonNavigator;
import net.myerichsen.hremvp.person.providers.PersonProvider;

/**
 * Delete the selected person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 6. feb. 2019
 *
 */
public class PersonDeleteHandler {
	@Inject
	private static IEventBroker eventBroker;

	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final static String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.person.parts.PersonNavigator";

	/**
	 * Select a person and delete it
	 *
	 * @param workbench
	 * @param partService
	 * @param application
	 * @param modelService
	 * @param shell
	 */
	@Execute
	public void execute(IWorkbench workbench, EPartService partService,
			MApplication application, EModelService modelService, Shell shell) {
		// Find selected person
		int personPid = 0;
		String primaryName = "";
		TableViewer viewer = null;

		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		MPart part = MBasicFactory.INSTANCE.createPart();

		for (final MPartStack mPartStack : stacks) {
			final List<MStackElement> a = mPartStack.getChildren();

			for (int i = 0; i < a.size(); i++) {
				part = (MPart) a.get(i);
				if (part.getContributionURI().equals(contributionURI)) {
					final PersonNavigator pn = (PersonNavigator) part
							.getObject();
					viewer = pn.getTableViewer();
					final TableItem[] selection = viewer.getTable()
							.getSelection();
					final TableItem item = selection[0];
					personPid = Integer.parseInt(item.getText(0));
					primaryName = item.getText(1);
					LOGGER.info("Selected: " + personPid + " " + primaryName);
					break;
				}
			}

			if (personPid > 0) {
				break;
			}
		}

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell,
				"Delete Person " + primaryName, null,
				"Are you sure that you will delete person " + personPid + ", "
						+ primaryName + "?",
				MessageDialog.CONFIRM, 0, new String[] { "OK", "Cancel" });

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post("MESSAGE",
					"Deletion of person " + primaryName + " has been canceled");
			return;
		}

		try {
			final PersonProvider provider = new PersonProvider();
			provider.delete(personPid);

			LOGGER.info("Person " + primaryName + " has been deleted");
			eventBroker.post("MESSAGE",
					"Person " + primaryName + " has been deleted");
			eventBroker.post(Constants.PERSON_LIST_UPDATE_TOPIC, personPid);
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}