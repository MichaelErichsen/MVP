///*     */ package org.historyresearchenvironment.usergui.wizards;
//
///*     */ import java.util.logging.Logger;
//
///*     */ import org.apache.commons.io.FilenameUtils;
///*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
///*     */ import org.eclipse.jface.dialogs.ProgressMonitorDialog;
///*     */ import org.eclipse.jface.wizard.WizardPage;
///*     */ import org.eclipse.swt.events.KeyEvent;
///*     */ import org.eclipse.swt.events.KeyListener;
///*     */ import org.eclipse.swt.events.SelectionAdapter;
///*     */ import org.eclipse.swt.events.SelectionEvent;
///*     */ import org.eclipse.swt.graphics.Font;
///*     */ import org.eclipse.swt.graphics.FontData;
///*     */ import org.eclipse.swt.layout.GridData;
///*     */ import org.eclipse.swt.layout.GridLayout;
///*     */ import org.eclipse.swt.widgets.Button;
///*     */ import org.eclipse.swt.widgets.Composite;
///*     */ import org.eclipse.swt.widgets.FileDialog;
///*     */ import org.eclipse.swt.widgets.Label;
///*     */ import org.eclipse.swt.widgets.Text;
///*     */ import org.eclipse.wb.swt.ResourceManager;
///*     */ import org.eclipse.wb.swt.SWTResourceManager;
///*     */ import org.historyresearchenvironment.usergui.providers.TMG9Provider;
//
///*     */
///*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
//
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */ public class TMG9ProjectWizardPage/*     */ extends WizardPage
///*     */ {
//	 private static final Logger LOGGER = Logger.getLogger("global");
//	 private final ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
//			 "org.historyresearchenvironment.usergui");
//	/*     */
//	/*     */ private Text textProject;
//	/*     */
//	/*     */ private Composite container;
//	/*     */
//	/*     */ private Button btnBrowse;
//	/*     */ private Label lblHDatabaseName;
//	/*     */ private Text textDbname;
//	/*     */ private Button btnSelect;
//	/*     */ private Label lblNewLabel;
//	/*     */ private String name;
//	/*     */ private String project;
//	/*     */ private String dbName;
//
//	/*     */
//	/*     */ public TMG9ProjectWizardPage()
//	/*     */ {
//		 super("Wizard Page");
//		 setImageDescriptor( ResourceManager
//				.getPluginImageDescriptor("org.historyresearchenvironment.usergui", "icons/TMG163.png"));
//		 setTitle("TMG 9 Project Conversion to H2 Database");
//		 setDescription(
//				 "Select a The Master Genealogist Version 9 Project\r\nSelect a H2 Database Name and Location\r\n");
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void createControl(final Composite parent)
//	/*     */ {
//		 this.container = new Composite(parent, 0);
//		 this.container.setFont(getHreFont(parent));
//		 GridLayout layout = new GridLayout();
//		 this.container.setLayout(layout);
//		 layout.numColumns = 2;
//		 Label labelProject = new Label(this.container, 0);
//		 labelProject.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
//		 labelProject.setText("TMG9 Project:");
//		/*     */
//		 this.textProject = new Text(this.container, 2052);
//		 this.textProject.setEditable(false);
//		 this.textProject.setText("");
//		 this.textProject.addKeyListener(new KeyListener()
//		/*     */ {
//			/*     */ public void keyPressed(KeyEvent e) {
//			}
//
//			/*     */
//			/*     */
//			/*     */
//			/*     */ public void keyReleased(KeyEvent e)
//			/*     */ {
//				 if (!TMG9ProjectWizardPage.this.textProject.getText().isEmpty()) {
//					 TMG9ProjectWizardPage.this.setPageComplete(true);
//					/*     */ }
//				/*     */
//				/*     */ }
//			 });
//		 this.textProject.setLayoutData(new GridData(768));
//		/*     */
//		/*     */
//		 setControl(this.container);
//		/*     */
//		 this.btnBrowse = new Button(this.container, 8);
//		 this.btnBrowse.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
//		 this.btnBrowse.addSelectionListener(new SelectionAdapter()
//		/*     */ {
//			/*     */ public void widgetSelected(SelectionEvent e) {
//				 FileDialog fd = new FileDialog(parent.getShell(), 4096);
//				 fd.setText("Open");
//				 fd.setFilterPath(".");
//				 String[] filterExt = { "*.pjc", "*.*" };
//				 fd.setFilterExtensions(filterExt);
//				 TMG9ProjectWizardPage.this.project = fd.open();
//				 TMG9ProjectWizardPage.this.textProject.setText(TMG9ProjectWizardPage.this.project);
//				 TMG9ProjectWizardPage.this.name = FilenameUtils
//						.getBaseName(TMG9ProjectWizardPage.this.project).replace("_", "");
//				 TMG9ProjectWizardPage.LOGGER.info("Project: " + TMG9ProjectWizardPage.this.project);
//				/*     */ }
//			 });
//		 this.btnBrowse.setText("Browse");
//		/*     */
//		 this.lblNewLabel = new Label(this.container, 0);
//		/*     */
//		 this.lblHDatabaseName = new Label(this.container, 0);
//		 this.lblHDatabaseName.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
//		 this.lblHDatabaseName.setText("H2 Database name:");
//		/*     */
//		 this.textDbname = new Text(this.container, 2048);
//		 this.textDbname.setEditable(false);
//		 this.textDbname.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
//		/*     */
//		 this.btnSelect = new Button(this.container, 0);
//		 this.btnSelect.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
//		 this.btnSelect.addSelectionListener(new SelectionAdapter()
//		/*     */ {
//			/*     */ public void widgetSelected(SelectionEvent e) {
//				 FileDialog fd = new FileDialog(parent.getShell(), 8192);
//				 fd.setText("Create");
//				 fd.setFileName(TMG9ProjectWizardPage.this.name);
//				 fd.setFilterPath(".");
//				 String[] filterExt = { "*.h2.db", "*.*" };
//				 fd.setFilterExtensions(filterExt);
//				 TMG9ProjectWizardPage.this.dbName = fd.open();
//				 TMG9ProjectWizardPage.this.textDbname.setText(TMG9ProjectWizardPage.this.dbName);
//				 TMG9ProjectWizardPage.this.dbName = TMG9ProjectWizardPage.this.dbName.replace(".h2.db", "");
//				 TMG9ProjectWizardPage.LOGGER.info("Database; " + TMG9ProjectWizardPage.this.dbName);
//				 TMG9ProjectWizardPage.this.setPageComplete(true);
//				/*     */
//				 TMG9ProjectWizardPage.this.runLoadJobs(parent);
//				/*     */ }
//			 });
//		 this.btnSelect.setText("Select");
//		 new Label(this.container, 0);
//		 setPageComplete(false);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ private Font getHreFont(Composite parent)
//	/*     */ {
//		 LOGGER.fine("Get HRE Font");
//		/*     */ Font font;
//		/*     */ try
//		/*     */ {
//			 String s = this.store.getString("HREFONT");
//			/*     */
//			 LOGGER.fine(s);
//			 String[] sa = s.split("-");
//			 for (int i = 0; i < sa.length; i++) {
//				 LOGGER.fine("sa[" + i + "]: " + sa[i]);
//				/*     */ }
//			 String[] sb = sa[0].split("\\|");
//			 for (int i = 0; i < sb.length; i++) {
//				 LOGGER.fine("sb[" + i + "]: " + sb[i]);
//				/*     */ }
//			 LOGGER
//					.fine("HRE font: " + sb[1] + " " + Math.round(Double.valueOf(sb[2]).doubleValue()) + " " + sb[3]);
//			 font = SWTResourceManager.getFont(sb[1], (int) Math.round(Double.valueOf(sb[2]).doubleValue()),
//					Integer.parseInt(sb[3]));
//			/*     */ } catch (NumberFormatException e) {
//			Font font;
//			 LOGGER
//					.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
//			 e.printStackTrace();
//			/*     */
//			 font = parent.getShell().getFont();
//			 FontData fd = font.getFontData()[0];
//			 font = SWTResourceManager.getFont(fd.getName(), 12, fd.getStyle());
//			/*     */ }
//		/*     */
//		 return font;
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */ private void runLoadJobs(Composite parent)
//	/*     */ {
//		/*     */ try
//		/*     */ {
//			 LOGGER.info("runLoadJobs");
//			/*     */
//			 ProgressMonitorDialog dialog = new ProgressMonitorDialog(parent.getShell());
//			 LOGGER.info("Dbname " + this.dbName + ", project: " + this.project);
//			 dialog.run(true, true, new TMG9Provider(this.dbName, this.project));
//			/*     */ } catch (Exception e) {
//			 LOGGER
//					.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
//			/*     */ }
//		/*     */ }
//	/*     */ }
