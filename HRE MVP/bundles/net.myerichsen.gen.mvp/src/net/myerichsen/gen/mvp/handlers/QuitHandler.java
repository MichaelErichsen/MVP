package net.myerichsen.gen.mvp.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to shut down HRE.
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 1. jan. 2019
 *
 */
public class QuitHandler {
	@Execute
	public void execute(IWorkbench workbench, Shell shell) {
		if (MessageDialog.openConfirm(shell, "Confirmation", "Do you want to exit?")) {
			workbench.close();
		}
	}
}
