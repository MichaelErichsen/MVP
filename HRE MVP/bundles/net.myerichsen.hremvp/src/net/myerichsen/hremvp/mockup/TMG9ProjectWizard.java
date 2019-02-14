package net.myerichsen.hremvp.mockup;

import org.eclipse.jface.wizard.Wizard;

public class TMG9ProjectWizard extends Wizard {
	protected TMG9ProjectWizardPage wizardPage;

	public TMG9ProjectWizard() {
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		wizardPage = new TMG9ProjectWizardPage();
		addPage(wizardPage);
	}

	@Override
	public String getWindowTitle() {
		return "Convert TMG9 Project";
	}

	@Override
	public boolean performFinish() {
		return true;
	}
}