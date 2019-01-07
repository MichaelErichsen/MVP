package net.myerichsen.hremvp.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.widgets.Composite;

/**
 * @author  Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 7. jan. 2019
 *
 */
public class PersonChildrenView {

	public PersonChildrenView() {
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
		// TODO	Set the focus to control
	}

}
