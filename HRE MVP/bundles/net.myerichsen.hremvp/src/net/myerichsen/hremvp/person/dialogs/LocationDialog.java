package net.myerichsen.hremvp.person.dialogs;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Create a new location
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 5. mar. 2019
 *
 */
public class LocationDialog extends TitleAreaDialog {

	/**
	 * Constructor
	 *
	 * @param shell
	 * @param context
	 */
	public LocationDialog(Shell parentShell, IEclipseContext context) {
		super(parentShell);
	}

}
