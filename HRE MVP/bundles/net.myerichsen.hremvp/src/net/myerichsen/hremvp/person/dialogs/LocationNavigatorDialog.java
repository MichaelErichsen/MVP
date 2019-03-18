package net.myerichsen.hremvp.person.dialogs;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Display all locations
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 5. mar. 2019
 *
 */
public class LocationNavigatorDialog extends TitleAreaDialog {

	/**
	 * Constructor
	 *
	 * @param shell
	 * @param context
	 */
	public LocationNavigatorDialog(Shell parentShell, IEclipseContext context) {
		super(parentShell);
	}

}
