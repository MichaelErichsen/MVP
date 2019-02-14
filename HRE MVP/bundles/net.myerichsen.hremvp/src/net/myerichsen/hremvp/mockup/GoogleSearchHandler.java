package net.myerichsen.hremvp.mockup;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class GoogleSearchHandler {
	protected static final Logger LOGGER = Logger.getLogger("global");

	@Execute
	public void execute(EPartService partService, MApplication application,
			EModelService modelService) {
		LOGGER.info("execute");

		final MPart part = MBasicFactory.INSTANCE.createPart();
		part.setLabel("Google Search");
		part.setCloseable(true);
		part.setContainerData("500");
		part.setVisible(true);
		part.setContributionURI(
				"bundleclass://org.historyresearchenvironment.usergui/org.historyresearchenvironment.usergui.parts.GoogleBrowser");
		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		stacks.get(stacks.size() - 1).getChildren().add(part);
		partService.showPart(part, EPartService.PartState.ACTIVATE);
		LOGGER.info("executed");
	}
}
