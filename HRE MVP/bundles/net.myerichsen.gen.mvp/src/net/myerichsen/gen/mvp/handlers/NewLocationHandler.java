package net.myerichsen.gen.mvp.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import net.myerichsen.gen.mvp.wizards.NewLocationWizard;

/**
 * Open the new location wizard
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
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
		WizardDialog dialog = new WizardDialog(shell, new NewLocationWizard(context));
		dialog.open();

	}
}
