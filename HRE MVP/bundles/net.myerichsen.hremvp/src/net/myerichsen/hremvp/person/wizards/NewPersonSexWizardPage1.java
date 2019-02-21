package net.myerichsen.hremvp.person.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.person.dialogs.SexTypeNavigatorDialog;
import net.myerichsen.hremvp.project.providers.SexTypeProvider;

/**
 * Person sex wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 17. feb. 2019
 *
 */
public class NewPersonSexWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private Text textSexTypePid;
	private Text textSex;
	private int sexTypePid;
	private Button btnCheckButtonPrimary;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonSexWizardPage1(IEclipseContext context) {
		super("New Person Sex Wizard Page 1");
		setTitle("Sex");
		setDescription("Add a sex for the person.");
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 *
	 */
	protected void browseSexTypes() {
		try {
			final SexTypeNavigatorDialog dialog = new SexTypeNavigatorDialog(
					textSexTypePid.getShell(), context);
			if (dialog.open() == Window.OK) {

				sexTypePid = dialog.getSexTypePid();
				textSexTypePid.setText(Integer.toString(sexTypePid));

				final SexTypeProvider provider = new SexTypeProvider();
				provider.get(sexTypePid);
				textSex.setText(provider.getLabel());
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void clearSex() {
		textSexTypePid.setText("");
		textSex.setText("");
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		final Label lblSex = new Label(container, SWT.NONE);
		lblSex.setText("Sex");

		textSexTypePid = new Text(container, SWT.BORDER);
		textSexTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textSexTypePid.setToolTipText("More sexes can be added later");
		textSexTypePid.setEditable(false);
		textSexTypePid.setToolTipText("Sex must be selected to continue");

		textSex = new Text(container, SWT.BORDER);
		textSex.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textSex.setEditable(false);
		new Label(container, SWT.NONE);

		final Composite compositeSex = new Composite(container, SWT.NONE);
		compositeSex.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeSex.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnBrowseSexes = new Button(compositeSex, SWT.NONE);
		btnBrowseSexes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseSexTypes();
				setPageComplete(true);
			}
		});
		btnBrowseSexes.setText("Browse");

		final Button btnClearSex = new Button(compositeSex, SWT.NONE);
		btnClearSex.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearSex();
				setPageComplete(false);
			}
		});
		btnClearSex.setText("Clear");
		new Label(container, SWT.NONE);

		btnCheckButtonPrimary = new Button(container, SWT.CHECK);
		btnCheckButtonPrimary.setText("Primary");
		new Label(container, SWT.NONE);

		setPageComplete(false);

	}

	/**
	 * @return the sexTypePid
	 */
	public int getSexTypePid() {
		return sexTypePid;
	}

	/**
	 * @return the btnCheckButtonPrimary
	 */
	public boolean isPrimary() {
		return btnCheckButtonPrimary.getSelection();
	}

	/**
	 * @param sexPid The persistent id of the sex type
	 */
	public void setSexTypePid(int sexPid) {
		sexTypePid = sexPid;
	}
}
