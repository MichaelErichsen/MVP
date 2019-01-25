package net.myerichsen.hremvp.event.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import net.myerichsen.hremvp.event.wizards.NewEventTypeWizard;

/**
 * Open the new personEvent type wizard
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 27. nov. 2018
 *
 */
public class NewEventTypeHandler {
	/**
	 * @param shell   The parent shell
	 * @param context The Eclipse Context
	 */
	@Execute
	public void execute(Shell shell, IEclipseContext context) {
		final WizardDialog dialog = new WizardDialog(shell, new NewEventTypeWizard(context));
		dialog.open();

	}
}
