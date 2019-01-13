package net.myerichsen.hremvp.location.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import net.myerichsen.hremvp.wizards.NewLocationWizard;

/**
 * Open the new location wizard
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 31. okt. 2018
 *
 */
public class NewLocationHandler {
	/**
	 * @param shell   The parent shell
	 * @param context The Eclipse Context
	 */
	@Execute
	public void execute(Shell shell, IEclipseContext context) {
		final WizardDialog dialog = new WizardDialog(shell, new NewLocationWizard(context));
		dialog.open();

	}
}
