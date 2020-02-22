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
/*     */ import org.eclipse.e4.core.commands.ECommandService;
/*     */ import org.eclipse.e4.core.commands.EHandlerService;
/*     */ import org.eclipse.e4.core.di.annotations.Optional;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.eclipse.e4.ui.di.UIEventTopic;
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
/*     */ import org.eclipse.wb.swt.SWTResourceManager;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterface;
/*     */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterfaceFactory;
/*     */ import org.historyresearchenvironment.usergui.dialogs.PersonSelectionDialog;
/*     */ import org.historyresearchenvironment.usergui.models.EventModel;
/*     */ import org.historyresearchenvironment.usergui.providers.PersonalEventProvider;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonalEventExplorer
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   @Inject
/*     */   ECommandService commandService;
/*     */   @Inject
/*     */   EHandlerService handlerService;
/*     */   private Text textIdentity;
/*     */   private Table tableProperties;
/*     */   private Text filterNameField;
/*     */   private Table tableEvents;
/*  57 */   private PersonalEventProvider provider = new PersonalEventProvider();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void callBusinessLayer(int perNo)
/*     */   {
/*  67 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*  68 */     this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/*     */     
/*  70 */     this.provider.setId(perNo);
/*     */     
/*  72 */     this.req = new ServerRequest("GET", "personalevents", this.provider);
/*     */     
/*  74 */     long before = System.nanoTime();
/*     */     
/*  76 */     this.resp = this.bli.callBusinessLayer(this.req);
/*     */     
/*  78 */     long after = System.nanoTime();
/*  79 */     LOGGER.fine("Elapsed time in milliseconds: " + (after - before) / 1000000L);
/*     */     
/*  81 */     if (this.resp == null) {
/*  82 */       this.eventBroker.post("MESSAGE", "Call not successful");
/*  83 */       LOGGER.severe("Call not successful");
/*  84 */     } else if (this.resp.getReturnCode() != 0) {
/*  85 */       this.eventBroker.post("MESSAGE", this.resp.getReturnMessage());
/*  86 */       LOGGER.severe(this.resp.getReturnMessage());
/*     */     } else {
/*  88 */       this.provider = ((PersonalEventProvider)this.resp.getProvider());
/*     */       try
/*     */       {
/*  91 */         updateGui();
/*     */       } catch (Exception e2) {
/*  93 */         LOGGER.severe(
/*  94 */           "Error in request " + this.req.getOperation() + " " + this.req.getModelName() + ", " + e2.getMessage());
/*  95 */         this.eventBroker.post("MESSAGE", e2.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/* 105 */     parent.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1));
/* 106 */     parent.setLayout(new GridLayout(7, false));
/* 107 */     parent.setFont(getHreFont(parent));
/* 108 */     new Label(parent, 0);
/* 109 */     new Label(parent, 0);
/* 110 */     new Label(parent, 0);
/* 111 */     new Label(parent, 0);
/*     */     
/* 113 */     Button btnSearch = new Button(parent, 0);
/* 114 */     btnSearch.setToolTipText("Select person by ID");
/* 115 */     btnSearch.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDown(MouseEvent e) {
/* 118 */         Display display = Display.getDefault();
/* 119 */         Shell shell = new Shell(display);
/* 120 */         PersonSelectionDialog dialog = new PersonSelectionDialog(shell);
/* 121 */         dialog.open();
/*     */         
/* 123 */         int i = Integer.parseInt(PersonalEventExplorer.this.store.getString("PERNO"));
/* 124 */         PersonalEventExplorer.this.callBusinessLayer(i);
/*     */       }
/*     */       
/*     */ 
/* 128 */     });
/* 129 */     btnSearch.setLayoutData(new GridData(16384, 16777216, false, false, 1, 2));
/* 130 */     btnSearch.setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon1.png"));
/*     */     
/* 132 */     Button btnGoogle = new Button(parent, 0);
/* 133 */     btnGoogle.setToolTipText("Does not do anything yet");
/* 134 */     btnGoogle.setLayoutData(new GridData(16384, 16777216, false, false, 1, 2));
/* 135 */     btnGoogle.setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon2.png"));
/*     */     
/* 137 */     Button btnNewButton_2 = new Button(parent, 0);
/* 138 */     btnNewButton_2.setToolTipText("Does not do anything either");
/* 139 */     btnNewButton_2.setLayoutData(new GridData(16384, 16777216, false, false, 1, 2));
/* 140 */     btnNewButton_2
/* 141 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon3.png"));
/*     */     
/* 143 */     Label lblIdentity = new Label(parent, 0);
/* 144 */     lblIdentity.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 145 */     lblIdentity.setText("Identity:");
/*     */     
/* 147 */     this.textIdentity = new Text(parent, 2048);
/* 148 */     this.textIdentity.setEditable(false);
/* 149 */     this.textIdentity.setLayoutData(new GridData(4, 4, true, false, 3, 1));
/*     */     
/* 151 */     this.tableProperties = new Table(parent, 67584);
/* 152 */     this.tableProperties.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDoubleClick(MouseEvent e) {
/* 155 */         TableItem[] tableItemArray = PersonalEventExplorer.this.tableProperties.getSelection();
/* 156 */         TableItem tableItem = tableItemArray[0];
/* 157 */         String string = tableItem.getText(1);
/* 158 */         String[] sa = string.split("\\(");
/* 159 */         String[] sb = sa[1].split("\\)");
/* 160 */         PersonalEventExplorer.this.eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(Integer.parseInt(sb[0])));
/*     */       }
/* 162 */     });
/* 163 */     GridData gd_tableProperties = new GridData(4, 4, true, false, 7, 1);
/* 164 */     gd_tableProperties.heightHint = 106;
/* 165 */     this.tableProperties.setLayoutData(gd_tableProperties);
/* 166 */     this.tableProperties.setFont(getHreFont(parent));
/* 167 */     this.tableProperties.setHeaderVisible(true);
/* 168 */     this.tableProperties.setLinesVisible(true);
/*     */     
/* 170 */     TableColumn tblclmnProperties = new TableColumn(this.tableProperties, 0);
/* 171 */     tblclmnProperties.setWidth(100);
/* 172 */     tblclmnProperties.setText("Property");
/*     */     
/* 174 */     TableColumn tblclmnValue = new TableColumn(this.tableProperties, 0);
/* 175 */     tblclmnValue.setWidth(367);
/* 176 */     tblclmnValue.setText("Value");
/*     */     
/* 178 */     TableColumn tblclmnRating = new TableColumn(this.tableProperties, 0);
/* 179 */     tblclmnRating.setWidth(100);
/* 180 */     tblclmnRating.setText("Rating");
/*     */     
/* 182 */     Label lblShowEvents = new Label(parent, 0);
/* 183 */     lblShowEvents.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 184 */     lblShowEvents.setText("Show Events:");
/*     */     
/* 186 */     Combo combo = new Combo(parent, 0);
/* 187 */     combo.setItems(new String[] { "All Events", "By Function", "By Theme" });
/* 188 */     combo.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/* 189 */     combo.select(0);
/*     */     
/* 191 */     Button btnFilter = new Button(parent, 32);
/* 192 */     btnFilter.setText("Filter:");
/*     */     
/* 194 */     this.filterNameField = new Text(parent, 2048);
/* 195 */     this.filterNameField.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/* 196 */     new Label(parent, 0);
/* 197 */     new Label(parent, 0);
/*     */     
/* 199 */     Button btnNewButton_3 = new Button(parent, 0);
/* 200 */     btnNewButton_3.setToolTipText("Does nothing");
/* 201 */     btnNewButton_3
/* 202 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon1.png"));
/*     */     
/* 204 */     this.tableEvents = new Table(parent, 67584);
/* 205 */     this.tableEvents.setLayoutData(new GridData(4, 4, true, true, 7, 1));
/* 206 */     this.tableEvents.setFont(getHreFont(parent));
/* 207 */     this.tableEvents.setHeaderVisible(true);
/* 208 */     this.tableEvents.setLinesVisible(true);
/*     */     
/* 210 */     TableColumn tblclmnEventTag = new TableColumn(this.tableEvents, 0);
/* 211 */     tblclmnEventTag.setWidth(100);
/* 212 */     tblclmnEventTag.setText("EventModel Tag");
/*     */     
/* 214 */     TableColumn tblclmnRole = new TableColumn(this.tableEvents, 0);
/* 215 */     tblclmnRole.setWidth(100);
/* 216 */     tblclmnRole.setText("Role");
/*     */     
/* 218 */     TableColumn tblclmnDate = new TableColumn(this.tableEvents, 0);
/* 219 */     tblclmnDate.setWidth(100);
/* 220 */     tblclmnDate.setText("Date");
/*     */     
/* 222 */     TableColumn tblclmnSummary = new TableColumn(this.tableEvents, 0);
/* 223 */     tblclmnSummary.setWidth(455);
/* 224 */     tblclmnSummary.setText("Summary");
/*     */     
/* 226 */     TableColumn tblclmnOptionalField = new TableColumn(this.tableEvents, 0);
/* 227 */     tblclmnOptionalField.setWidth(100);
/* 228 */     tblclmnOptionalField.setText("Optional Field 1");
/*     */     
/* 230 */     callBusinessLayer(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @PreDestroy
/*     */   public void dispose()
/*     */   {
/* 238 */     this.provider.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Inject
/*     */   @Optional
/*     */   private void subscribePersonUpdateTopic(@UIEventTopic("PERSON_UPDATE_TOPIC") int perNo)
/*     */   {
/* 247 */     callBusinessLayer(perNo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateGui()
/*     */   {
/*     */     try
/*     */     {
/* 258 */       this.textIdentity.setText(this.provider.getIdentity());
/*     */     } catch (Exception e1) {
/* 260 */       LOGGER.severe(e1.getMessage());
/*     */     }
/*     */     
/* 263 */     this.tableProperties.removeAll();
/*     */     
/* 265 */     TableItem ti = new TableItem(this.tableProperties, 0);
/* 266 */     ti.setText(0, "Father-Bio");
/*     */     try {
/* 268 */       ti.setText(1, this.provider.getFather());
/*     */     }
/*     */     catch (Exception localException1) {}
/*     */     
/* 272 */     ti = new TableItem(this.tableProperties, 0);
/* 273 */     ti.setText(0, "Mother-Bio");
/*     */     try {
/* 275 */       ti.setText(1, this.provider.getMother());
/*     */     }
/*     */     catch (Exception localException2) {}
/*     */     
/* 279 */     ti = new TableItem(this.tableProperties, 0);
/* 280 */     ti.setText(0, "#Children");
/*     */     try {
/* 282 */       ti.setText(1, Integer.toString(this.provider.getNoChildren()));
/*     */     }
/*     */     catch (Exception localException3) {}
/*     */     
/* 286 */     this.tableEvents.removeAll();
/*     */     try
/*     */     {
/* 289 */       Iterator<EventModel> iterator = this.provider.getEvents().iterator();
/*     */       
/* 291 */       while (iterator.hasNext()) {
/* 292 */         EventModel item = (EventModel)iterator.next();
/*     */         
/* 294 */         ti = new TableItem(this.tableEvents, 0);
/* 295 */         ti.setText(item.getStrings());
/*     */       }
/* 297 */       LOGGER.fine("Found: " + this.provider.writeJson(this.provider.getClass().getName()));
/* 298 */       this.eventBroker.post("PEM", this.provider);
/*     */     } catch (Exception e) {
/* 300 */       this.eventBroker.post("MESSAGE", e.getClass() + " " + e.getMessage());
/* 301 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PersonalEventExplorer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */