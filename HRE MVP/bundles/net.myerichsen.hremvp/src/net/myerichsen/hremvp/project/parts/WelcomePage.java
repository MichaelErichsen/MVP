package net.myerichsen.hremvp.project.parts;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.wb.swt.SWTResourceManager;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * Application welcome page
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 28. apr. 2019
 *
 */
public class WelcomePage {
	final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	public WelcomePage() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		parent.setLayout(new GridLayout(2, false));

		StyledText styledTextHeader = new StyledText(parent, SWT.WRAP);
		styledTextHeader.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		styledTextHeader.setRightMargin(10);
		styledTextHeader.setLeftMargin(10);
		styledTextHeader.setTopMargin(10);
		styledTextHeader.setBottomMargin(10);
		styledTextHeader
				.setFont(SWTResourceManager.getFont("Segoe UI", 24, SWT.BOLD));
		styledTextHeader.setText("Welcome to the HRE MVP");
		styledTextHeader.setDoubleClickEnabled(false);
		styledTextHeader.setEditable(false);

		StyledText styledTextBody = new StyledText(parent, SWT.WRAP);
		styledTextBody.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		styledTextBody.setIndent(10);
		styledTextBody.setBottomMargin(10);
		styledTextBody.setLeftMargin(10);
		styledTextBody.setRightMargin(10);
		styledTextBody.setDoubleClickEnabled(false);
		styledTextBody.setEditable(false);
		styledTextBody.setFont(
				SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		styledTextBody.setText(
				"This is an attempt of a Minumum Viable Product for the History Research Environment system.\n"
						+ "It is built upon a reduced version of the HRE data model.\n"
						+ "A fundamental feature is that (almost) any entity can have any number of relations, "
						+ "such as many locations for an event, and many events for a location.\n"
						+ "The entity names are not aligned to the HRE data model, "
						+ "but more in line with TMG (The Master Genealogist).\n"
						+ "The application is not (yet) built for completely "
						+ "mouseless operation. If you are in doubt of what to do in any "
						+ "window, please try to right click or double click.\n"
						+ "You can create new projects with a basic set of master data, "
						+ "such as languages, person name styles etc., which can be extended as you wish."
						+ "The application is a stand-alone application, based on Java, "
						+ "Eclipse E4, Eclipse Standard Widget Toolkit and JFace, and "
						+ "the H2 relational database.\nIt can also run as a server. "
						+ "If you start the embedded server from the Files menu item, "
						+ "you can access some of the application using a REST and JSON "
						+ "interface. If you use a Firefox web browser you can point it "
						+ "to the server link to access parts of MVP.\nThis feature has "
						+ "two purposes:\nTo be able to run HRE MVP on separate client "
						+ "and server machines\nTo be able to build mobile front ends "
						+ "to HRE MVP from Android and other devices.");

		Label label = new Label(parent, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		label.setText("Server Link:");

		Link link = new Link(parent, SWT.NONE);
		link.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		link.setText("<a>http://" + store.getString("SERVERADDRESS") + ":"
				+ store.getString("SERVERPORT") + "</a>");

	}
}