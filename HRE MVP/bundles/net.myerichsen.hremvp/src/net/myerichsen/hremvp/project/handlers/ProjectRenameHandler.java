
package net.myerichsen.hremvp.project.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 27. jan. 2019
 *
 */
public class ProjectRenameHandler {
	/**
	 * @param workbench
	 * @param shell
	 */
	@Execute
	public void execute(IWorkbench workbench, Shell shell) {
		final MessageDialog dialog = new MessageDialog(shell, "Rename", null,
				"Not yet implemented", MessageDialog.INFORMATION, 0,
				new String[] { "OK" });
		dialog.open();
	}

}