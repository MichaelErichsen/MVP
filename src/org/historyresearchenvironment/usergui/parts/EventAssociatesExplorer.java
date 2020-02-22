/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.eclipse.e4.ui.di.Focus;
/*     */ import org.eclipse.swt.events.MouseAdapter;
/*     */ import org.eclipse.swt.events.MouseEvent;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Button;
/*     */ import org.eclipse.swt.widgets.Combo;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.eclipse.swt.widgets.Table;
/*     */ import org.eclipse.swt.widgets.TableColumn;
/*     */ import org.eclipse.swt.widgets.TableItem;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ import org.eclipse.wb.swt.ResourceManager;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterface;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterfaceFactory;
/*     */ import org.historyresearchenvironment.usergui.dialogs.PersonSelectionDialog;
/*     */ import org.historyresearchenvironment.usergui.models.EventAssociatesModel;
/*     */ import org.historyresearchenvironment.usergui.providers.EventAssociatesProvider;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventAssociatesExplorer
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   @Inject
/*     */   private IEventBroker eventBroker;
/*     */   private ScopedPreferenceStore store;
/*     */   private Text textIdentity;
/*     */   private Table tableProperties;
/*     */   private Text text;
/*     */   private Table tableAssociates;
/*     */   private EventAssociatesProvider eam;
/*     */   private BusinessLayerInterface bli;
/*     */   private ServerRequest req;
/*     */   private ServerResponse resp;
/*     */   
/*     */   protected void callBusinessLayer(int i) {}
/*     */   
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/*  66 */     this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/*  67 */     this.eam = new EventAssociatesProvider();
/*  68 */     this.eam.setId(1);
/*     */     
/*  70 */     this.req = new ServerRequest("GET", "eventassociates", this.eam);
/*     */     
/*  72 */     this.resp = this.bli.callBusinessLayer(this.req);
/*     */     try {
/*  74 */       this.eam = ((EventAssociatesProvider)this.resp.getProvider());
/*     */     } catch (Exception e1) {
/*  76 */       this.eventBroker.post("MESSAGE", e1.getMessage());
/*  77 */       LOGGER.severe(e1.getMessage());
/*     */     }
/*     */     
/*  80 */     parent.setLayout(new GridLayout(6, false));
/*     */     
/*  82 */     Label lblIdentity = new Label(parent, 0);
/*  83 */     lblIdentity.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  84 */     lblIdentity.setText("Identity:");
/*     */     
/*  86 */     this.textIdentity = new Text(parent, 2048);
/*  87 */     this.textIdentity.setLayoutData(new GridData(4, 16777216, true, false, 2, 1));
/*     */     
/*  89 */     Button btnSearch = new Button(parent, 0);
/*  90 */     btnSearch.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDown(MouseEvent e) {
/*  93 */         Display display = Display.getDefault();
/*  94 */         Shell shell = new Shell(display);
/*  95 */         PersonSelectionDialog dialog = new PersonSelectionDialog(shell);
/*  96 */         dialog.open();
/*     */         
/*  98 */         EventAssociatesExplorer.this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  99 */         EventAssociatesExplorer.this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/* 100 */         EventAssociatesExplorer.this.eam = new EventAssociatesProvider();
/* 101 */         EventAssociatesExplorer.this.eam.setId(Integer.parseInt(EventAssociatesExplorer.this.store.getString("PERNO")));
/*     */         
/* 103 */         EventAssociatesExplorer.this.req = new ServerRequest("GET", "eventassociates", EventAssociatesExplorer.this.eam);
/*     */         
/* 105 */         long before = System.nanoTime();
/*     */         
/* 107 */         EventAssociatesExplorer.this.resp = EventAssociatesExplorer.this.bli.callBusinessLayer(EventAssociatesExplorer.this.req);
/*     */         
/* 109 */         long after = System.nanoTime();
/*     */         
/* 111 */         EventAssociatesExplorer.LOGGER.fine("Elapsed time in milliseconds: " + (after - before) / 1000000L);
/*     */         
/* 113 */         if (EventAssociatesExplorer.this.resp == null) {
/* 114 */           EventAssociatesExplorer.this.eventBroker.post("MESSAGE", "Call not successful");
/* 115 */           EventAssociatesExplorer.LOGGER.info("Call not successful");
/* 116 */         } else if (EventAssociatesExplorer.this.resp.getReturnCode() != 0) {
/* 117 */           EventAssociatesExplorer.this.eventBroker.post("MESSAGE", EventAssociatesExplorer.this.resp.getReturnMessage());
/* 118 */           EventAssociatesExplorer.LOGGER.info(EventAssociatesExplorer.this.resp.getReturnMessage());
/*     */         } else {
/* 120 */           EventAssociatesExplorer.this.eam = ((EventAssociatesProvider)EventAssociatesExplorer.this.resp.getProvider());
/*     */           try
/*     */           {
/* 123 */             EventAssociatesExplorer.this.populateExplorer();
/*     */           } catch (Exception e2) {
/* 125 */             EventAssociatesExplorer.LOGGER.severe("Error in request " + EventAssociatesExplorer.this.req.getOperation() + " " + EventAssociatesExplorer.this.req.getModelName() + ", " + 
/* 126 */               e2.getMessage());
/* 127 */             EventAssociatesExplorer.this.eventBroker.post("MESSAGE", e2.getMessage());
/*     */           }
/*     */         }
/*     */       }
/* 131 */     });
/* 132 */     btnSearch.setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon1.png"));
/*     */     
/* 134 */     Button btnNewButton_1 = new Button(parent, 0);
/* 135 */     btnNewButton_1
/* 136 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon2.png"));
/*     */     
/* 138 */     Button btnNewButton_2 = new Button(parent, 0);
/* 139 */     btnNewButton_2
/* 140 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon3.png"));
/*     */     
/* 142 */     this.tableProperties = new Table(parent, 67584);
/* 143 */     this.tableProperties.setLayoutData(new GridData(4, 4, true, true, 6, 1));
/* 144 */     this.tableProperties.setFont(getHreFont(parent));
/* 145 */     this.tableProperties.setHeaderVisible(true);
/* 146 */     this.tableProperties.setLinesVisible(true);
/*     */     
/* 148 */     TableColumn tblclmnProperties = new TableColumn(this.tableProperties, 0);
/* 149 */     tblclmnProperties.setWidth(100);
/* 150 */     tblclmnProperties.setText("Properties");
/*     */     
/* 152 */     TableColumn tblclmnValue = new TableColumn(this.tableProperties, 0);
/* 153 */     tblclmnValue.setWidth(352);
/* 154 */     tblclmnValue.setText("Value");
/*     */     
/* 156 */     TableColumn tblclmnRating = new TableColumn(this.tableProperties, 0);
/* 157 */     tblclmnRating.setWidth(100);
/* 158 */     tblclmnRating.setText("Rating");
/*     */     
/* 160 */     Label label = new Label(parent, 258);
/* 161 */     label.setLayoutData(new GridData(4, 16777216, true, false, 6, 1));
/*     */     
/* 163 */     Label lblShowAssociates = new Label(parent, 0);
/* 164 */     lblShowAssociates.setText("Show Associates:");
/*     */     
/* 166 */     Combo comboAssociates = new Combo(parent, 0);
/* 167 */     comboAssociates.setItems(new String[] { "All Associates (in one table)", 
/* 168 */       "By Associate Type (one table per associate type, person, item, etc)" });
/* 169 */     comboAssociates.setLayoutData(new GridData(4, 16777216, true, false, 2, 1));
/* 170 */     comboAssociates.select(0);
/*     */     
/* 172 */     Button btnFilter = new Button(parent, 32);
/* 173 */     btnFilter.setText("Filter");
/*     */     
/* 175 */     this.text = new Text(parent, 2048);
/* 176 */     this.text.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/* 178 */     Button btnNewButton = new Button(parent, 0);
/* 179 */     btnNewButton
/* 180 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon1.png"));
/*     */     
/* 182 */     this.tableAssociates = new Table(parent, 67584);
/* 183 */     this.tableAssociates.setLayoutData(new GridData(4, 4, true, true, 6, 1));
/* 184 */     this.tableAssociates.setFont(getHreFont(parent));
/* 185 */     this.tableAssociates.setHeaderVisible(true);
/* 186 */     this.tableAssociates.setLinesVisible(true);
/*     */     
/* 188 */     TableColumn tblclmnType = new TableColumn(this.tableAssociates, 0);
/* 189 */     tblclmnType.setWidth(100);
/* 190 */     tblclmnType.setText("Type");
/*     */     
/* 192 */     TableColumn tblclmnRole = new TableColumn(this.tableAssociates, 0);
/* 193 */     tblclmnRole.setWidth(197);
/* 194 */     tblclmnRole.setText("Role");
/*     */     
/* 196 */     TableColumn tblclmnSummary = new TableColumn(this.tableAssociates, 0);
/* 197 */     tblclmnSummary.setWidth(277);
/* 198 */     tblclmnSummary.setText("Summary");
/*     */     
/* 200 */     TableColumn tblclmnOptionalFieldsHere = new TableColumn(this.tableAssociates, 0);
/* 201 */     tblclmnOptionalFieldsHere.setWidth(100);
/* 202 */     tblclmnOptionalFieldsHere.setText("Optional Fields here");
/*     */     
/* 204 */     populateExplorer();
/*     */   }
/*     */   
/*     */ 
/*     */   @PreDestroy
/*     */   public void dispose() {}
/*     */   
/*     */ 
/*     */   protected void populateExplorer()
/*     */   {
/* 214 */     this.textIdentity.setText(this.eam.getIdentity());
/*     */     
/* 216 */     this.tableProperties.removeAll();
/* 217 */     TableItem ti = new TableItem(this.tableProperties, 0);
/* 218 */     ti.setText(0, "* Location");
/*     */     try {
/* 220 */       ti.setText(1, this.eam.getLocation());
/*     */     }
/*     */     catch (Exception localException1) {}
/* 223 */     ti = new TableItem(this.tableProperties, 0);
/* 224 */     ti.setText(0, "* Date");
/*     */     try {
/* 226 */       ti.setText(1, this.eam.getDate());
/*     */     }
/*     */     catch (Exception localException2) {}
/*     */     
/* 230 */     this.tableAssociates.removeAll();
/*     */     try {
/* 232 */       Iterator<EventAssociatesModel> iterator = this.eam.getEaiv().iterator();
/*     */       
/* 234 */       while (iterator.hasNext()) {
/* 235 */         EventAssociatesModel item = (EventAssociatesModel)iterator.next();
/*     */         
/* 237 */         ti = new TableItem(this.tableAssociates, 0);
/* 238 */         ti.setText(item.getStrings());
/*     */       }
/*     */     } catch (Exception e) {
/* 241 */       this.eventBroker.post("MESSAGE", e.getClass() + " " + e.getMessage());
/* 242 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   @Focus
/*     */   public void setFocus() {}
/*     */   
/*     */   protected void updateGui() {}
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\EventAssociatesExplorer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */