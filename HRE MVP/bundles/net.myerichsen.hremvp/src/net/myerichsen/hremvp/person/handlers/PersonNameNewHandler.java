
package net.myerichsen.hremvp.person.handlers;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.person.parts.PersonNavigator;
import net.myerichsen.hremvp.person.wizards.NewPersonNameWizard;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 8. feb. 2019
 *
 */
public class PersonNameNewHandler {
	private final static String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.person.parts.PersonNavigator";

	/**
	 * Find person pid and open a wizard to add another name to it
	 *
	 * @param workbench
	 * @param partService
	 * @param application
	 * @param modelService
	 * @param shell
	 */
	@Execute
	public void execute(IWorkbench workbench, EPartService partService,
			MApplication application, EModelService modelService, Shell shell,
			IEclipseContext context) {
		// Find selected person
		int personPid = 0;
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
					break;
				}
			}

			if (personPid > 0) {
				break;
			}
		}

		final WizardDialog dialog = new WizardDialog(shell,
				new NewPersonNameWizard(personPid, context));
		dialog.open();
	}
}