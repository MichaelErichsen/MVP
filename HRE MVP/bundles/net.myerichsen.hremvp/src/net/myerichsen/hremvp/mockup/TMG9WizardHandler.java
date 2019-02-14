package net.myerichsen.hremvp.mockup;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class TMG9WizardHandler {
	@Execute
	public void execute(Shell shell) {
		final WizardDialog wizardDialog = new WizardDialog(shell,
				new TMG9ProjectWizard());
		if (wizardDialog.open() == 0) {
			System.out.println("Ok pressed");
		} else {
			System.out.println("Cancel pressed");
		}
	}
}