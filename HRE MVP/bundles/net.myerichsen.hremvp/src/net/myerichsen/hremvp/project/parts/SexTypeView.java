package net.myerichsen.hremvp.project.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 19. feb. 2019
 *
 */
public class SexTypeView {

	public SexTypeView() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO Set the focus to control
	}

}
