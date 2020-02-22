/*     */ package org.historyresearchenvironment.usergui.wizards;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.jface.dialogs.ProgressMonitorDialog;
/*     */ import org.eclipse.jface.wizard.WizardPage;
/*     */ import org.eclipse.swt.events.KeyEvent;
/*     */ import org.eclipse.swt.events.KeyListener;
/*     */ import org.eclipse.swt.events.SelectionAdapter;
/*     */ import org.eclipse.swt.events.SelectionEvent;
/*     */ import org.eclipse.swt.graphics.Font;
/*     */ import org.eclipse.swt.graphics.FontData;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Button;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.FileDialog;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ import org.eclipse.wb.swt.ResourceManager;
/*     */ import org.eclipse.wb.swt.SWTResourceManager;
/*     */ import org.historyresearchenvironment.usergui.providers.TMG9Provider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TMG9ProjectWizardPage
/*     */   extends WizardPage
/*     */ {
/*  37 */   private static final Logger LOGGER = Logger.getLogger("global");
/*  38 */   private final ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  39 */     "org.historyresearchenvironment.usergui");
/*     */   
/*     */   private Text textProject;
/*     */   
/*     */   private Composite container;
/*     */   
/*     */   private Button btnBrowse;
/*     */   private Label lblHDatabaseName;
/*     */   private Text textDbname;
/*     */   private Button btnSelect;
/*     */   private Label lblNewLabel;
/*     */   private String name;
/*     */   private String project;
/*     */   private String dbName;
/*     */   
/*     */   public TMG9ProjectWizardPage()
/*     */   {
/*  56 */     super("Wizard Page");
/*  57 */     setImageDescriptor(
/*  58 */       ResourceManager.getPluginImageDescriptor("org.historyresearchenvironment.usergui", "icons/TMG163.png"));
/*  59 */     setTitle("TMG 9 Project Conversion to H2 Database");
/*  60 */     setDescription(
/*  61 */       "Select a The Master Genealogist Version 9 Project\r\nSelect a H2 Database Name and Location\r\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createControl(final Composite parent)
/*     */   {
/*  73 */     this.container = new Composite(parent, 0);
/*  74 */     this.container.setFont(getHreFont(parent));
/*  75 */     GridLayout layout = new GridLayout();
/*  76 */     this.container.setLayout(layout);
/*  77 */     layout.numColumns = 2;
/*  78 */     Label labelProject = new Label(this.container, 0);
/*  79 */     labelProject.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/*  80 */     labelProject.setText("TMG9 Project:");
/*     */     
/*  82 */     this.textProject = new Text(this.container, 2052);
/*  83 */     this.textProject.setEditable(false);
/*  84 */     this.textProject.setText("");
/*  85 */     this.textProject.addKeyListener(new KeyListener()
/*     */     {
/*     */       public void keyPressed(KeyEvent e) {}
/*     */       
/*     */ 
/*     */ 
/*     */       public void keyReleased(KeyEvent e)
/*     */       {
/*  93 */         if (!TMG9ProjectWizardPage.this.textProject.getText().isEmpty()) {
/*  94 */           TMG9ProjectWizardPage.this.setPageComplete(true);
/*     */         }
/*     */         
/*     */       }
/*  98 */     });
/*  99 */     this.textProject.setLayoutData(new GridData(768));
/*     */     
/*     */ 
/* 102 */     setControl(this.container);
/*     */     
/* 104 */     this.btnBrowse = new Button(this.container, 8);
/* 105 */     this.btnBrowse.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/* 106 */     this.btnBrowse.addSelectionListener(new SelectionAdapter()
/*     */     {
/*     */       public void widgetSelected(SelectionEvent e) {
/* 109 */         FileDialog fd = new FileDialog(parent.getShell(), 4096);
/* 110 */         fd.setText("Open");
/* 111 */         fd.setFilterPath(".");
/* 112 */         String[] filterExt = { "*.pjc", "*.*" };
/* 113 */         fd.setFilterExtensions(filterExt);
/* 114 */         TMG9ProjectWizardPage.this.project = fd.open();
/* 115 */         TMG9ProjectWizardPage.this.textProject.setText(TMG9ProjectWizardPage.this.project);
/* 116 */         TMG9ProjectWizardPage.this.name = FilenameUtils.getBaseName(TMG9ProjectWizardPage.this.project).replace("_", "");
/* 117 */         TMG9ProjectWizardPage.LOGGER.info("Project: " + TMG9ProjectWizardPage.this.project);
/*     */       }
/* 119 */     });
/* 120 */     this.btnBrowse.setText("Browse");
/*     */     
/* 122 */     this.lblNewLabel = new Label(this.container, 0);
/*     */     
/* 124 */     this.lblHDatabaseName = new Label(this.container, 0);
/* 125 */     this.lblHDatabaseName.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/* 126 */     this.lblHDatabaseName.setText("H2 Database name:");
/*     */     
/* 128 */     this.textDbname = new Text(this.container, 2048);
/* 129 */     this.textDbname.setEditable(false);
/* 130 */     this.textDbname.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/* 132 */     this.btnSelect = new Button(this.container, 0);
/* 133 */     this.btnSelect.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/* 134 */     this.btnSelect.addSelectionListener(new SelectionAdapter()
/*     */     {
/*     */       public void widgetSelected(SelectionEvent e) {
/* 137 */         FileDialog fd = new FileDialog(parent.getShell(), 8192);
/* 138 */         fd.setText("Create");
/* 139 */         fd.setFileName(TMG9ProjectWizardPage.this.name);
/* 140 */         fd.setFilterPath(".");
/* 141 */         String[] filterExt = { "*.h2.db", "*.*" };
/* 142 */         fd.setFilterExtensions(filterExt);
/* 143 */         TMG9ProjectWizardPage.this.dbName = fd.open();
/* 144 */         TMG9ProjectWizardPage.this.textDbname.setText(TMG9ProjectWizardPage.this.dbName);
/* 145 */         TMG9ProjectWizardPage.this.dbName = TMG9ProjectWizardPage.this.dbName.replace(".h2.db", "");
/* 146 */         TMG9ProjectWizardPage.LOGGER.info("Database; " + TMG9ProjectWizardPage.this.dbName);
/* 147 */         TMG9ProjectWizardPage.this.setPageComplete(true);
/*     */         
/* 149 */         TMG9ProjectWizardPage.this.runLoadJobs(parent);
/*     */       }
/* 151 */     });
/* 152 */     this.btnSelect.setText("Select");
/* 153 */     new Label(this.container, 0);
/* 154 */     setPageComplete(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Font getHreFont(Composite parent)
/*     */   {
/* 162 */     LOGGER.fine("Get HRE Font");
/*     */     Font font;
/*     */     try
/*     */     {
/* 166 */       String s = this.store.getString("HREFONT");
/*     */       
/* 168 */       LOGGER.fine(s);
/* 169 */       String[] sa = s.split("-");
/* 170 */       for (int i = 0; i < sa.length; i++) {
/* 171 */         LOGGER.fine("sa[" + i + "]: " + sa[i]);
/*     */       }
/* 173 */       String[] sb = sa[0].split("\\|");
/* 174 */       for (int i = 0; i < sb.length; i++) {
/* 175 */         LOGGER.fine("sb[" + i + "]: " + sb[i]);
/*     */       }
/* 177 */       LOGGER.fine("HRE font: " + sb[1] + " " + Math.round(Double.valueOf(sb[2]).doubleValue()) + " " + sb[3]);
/* 178 */       font = SWTResourceManager.getFont(sb[1], (int)Math.round(Double.valueOf(sb[2]).doubleValue()), Integer.parseInt(sb[3]));
/*     */     } catch (NumberFormatException e) { Font font;
/* 180 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/* 181 */       e.printStackTrace();
/*     */       
/* 183 */       font = parent.getShell().getFont();
/* 184 */       FontData fd = font.getFontData()[0];
/* 185 */       font = SWTResourceManager.getFont(fd.getName(), 12, fd.getStyle());
/*     */     }
/*     */     
/* 188 */     return font;
/*     */   }
/*     */   
/*     */ 
/*     */   private void runLoadJobs(Composite parent)
/*     */   {
/*     */     try
/*     */     {
/* 196 */       LOGGER.info("runLoadJobs");
/*     */       
/* 198 */       ProgressMonitorDialog dialog = new ProgressMonitorDialog(parent.getShell());
/* 199 */       LOGGER.info("Dbname " + this.dbName + ", project: " + this.project);
/* 200 */       dialog.run(true, true, new TMG9Provider(this.dbName, this.project));
/*     */     } catch (Exception e) {
/* 202 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\wizards\TMG9ProjectWizardPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */