package net.myerichsen.hremvp.person.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import net.myerichsen.hremvp.wizards.NewPersonWizard;

/**
 * Open the new person wizard
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 12. jan. 2019
 *
 */
public class NewPersonHandler {
	/**
	 * @param shell   The parent shell
	 * @param context The Eclipse Context
	 */
	@Execute
	public void execute(Shell shell, IEclipseContext context) {
		final WizardDialog dialog = new WizardDialog(shell, new NewPersonWizard(context));
		dialog.open();

	}
}
