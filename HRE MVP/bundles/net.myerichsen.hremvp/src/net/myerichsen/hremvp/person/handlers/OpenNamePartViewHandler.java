package net.myerichsen.hremvp.person.handlers;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

/**
 * Handler to open the person Name Part view
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 26. mar. 2019
 */
public class OpenNamePartViewHandler {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final static String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.person.parts.PersonNamePartView";

	/**
	 * @param partService  The Eclipse part service
	 * @param application  The Eclipse Application
	 * @param modelService The Eclipse model service
	 *
	 */
	@Execute
	public void execute(EPartService partService, MApplication application,
			EModelService modelService) {
		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		MPart part = MBasicFactory.INSTANCE.createPart();

		for (final MPartStack mPartStack : stacks) {
			final List<MStackElement> a = mPartStack.getChildren();

			for (int i = 0; i < a.size(); i++) {
				part = (MPart) a.get(i);

				try {
					if (part.getContributionURI().equals(contributionURI)) {
						partService.showPart(part, PartState.ACTIVATE);
						return;
					}
				} catch (final Exception e) {
					LOGGER.severe(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		part.setLabel("Name Part");
		part.setContainerData("650");
		part.setCloseable(true);
		part.setVisible(true);
		part.setContributionURI(contributionURI);
		stacks.get(stacks.size() - 2).getChildren().add(part);
		partService.showPart(part, PartState.ACTIVATE);
		LOGGER.fine("Opening new window");
	}

}