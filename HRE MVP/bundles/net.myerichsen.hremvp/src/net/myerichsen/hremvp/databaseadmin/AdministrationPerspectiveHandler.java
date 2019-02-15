package net.myerichsen.hremvp.databaseadmin;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

/**
 * Switch perspective to administration perspective
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. nov. 2018
 *
 */
public class AdministrationPerspectiveHandler {
	/**
	 * @param app
	 * @param partService
	 * @param modelService
	 */
	@Execute
	public void execute(MApplication app, EPartService partService,
			EModelService modelService) {
		partService.switchPerspective(
				"net.myerichsen.hremvp.perspective.administrationperspective");
	}
}