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
/*     */ import org.historyresearchenvironment.usergui.models.ConnectionEventsItem;
/*     */ import org.historyresearchenvironment.usergui.models.PersonalConnectionsModel;
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
/*     */ 
/*     */ 
/*     */ public class PersonalConnectionsExplorer
/*     */   extends AbstractHREGuiPart
/*     */ {
/*     */   @Inject
/*     */   private IEventBroker eventBroker;
/*     */   private ScopedPreferenceStore store;
/*     */   private Text textIdentity;
/*     */   private Table tableParents;
/*     */   private Text textFilterName;
/*     */   private Table tableEvents;
/*     */   private PersonalConnectionsModel pcm;
/*     */   private BusinessLayerInterface bli;
/*     */   private ServerRequest req;
/*     */   private ServerResponse resp;
/*     */   
/*     */   protected void callBusinessLayer(int i) {}
/*     */   
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/*  68 */     this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/*  69 */     this.pcm = new PersonalConnectionsModel();
/*  70 */     this.pcm.setId(1);
/*     */     
/*  72 */     this.req = new ServerRequest("GET", "personalconnections", this.pcm);
/*     */     
/*  74 */     this.resp = this.bli.callBusinessLayer(this.req);
/*     */     try {
/*  76 */       this.pcm = ((PersonalConnectionsModel)this.resp.getProvider());
/*     */     } catch (Exception e1) {
/*  78 */       this.eventBroker.post("MESSAGE", e1.getMessage());
/*  79 */       LOGGER.severe(e1.getMessage());
/*     */     }
/*     */     
/*  82 */     parent.setLayout(new GridLayout(6, false));
/*  83 */     parent.setFont(getHreFont(parent));
/*     */     
/*  85 */     Label lblIdentity = new Label(parent, 0);
/*  86 */     lblIdentity.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  87 */     lblIdentity.setText("Identity:");
/*     */     
/*  89 */     this.textIdentity = new Text(parent, 2048);
/*  90 */     this.textIdentity.setEditable(false);
/*  91 */     this.textIdentity.setLayoutData(new GridData(4, 16777216, true, false, 2, 1));
/*     */     
/*  93 */     Button btnSearch = new Button(parent, 0);
/*  94 */     btnSearch.setToolTipText("Meant to search persaon by ID, but the data is hard coded");
/*  95 */     btnSearch.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDown(MouseEvent e) {
/*  98 */         Display display = Display.getDefault();
/*  99 */         Shell shell = new Shell(display);
/* 100 */         PersonSelectionDialog dialog = new PersonSelectionDialog(shell);
/* 101 */         dialog.open();
/*     */         
/* 103 */         PersonalConnectionsExplorer.this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/* 104 */         PersonalConnectionsExplorer.this.bli = BusinessLayerInterfaceFactory.getBusinessLayerInterface();
/* 105 */         PersonalConnectionsExplorer.this.pcm = new PersonalConnectionsModel();
/* 106 */         PersonalConnectionsExplorer.this.pcm.setId(Integer.parseInt(PersonalConnectionsExplorer.this.store.getString("PERNO")));
/*     */         
/* 108 */         PersonalConnectionsExplorer.this.req = new ServerRequest("GET", "personalconnections", PersonalConnectionsExplorer.this.pcm);
/*     */         
/* 110 */         long before = System.nanoTime();
/*     */         
/* 112 */         PersonalConnectionsExplorer.this.resp = PersonalConnectionsExplorer.this.bli.callBusinessLayer(PersonalConnectionsExplorer.this.req);
/*     */         
/* 114 */         long after = System.nanoTime();
/*     */         
/* 116 */         PersonalConnectionsExplorer.LOGGER.fine("Elapsed time in milliseconds: " + (after - before) / 1000000L);
/*     */         
/* 118 */         if (PersonalConnectionsExplorer.this.resp == null) {
/* 119 */           PersonalConnectionsExplorer.this.eventBroker.post("MESSAGE", "Call not successful");
/* 120 */         } else if (PersonalConnectionsExplorer.this.resp.getReturnCode() != 0) {
/* 121 */           PersonalConnectionsExplorer.this.eventBroker.post("MESSAGE", PersonalConnectionsExplorer.this.resp.getReturnMessage());
/*     */         } else {
/* 123 */           PersonalConnectionsExplorer.this.pcm = ((PersonalConnectionsModel)PersonalConnectionsExplorer.this.resp.getProvider());
/*     */           try
/*     */           {
/* 126 */             PersonalConnectionsExplorer.this.populateExplorer();
/*     */           } catch (Exception e2) {
/* 128 */             PersonalConnectionsExplorer.LOGGER.severe("Error in request " + PersonalConnectionsExplorer.this.req.getOperation() + " " + PersonalConnectionsExplorer.this.req.getModelName() + ", " + 
/* 129 */               e2.getMessage());
/* 130 */             PersonalConnectionsExplorer.this.eventBroker.post("MESSAGE", e2.getMessage());
/*     */           }
/*     */         }
/*     */       }
/* 134 */     });
/* 135 */     btnSearch.setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon1.png"));
/*     */     
/* 137 */     Button btnNewButton_1 = new Button(parent, 0);
/* 138 */     btnNewButton_1.setToolTipText("Have no idea of what it is supposed to do");
/* 139 */     btnNewButton_1.setImage(
/* 140 */       ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/gear-32.png"));
/*     */     
/* 142 */     Button btnNewButton_2 = new Button(parent, 0);
/* 143 */     btnNewButton_2.setToolTipText("Does absolutely nothing");
/* 144 */     btnNewButton_2
/* 145 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon3.png"));
/* 146 */     new Label(parent, 0);
/* 147 */     new Label(parent, 0);
/* 148 */     new Label(parent, 0);
/* 149 */     new Label(parent, 0);
/* 150 */     new Label(parent, 0);
/* 151 */     new Label(parent, 0);
/*     */     
/* 153 */     this.tableParents = new Table(parent, 67584);
/* 154 */     GridData gd_tableParents = new GridData(4, 4, true, false, 6, 1);
/* 155 */     gd_tableParents.heightHint = 106;
/* 156 */     this.tableParents.setLayoutData(gd_tableParents);
/* 157 */     this.tableParents.setFont(getHreFont(parent));
/* 158 */     this.tableParents.setHeaderVisible(true);
/* 159 */     this.tableParents.setLinesVisible(true);
/*     */     
/* 161 */     TableColumn tblclmnProperties = new TableColumn(this.tableParents, 0);
/* 162 */     tblclmnProperties.setWidth(100);
/* 163 */     tblclmnProperties.setText("Properties");
/*     */     
/* 165 */     TableColumn tblclmnValue = new TableColumn(this.tableParents, 0);
/* 166 */     tblclmnValue.setWidth(290);
/* 167 */     tblclmnValue.setText("Value");
/*     */     
/* 169 */     TableColumn tblclmnRating = new TableColumn(this.tableParents, 0);
/* 170 */     tblclmnRating.setWidth(100);
/* 171 */     tblclmnRating.setText("Rating");
/*     */     
/* 173 */     Label label = new Label(parent, 258);
/* 174 */     label.setLayoutData(new GridData(4, 16777216, false, false, 5, 1));
/* 175 */     new Label(parent, 0);
/*     */     
/* 177 */     Label lblSortConnectionsBy = new Label(parent, 0);
/* 178 */     lblSortConnectionsBy.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 179 */     lblSortConnectionsBy.setText("Sort Connections by:");
/*     */     
/* 181 */     Combo comboConnectionSort = new Combo(parent, 0);
/* 182 */     comboConnectionSort.setItems(new String[] { "Event Date, then Person", "Person, then Event Date", 
/* 183 */       "Theme, then Event Date, then Person", "Theme, then Person, then Event Date" });
/* 184 */     comboConnectionSort.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/* 185 */     comboConnectionSort.select(0);
/*     */     
/* 187 */     Button btnFilter = new Button(parent, 32);
/* 188 */     btnFilter.setText("Filter:");
/*     */     
/* 190 */     this.textFilterName = new Text(parent, 2048);
/* 191 */     this.textFilterName.setLayoutData(new GridData(4, 16777216, true, false, 2, 1));
/*     */     
/* 193 */     Button btnNewButton = new Button(parent, 0);
/* 194 */     btnNewButton
/* 195 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/Icon1.png"));
/*     */     
/* 197 */     this.tableEvents = new Table(parent, 67584);
/* 198 */     this.tableEvents.setLayoutData(new GridData(4, 4, true, true, 6, 1));
/* 199 */     this.tableEvents.setFont(getHreFont(parent));
/* 200 */     this.tableEvents.setHeaderVisible(true);
/* 201 */     this.tableEvents.setLinesVisible(true);
/*     */     
/* 203 */     TableColumn tblclmnPerson = new TableColumn(this.tableEvents, 0);
/* 204 */     tblclmnPerson.setWidth(100);
/* 205 */     tblclmnPerson.setText("Person");
/*     */     
/* 207 */     TableColumn tblclmnRole = new TableColumn(this.tableEvents, 0);
/* 208 */     tblclmnRole.setWidth(100);
/* 209 */     tblclmnRole.setText("Role");
/*     */     
/* 211 */     TableColumn tblclmnEvent = new TableColumn(this.tableEvents, 0);
/* 212 */     tblclmnEvent.setWidth(250);
/* 213 */     tblclmnEvent.setText("Event");
/*     */     
/* 215 */     TableColumn tblclmnDate = new TableColumn(this.tableEvents, 0);
/* 216 */     tblclmnDate.setWidth(100);
/* 217 */     tblclmnDate.setText("Date");
/*     */     
/* 219 */     TableColumn tblclmnOptionalFieldsHere = new TableColumn(this.tableEvents, 0);
/* 220 */     tblclmnOptionalFieldsHere.setWidth(151);
/* 221 */     tblclmnOptionalFieldsHere.setText("Optional Fields here");
/*     */     
/* 223 */     populateExplorer();
/*     */   }
/*     */   
/*     */ 
/*     */   @PreDestroy
/*     */   public void dispose() {}
/*     */   
/*     */ 
/*     */   private void populateExplorer()
/*     */   {
/* 233 */     this.textIdentity.setText(this.pcm.getIdentity());
/*     */     
/* 235 */     this.tableParents.removeAll();
/* 236 */     TableItem ti = new TableItem(this.tableParents, 0);
/* 237 */     ti.setText(0, "Father-Bio");
/*     */     try {
/* 239 */       ti.setText(1, this.pcm.getFather());
/*     */     }
/*     */     catch (Exception localException1) {}
/*     */     
/* 243 */     ti = new TableItem(this.tableParents, 0);
/* 244 */     ti.setText(0, "Mother-Bio");
/*     */     try {
/* 246 */       ti.setText(1, this.pcm.getMother());
/*     */     }
/*     */     catch (Exception localException2) {}
/*     */     
/* 250 */     ti = new TableItem(this.tableParents, 0);
/* 251 */     ti.setText(0, "#Children");
/*     */     try {
/* 253 */       ti.setText(1, Integer.toString(this.pcm.getNoChildren()));
/*     */     }
/*     */     catch (Exception localException3) {}
/*     */     
/* 257 */     this.tableEvents.removeAll();
/*     */     try {
/* 259 */       Iterator<ConnectionEventsItem> iterator = this.pcm.getCaiv().iterator();
/*     */       
/* 261 */       while (iterator.hasNext()) {
/* 262 */         ConnectionEventsItem eventModel = (ConnectionEventsItem)iterator.next();
/*     */         
/* 264 */         ti = new TableItem(this.tableEvents, 0);
/* 265 */         ti.setText(eventModel.getStrings());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 270 */       this.eventBroker.post("MESSAGE", e.getClass() + " " + e.getMessage());
/* 271 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */   @Focus
/*     */   public void setFocus() {}
/*     */   
/*     */   protected void updateGui() {}
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PersonalConnectionsExplorer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */