package net.myerichsen.hremvp.person.dialogs;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 21. mar. 2019
 *
 */
public class PersonDialog extends TitleAreaDialog {

	/**
	 * Constructor
	 *
	 * @param parentShell
	 */
	public PersonDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 *
	 * @param shell
	 * @param context
	 */
	public PersonDialog(Shell shell, IEclipseContext context) {
		super(shell);
		// TODO
	}

}
